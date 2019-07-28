package org.fengye.school.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.fengye.school.R;
import org.fengye.school.adapter.VPAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentLoginBinding;
import org.fengye.school.databinding.FragmentMainBinding;
import org.fengye.school.listener.AbsUpdateListener;
import org.fengye.school.model.bmob.User;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.UserViewModel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, UserViewModel> {

    private UserRepository repository = new UserRepository();

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("登录");
        binding.topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();

        binding.qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(getContext())
                        .getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                String uid = map.get("uid");
                                String accessToken = map.get("accessToken");
                                String expiration = map.get("expiration");
                                String gender = map.get("gender");
                                String name = map.get("name");
                                String iconurl = map.get("iconurl");
                                thirdSingUpLogin("qq", accessToken, expiration, uid,name,iconurl);
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                toastError(throwable.toString());
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {

                            }
                        });
            }
        });

    }

    /**
     * 第三方平台一键注册或登录
     *
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private void thirdSingUpLogin(String snsType, String accessToken, String expiresIn, final String userId, final String name, final String iconUrl) {

        tips("登录中...");

        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject user, BmobException e) {
                cancelTip();
                if (e == null) {
                    User currentUser = repository.getCurrentUser();
                    currentUser.setNickname(name);
                    currentUser.setAvatar(iconUrl);
                    currentUser.update(currentUser.getObjectId(),new AbsUpdateListener());
                    toastSuccess("登录成功");
                    viewModel.user.set(currentUser);
                    popBackStack();
                } else {
                    toastError("登录失败");
                }
            }
        });
    }


    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(getActivity(),viewModelFactory).get(UserViewModel.class);
    }
}
