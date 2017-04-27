package mainpackage.jivetest.interactor;

import mainpackage.jivetest.listener.GetPhotoCommentsListener;
import mainpackage.jivetest.listener.GetUserInfoListener;

public interface IFlickerPhotoViewInteractor {

    void getUserName(String userId, GetUserInfoListener getUserInfoListener);

    void getPhotoComments(String photoId, GetPhotoCommentsListener getPhotoCommentsListener);

}
