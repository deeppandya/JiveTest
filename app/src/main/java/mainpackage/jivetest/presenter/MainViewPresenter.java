package mainpackage.jivetest.presenter;

import android.view.View;

import java.util.List;

import mainpackage.jivetest.R;
import mainpackage.jivetest.interactor.IMainViewInteractor;
import mainpackage.jivetest.interactor.MainViewInteractor;
import mainpackage.jivetest.iviews.IMainView;
import mainpackage.jivetest.listener.GetPhotosFromFlickerListener;
import mainpackage.jivetest.listener.GetRecentPhotosListener;
import mainpackage.jivetest.model.FlickerPhotos;
import mortar.Presenter;
import mortar.bundler.BundleService;

public class MainViewPresenter  extends Presenter<IMainView> {

    private IMainViewInteractor mainViewIntegrator;

    public MainViewPresenter() {
        mainViewIntegrator=new MainViewInteractor();
    }

    @Override
    protected BundleService extractBundleService(IMainView view) {
        return BundleService.getBundleService(view.getContext());
    }

    public void getFlickerPhotos(String photoQuery){
        GetPhotosFromFlickerListener getPhotosFromFlickerListener = new GetPhotosFromFlickerListener() {
            @Override
            public void beforeGetPhotosFromFlicker() {
                getView().showProgressDialog(getView().getContext().getResources().getString(R.string.getting_photos));
            }

            @Override
            public void afterGetPhotosFromFlicker(List<FlickerPhotos> flickerPhotosList) {
                getView().hideProgressDialog();
                getView().updateEmptySearch(View.GONE);
                setFlickerPhotosListAdapter(flickerPhotosList);
            }

            @Override
            public void onError() {
                getView().hideProgressDialog();
                getView().updateEmptySearch(View.VISIBLE);
            }
        };

        mainViewIntegrator.getFlickerPhotos(photoQuery,getPhotosFromFlickerListener);
    }

    private void setFlickerPhotosListAdapter(List<FlickerPhotos> flickerPhotosList) {
        getView().setFlickerPhotoListAdapter(flickerPhotosList);
    }

    public void getRecentFlickerPhotos() {
        GetRecentPhotosListener getRecentPhotosListener=new GetRecentPhotosListener() {
            @Override
            public void beforeGetRecntPhotos() {
                getView().showProgressDialog(getView().getContext().getResources().getString(R.string.getting_recent_photos));
            }

            @Override
            public void afterGetRecentPhotos(List<FlickerPhotos> flickerPhotosList) {
                getView().hideProgressDialog();
                setFlickerPhotosListAdapter(flickerPhotosList);
            }

            @Override
            public void onError() {
                getView().hideProgressDialog();
            }
        };

        mainViewIntegrator.getRecentPhotos(getRecentPhotosListener);

    }
}
