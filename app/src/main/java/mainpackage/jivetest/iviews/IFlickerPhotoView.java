package mainpackage.jivetest.iviews;

import android.content.Context;

import mainpackage.jivetest.model.UserInfo;


public interface IFlickerPhotoView {
    Context getContext();
    void showProgressDialog(String progressDialogTitle);
    void hideProgressDialog();
    void updateUserName(UserInfo username);
    void updatePhotoComments(String comments);
}
