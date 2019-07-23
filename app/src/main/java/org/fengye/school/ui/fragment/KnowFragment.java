package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentKnowBinding;
import org.fengye.school.databinding.FragmentQuestionBinding;
import org.fengye.school.model.bean.TodayWord;
import org.fengye.school.vm.KnowViewModel;
import org.fengye.school.vm.QuestionViewModel;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowFragment extends BaseFragment<FragmentKnowBinding> {

    private KnowViewModel viewModel;

    public static KnowFragment newInstance() {

        Bundle args = new Bundle();

        KnowFragment fragment = new KnowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public KnowFragment() {
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.refreshWord();
    }


    @Override
    protected void initView() {
        super.initView();
        binding.topBar.setTitle("知识");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KnowViewModel();
            }
        };

        viewModel = ViewModelProviders.of(getActivity(), factory).get(KnowViewModel.class);

        binding.setViewModel(viewModel);

    }

    @Override
    protected void initListener() {
        super.initListener();
        viewModel.getTodayWord().observe(this, new Observer<TodayWord>() {
            @Override
            public void onChanged(TodayWord todayWord) {
                binding.setTodayWord(todayWord);
            }
        });


    }



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_know;
    }
}
