package mainpackage.jivetest.interactor;

import mainpackage.jivetest.listener.GetPhotosFromFlickerListener;
import mainpackage.jivetest.listener.GetRecentPhotosListener;

public interface IMainViewInteractor {
    void getFlickerPhotos(String photoQuery,GetPhotosFromFlickerListener getPhotosFromFlickerListener);

    void getRecentPhotos(GetRecentPhotosListener getRecentPhotosListener);
}
