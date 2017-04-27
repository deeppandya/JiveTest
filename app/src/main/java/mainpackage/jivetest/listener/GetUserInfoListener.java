package mainpackage.jivetest.listener;

import mainpackage.jivetest.model.UserInfo;

public interface GetUserInfoListener {
    void beforeGetUserInfo();

    void afterGetUserInfo(UserInfo userInfo);

    void onError();
}
