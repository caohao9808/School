package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.fengye.school.R;
import org.fengye.school.adapter.RecordAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.callback.WordCallback;
import org.fengye.school.databinding.FragmentWordRecordBinding;
import org.fengye.school.model.sqlite.Words;
import org.fengye.school.vm.RecordViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordRecordFragment extends BaseFragment<FragmentWordRecordBinding, RecordViewModel> {

    private RecordAdapter adapter;

    private int type = 0;

    public static WordRecordFragment newInstance(int type) {

        Bundle args = new Bundle();

        WordRecordFragment fragment = new WordRecordFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public WordRecordFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type", 0);
        }

    }

    @Override
    protected RecordViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(RecordViewModel.class);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        adapter = new RecordAdapter(new WordCallback());
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

        LiveData<PagedList<Words>> data = null;

        switch (type) {
            case 0:
                binding.topBar.setTitle("已学习");
                data = viewModel.getAllAlreadyLearn();
                break;
            case 1:
                binding.topBar.setTitle("今日单词");
                data = viewModel.getTodayWords();
                break;
            case 2:
                binding.topBar.setTitle("今日已学");
                data = viewModel.getTodayAlreadyLearn();
                break;
            case 3:
                binding.topBar.setTitle("不认识");
                data = viewModel.getNotKnowWords();
                break;
            default:
                binding.topBar.setTitle("已学习");
                data = viewModel.getAllAlreadyLearn();
        }

        data.observe(this, new Observer<PagedList<Words>>() {
            @Override
            public void onChanged(PagedList<Words> words) {
                adapter.submitList(words);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_word_record;
    }


}
