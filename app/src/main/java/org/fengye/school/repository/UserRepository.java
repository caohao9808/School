package org.fengye.school.repository;

import org.fengye.school.BuildConfig;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bmob.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

public class UserRepository {


    public User getCurrentUser() {

        User currentUser = BmobUser.getCurrentUser(User.class);
        return currentUser;
    }

    public boolean isLogin() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        return currentUser != null;
    }

    public void fetchUserInfo(final QueryListener<User> listener) {
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    final User user1 = BmobUser.getCurrentUser(User.class);
                    listener.onSuccess(user1);
                } else {
                    listener.onError(e.toString());
                }
            }
        });
    }

}
