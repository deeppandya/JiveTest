package mainpackage.jivetest.interactor;

import mainpackage.jivetest.asynctask.GetPhotoCommentsAsyncTask;
import mainpackage.jivetest.asynctask.GetUserInfoAsyncTask;
import mainpackage.jivetest.listener.GetPhotoCommentsListener;
import mainpackage.jivetest.listener.GetUserInfoListener;

public class FlickerPhotoViewInteractor implements IFlickerPhotoViewInteractor {
    @Override
    public void getUserName(String userId, GetUserInfoListener getUserInfoListener) {
        GetUserInfoAsyncTask getUserInfoAsyncTask=new GetUserInfoAsyncTask(userId,getUserInfoListener);
        getUserInfoAsyncTask.execute();
    }

    @Override
    public void getPhotoComments(String photoId, GetPhotoCommentsListener getPhotoCommentsListener) {
        GetPhotoCommentsAsyncTask getPhotoCommentsAsyncTask=new GetPhotoCommentsAsyncTask(photoId,getPhotoCommentsListener);
        getPhotoCommentsAsyncTask.execute();
    }
}
