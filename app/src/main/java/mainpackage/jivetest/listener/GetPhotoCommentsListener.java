package mainpackage.jivetest.listener;

import java.util.List;

import mainpackage.jivetest.model.PhotoComments;

public interface GetPhotoCommentsListener {
    void beforeGetPhotoComments();

    void afterGetPhotoComments(List<PhotoComments> photoComments);

    void onError();
}
