package mainpackage.jivetest.interactor;

import mainpackage.jivetest.asynctask.GetPhotosFromFlickerAsyncTask;
import mainpackage.jivetest.asynctask.GetRecentPhotosAsyncTask;
import mainpackage.jivetest.listener.GetPhotosFromFlickerListener;
import mainpackage.jivetest.listener.GetRecentPhotosListener;

public class MainViewInteractor implements IMainViewInteractor {

    @Override
    public void getFlickerPhotos(String photoQuery,GetPhotosFromFlickerListener getPhotosFromFlickerListener) {

        GetPhotosFromFlickerAsyncTask getPhotosFromFlickerAsyncTask = new GetPhotosFromFlickerAsyncTask(photoQuery,getPhotosFromFlickerListener);
        getPhotosFromFlickerAsyncTask.execute();
    }

    @Override
    public void getRecentPhotos(GetRecentPhotosListener getRecentPhotosListener) {
        GetRecentPhotosAsyncTask getRecentPhotosAsyncTask=new GetRecentPhotosAsyncTask(getRecentPhotosListener);
        getRecentPhotosAsyncTask.execute();
    }
}
