package org.fengye.school.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.fengye.school.R;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentAnswerQuestionBinding;
import org.fengye.school.databinding.FragmentPostQuestionBinding;
import org.fengye.school.listener.SimpleListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.AnswerQuestionRepository;
import org.fengye.school.repository.UserRepository;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerQuestionFragment extends BaseSimpleFragment<FragmentAnswerQuestionBinding> {

    private Question question;
    private UserRepository userRepository = new UserRepository();

    private AnswerQuestionRepository answerQuestionRepository = new AnswerQuestionRepository();


    public static AnswerQuestionFragment newInstance(Question question) {

        Bundle args = new Bundle();

        AnswerQuestionFragment fragment = new AnswerQuestionFragment();
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
        }
        if (question == null) {
            popBackStack();
        }
    }

    public AnswerQuestionFragment() {
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("回答");
        binding.topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        final Button button = binding.topBar.addRightTextButton("发布", R.id.topbar_right_view);
        button.setCompoundDrawables(getDrawable(R.drawable.ic_add, getColor(R.color.white)), null, null, null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.answerContent.getText().toString().trim();
                if (content.length() < 5) {
                    toast("回答最少5个字");
                    return;
                }
                if (userRepository.getCurrentUser() == null) {
                    toast("请登录");
                    return;
                }
                Answer answer = new Answer();
                answer.setContent(content);
                answer.setQuestion(question);
                answer.setUser(userRepository.getCurrentUser());
                answerQuestionRepository.postAnswer(answer, new SimpleListener() {
                    @Override
                    public void onStart() {
                        tips("发布中...");
                    }

                    @Override
                    public void onError(String msg) {
                        toast(msg);
                    }

                    @Override
                    public void onSuccess() {
                        toast("发布成功");
                        getMessage().refreshAnswer.setValue(true);
                        popBackStack();
                    }

                    @Override
                    public void onFinish() {
                        cancelTip();
                    }
                });
            }
        });


    }


    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setQuestion(question);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_answer_question;
    }

}
