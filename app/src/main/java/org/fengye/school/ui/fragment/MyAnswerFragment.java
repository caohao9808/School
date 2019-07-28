package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.fengye.school.R;
import org.fengye.school.adapter.AnswerQuestionAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentAnswerBinding;
import org.fengye.school.databinding.FragmentMyAnswerBinding;
import org.fengye.school.databinding.ItemHomeQuestionBinding;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.QuestionRepository;
import org.fengye.school.vm.AnswerQuestionViewModel;
import org.fengye.school.vm.MyAnswerQuestionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAnswerFragment extends BaseFragment<FragmentMyAnswerBinding, MyAnswerQuestionViewModel> implements OnRefreshLoadMoreListener {


    private static final String TAG = "AnswerFragment";


    private AnswerQuestionAdapter adapter;

    private QuestionRepository questionRepository = new QuestionRepository();



    public static MyAnswerFragment newInstance() {

        Bundle args = new Bundle();

        MyAnswerFragment fragment = new MyAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAnswerFragment() {
    }

    @Override
    protected MyAnswerQuestionViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(MyAnswerQuestionViewModel.class);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("我的回答");

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
        return R.layout.fragment_my_answer;
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
