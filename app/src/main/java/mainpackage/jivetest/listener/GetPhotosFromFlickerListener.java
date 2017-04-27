package mainpackage.jivetest.listener;

import java.util.List;

import mainpackage.jivetest.model.FlickerPhotos;

public interface GetPhotosFromFlickerListener {
    void beforeGetPhotosFromFlicker();

    void afterGetPhotosFromFlicker(List<FlickerPhotos> flickerPhotosList);

    void onError();
}
