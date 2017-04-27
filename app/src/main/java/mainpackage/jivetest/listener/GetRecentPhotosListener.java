package mainpackage.jivetest.listener;

import java.util.List;

import mainpackage.jivetest.model.FlickerPhotos;

public interface GetRecentPhotosListener {
    void beforeGetRecntPhotos();

    void afterGetRecentPhotos(List<FlickerPhotos> flickerPhotosList);

    void onError();
}
