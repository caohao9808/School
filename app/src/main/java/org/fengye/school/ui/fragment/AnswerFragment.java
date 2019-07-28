package org.fengye.school.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.fengye.school.R;
import org.fengye.school.adapter.AnswerQuestionAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentAnswerBinding;
import org.fengye.school.databinding.ItemHomeQuestionBinding;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.QuestionRepository;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.AnswerQuestionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends BaseFragment<FragmentAnswerBinding, AnswerQuestionViewModel> implements OnRefreshLoadMoreListener {

    private UserRepository userRepository = new UserRepository();

    private static final String TAG = "AnswerFragment";

    private AnswerQuestionAdapter adapter;

    private QuestionRepository questionRepository = new QuestionRepository();


    public static AnswerFragment newInstance() {


        Bundle args = new Bundle();

        AnswerFragment fragment = new AnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AnswerFragment() {


    }

    @Override
    protected AnswerQuestionViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AnswerQuestionViewModel.class);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("问答");
        binding.topBar.addRightTextButton("提问", R.id.topbar_right_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userRepository.isLogin()) {
                            startFragment(PostQuestionFragment.newInstance());
                        } else {
                            toast("请先登录");
                        }
                    }
                });
        binding.topBar.addLeftTextButton("问题", R.id.topbar_left_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(AllQuestionFragment.newInstance());
                    }
                });


        //        binding.topBar.addRightView(getRightView(),R.id.topbar_right_view);


        adapter = new AnswerQuestionAdapter();


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

        getMessage().refreshAnswer.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.refreshLayout.autoRefresh();
                }
            }
        });


        adapter.setItemClickListener(new AnswerQuestionAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position, Answer answer) {
                startFragment(AnswerInfoFragment.newInstance(answer));
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        binding.refreshLayout.autoRefresh();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_answer;
    }

    private View getRightView() {
        View view = View.inflate(getContext(), R.layout.topbar_right_view, null);
        return view;

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.loadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        questionRepository.getNewData(3, new AbsQueryListener<List<Question>>() {
            @Override
            public void onSuccess(List<Question> questions) {
                super.onSuccess(questions);
                binding.newQuestion.removeAllViews();
                for (final Question question : questions) {
                    ItemHomeQuestionBinding inflate = ItemHomeQuestionBinding.inflate(LayoutInflater.from(getContext()),
                            binding.newQuestion, false);
                    inflate.setQuestion(question);
                    inflate.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startFragment(QuestionInfoFragment.newInstance(question));
                        }
                    });
                    binding.newQuestion.addView(inflate.getRoot());
                }
                if (questions.size() > 0) {
                    binding.newLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.newLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                binding.newLayout.setVisibility(View.GONE);
            }
        });
        viewModel.refresh();

    }
}
