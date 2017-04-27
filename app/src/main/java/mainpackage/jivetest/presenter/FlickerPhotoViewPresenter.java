package mainpackage.jivetest.presenter;

import java.util.List;

import mainpackage.jivetest.R;
import mainpackage.jivetest.interactor.FlickerPhotoViewInteractor;
import mainpackage.jivetest.interactor.IFlickerPhotoViewInteractor;
import mainpackage.jivetest.iviews.IFlickerPhotoView;
import mainpackage.jivetest.listener.GetPhotoCommentsListener;
import mainpackage.jivetest.listener.GetUserInfoListener;
import mainpackage.jivetest.model.PhotoComments;
import mainpackage.jivetest.model.UserInfo;
import mortar.Presenter;
import mortar.bundler.BundleService;

public class FlickerPhotoViewPresenter extends Presenter<IFlickerPhotoView> {

    private IFlickerPhotoViewInteractor flickerPhotoViewInteractor;

    public FlickerPhotoViewPresenter() {
        flickerPhotoViewInteractor = new FlickerPhotoViewInteractor();
    }

    @Override
    protected BundleService extractBundleService(IFlickerPhotoView view) {
        return BundleService.getBundleService(view.getContext());
    }

    public void getUserName(final String userId) {
        GetUserInfoListener getUserInfoListener = new GetUserInfoListener() {
            @Override
            public void beforeGetUserInfo() {
                getView().showProgressDialog(getView().getContext().getResources().getString(R.string.getting_username));
            }

            @Override
            public void afterGetUserInfo(UserInfo userInfo) {
                getView().hideProgressDialog();
                getView().updateUserName(userInfo);
            }

            @Override
            public void onError() {
                getView().hideProgressDialog();
            }
        };

        flickerPhotoViewInteractor.getUserName(userId, getUserInfoListener);

    }

    public void getPhotoComments(String photoId) {
        GetPhotoCommentsListener getPhotoCommentsListener = new GetPhotoCommentsListener() {
            @Override
            public void beforeGetPhotoComments() {
                getView().showProgressDialog(getView().getContext().getResources().getString(R.string.getting_photo_comments));
            }

            @Override
            public void afterGetPhotoComments(List<PhotoComments> photoComments) {
                getView().hideProgressDialog();

                if (photoComments.size() > 0) {
                    String comments = "";

                    for (int i = 0; i < photoComments.size(); i++) {
                        comments = comments + photoComments.get(i).getAuthorname()+" : " + photoComments.get(i).get_content() + "\n";
                    }

                    getView().updatePhotoComments(comments);
                } else {
                    getView().updatePhotoComments(getView().getContext().getResources().getString(R.string.no_comments));
                }

            }

            @Override
            public void onError() {
                getView().hideProgressDialog();
                getView().updatePhotoComments(getView().getContext().getResources().getString(R.string.no_comments));
            }
        };

        flickerPhotoViewInteractor.getPhotoComments(photoId, getPhotoCommentsListener);

    }

}
