package org.fengye.school.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentAnswerInfoBinding;
import org.fengye.school.databinding.FragmentPostQuestionBinding;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerInfoFragment extends BaseFragment<FragmentAnswerInfoBinding> {


    public static AnswerInfoFragment newInstance() {

        Bundle args = new Bundle();

        AnswerInfoFragment fragment = new AnswerInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AnswerInfoFragment() {
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


    }



    @Override
    protected void initListener() {
        super.initListener();
        binding.allAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(QuestionInfoFragment.newInstance());
            }
        });
        binding.questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(QuestionInfoFragment.newInstance());
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_answer_info;
    }

}
