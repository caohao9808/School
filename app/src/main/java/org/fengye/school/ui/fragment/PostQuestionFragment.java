package org.fengye.school.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.fengye.school.R;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentPostQuestionBinding;
import org.fengye.school.listener.AbsSimpleListener;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.QuestionRepository;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.UserViewModel;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
@RuntimePermissions
public class PostQuestionFragment extends BaseSimpleFragment<FragmentPostQuestionBinding> {

    private UserRepository userRepository = new UserRepository();
    private QuestionRepository questionRepository = new QuestionRepository();

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

        String title = binding.questionTitle.getText().toString().trim();
        if (title.length() < 5) {
            toast("问题不可少于5个字");
            return;
        }

        if (userRepository.getCurrentUser() == null) {
            toast("请登录");
            return;
        }

        Question question = new Question();
        question.setTitle(title);
        question.setContent(binding.questionContent.getText().toString());
        question.setUser(userRepository.getCurrentUser());
        question.setNumberOfAnswers(0);

        questionRepository.postQuestion(question, new AbsSimpleListener() {
            @Override
            public void onStart() {
                super.onStart();
                tips("发布中...");
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                toast("发布失败，" + msg);
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                toast("发布成功");
                getMessage().refreshQuestion.setValue(true);
                getMessage().refreshAnswer.setValue(true);
                popBackStack();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                cancelTip();
            }
        });

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
        PostQuestionFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
