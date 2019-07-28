package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.fengye.school.R;
import org.fengye.school.adapter.AnswerListAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentQuestionInfoBinding;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.QuestionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionInfoFragment extends BaseFragment<FragmentQuestionInfoBinding, QuestionViewModel> implements OnRefreshLoadMoreListener {

    private Question question;
    private AnswerListAdapter adapter;

    private UserRepository userRepository = new UserRepository();

    public static QuestionInfoFragment newInstance(Question question) {

        Bundle args = new Bundle();

        QuestionInfoFragment fragment = new QuestionInfoFragment();
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

    public QuestionInfoFragment() {
    }

    @Override
    protected QuestionViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(QuestionViewModel.class);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("所有回答");
        binding.topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        adapter = new AnswerListAdapter();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initListener() {
        super.initListener();


        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.recycleView.setAdapter(adapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(this);



        viewModel.getNewLiveData().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                adapter.setNewData(answers);
                binding.refreshLayout.finishRefresh(0, true, !viewModel.isHasMoreData());
            }
        });
        viewModel.getMoreLiveData().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                adapter.addData(answers);
                binding.refreshLayout.finishLoadMore(0, true, !viewModel.isHasMoreData());
            }
        });
        binding.answerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRepository.isLogin()) {
                    startFragment(AnswerQuestionFragment.newInstance(question));
                } else {
                    toast("请先登录");
                }
            }
        });

        getMessage().refreshAnswer.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.refreshLayout.autoRefresh();
                }
            }
        });

        adapter.setItemClickListener(new AnswerListAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position, Answer answer) {
//                toast(position + "");
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.setQuestion(question);
        binding.setQuestion(question);
        binding.refreshLayout.autoRefresh();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_question_info;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.loadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.refresh();
    }
}
