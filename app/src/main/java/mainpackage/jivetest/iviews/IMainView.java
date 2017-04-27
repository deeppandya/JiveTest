package mainpackage.jivetest.iviews;

import android.content.Context;

import java.util.List;

import mainpackage.jivetest.model.FlickerPhotos;

public interface IMainView {
    Context getContext();
    void setFlickerPhotoListAdapter(List<FlickerPhotos> flickerPhotoses);
    void showProgressDialog(String progressDialogTitle);
    void hideProgressDialog();
    void openPhotoFullView(FlickerPhotos flickerPhotos);
    void updateEmptySearch(int visibility);
}
