package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.fengye.school.R;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentAnswerInfoBinding;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.AnswerQuestionViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerInfoFragment extends BaseSimpleFragment<FragmentAnswerInfoBinding> {

    private UserRepository userRepository = new UserRepository();
    private Answer answer;

    public static AnswerInfoFragment newInstance(Answer answer) {

        Bundle args = new Bundle();

        AnswerInfoFragment fragment = new AnswerInfoFragment();
        args.putSerializable("answer", answer);
        fragment.setArguments(args);
        return fragment;
    }

    public AnswerInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answer = (Answer) getArguments().getSerializable("answer");
        }
        if (answer == null) {
            popBackStack();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("回答详情");
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
        binding.allAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(QuestionInfoFragment.newInstance(answer.getQuestion()));
            }
        });
        binding.questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(QuestionInfoFragment.newInstance(answer.getQuestion()));
            }
        });



        binding.answerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRepository.isLogin()) {
                    startFragment(AnswerQuestionFragment.newInstance(answer.getQuestion()));
                } else {
                    toast("请先登录");
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setAnswer(answer);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_answer_info;
    }

}
