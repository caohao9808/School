package org.fengye.school.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentPostQuestionBinding;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
@RuntimePermissions
public class PostQuestionFragment extends BaseFragment<FragmentPostQuestionBinding> {


    public static PostQuestionFragment newInstance() {

        Bundle args = new Bundle();

        PostQuestionFragment fragment = new PostQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostQuestionFragment() {
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("提问");
        binding.topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        Button button = binding.topBar.addRightTextButton("发布", R.id.topbar_right_view);
        button.setCompoundDrawables(getDrawable(R.drawable.ic_add, getColor(R.color.white)), null, null, null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostQuestionFragmentPermissionsDispatcher.postQuestionWithPermissionCheck(PostQuestionFragment.this);
            }
        });


    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void postQuestion() {
        toast("发布");
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void onCameraDenied() {
        toast("拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void onCameraNeverAskAgain() {
        toast("不在提示");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PostQuestionFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode, grantResults);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_post_question;
    }

}
