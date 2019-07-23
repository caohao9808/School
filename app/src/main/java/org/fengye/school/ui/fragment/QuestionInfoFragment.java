package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.fengye.school.R;
import org.fengye.school.adapter.AnswerListAdapter;
import org.fengye.school.adapter.AnswerQuestionAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentAnswerInfoBinding;
import org.fengye.school.databinding.FragmentQuestionInfoBinding;
import org.fengye.school.model.bean.Answer;
import org.fengye.school.vm.QuestionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionInfoFragment extends BaseFragment<FragmentQuestionInfoBinding> implements OnRefreshLoadMoreListener {

    private AnswerListAdapter adapter;
    private QuestionViewModel viewModel;

    public static QuestionInfoFragment newInstance() {

        Bundle args = new Bundle();

        QuestionInfoFragment fragment = new QuestionInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionInfoFragment() {
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

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new QuestionViewModel();
            }
        };


        viewModel = ViewModelProviders.of(getActivity(), factory).get(QuestionViewModel.class);
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
                binding.refreshLayout.finishRefresh(0,true,!viewModel.isHasMoreData());
            }
        });
        viewModel.getMoreLiveData().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                adapter.addData(answers);
                binding.refreshLayout.finishLoadMore(0,true,!viewModel.isHasMoreData());
            }
        });




        adapter.setItemClickListener(new AnswerListAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position, Answer answer) {
                toast(position + "");
            }
        });


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
