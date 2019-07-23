package org.fengye.school.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.fengye.school.R;
import org.fengye.school.adapter.AnswerQuestionAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentQuestionBinding;
import org.fengye.school.model.bean.Answer;
import org.fengye.school.vm.AnswerQuestionViewModel;
import org.fengye.school.vm.QuestionViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends BaseFragment<FragmentQuestionBinding> implements OnRefreshLoadMoreListener {


    private static final String TAG = "QuestionFragment";


    private AnswerQuestionAdapter adapter;

    private AnswerQuestionViewModel viewModel;


    public static QuestionFragment newInstance() {

        Bundle args = new Bundle();

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionFragment() {
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("问答");

        binding.topBar.addRightTextButton("提问", R.id.topbar_right_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(PostQuestionFragment.newInstance());
                    }
                });


        //        binding.topBar.addRightView(getRightView(),R.id.topbar_right_view);

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AnswerQuestionViewModel();
            }
        };


        viewModel = ViewModelProviders.of(getActivity(), factory).get(AnswerQuestionViewModel.class);
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
                startFragment(AnswerInfoFragment.newInstance());
            }
        });


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_question;
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
        viewModel.refresh();
    }
}
