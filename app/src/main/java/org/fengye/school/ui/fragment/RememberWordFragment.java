package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentRememberWordBinding;
import org.fengye.school.vm.KnowViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RememberWordFragment extends BaseFragment<FragmentRememberWordBinding,KnowViewModel> {




    public static RememberWordFragment newInstance() {

        Bundle args = new Bundle();

        RememberWordFragment fragment = new RememberWordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RememberWordFragment() {
    }

    @Override
    protected void initData() {
        super.initData();


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

        binding.setViewModel(viewModel);


        viewModel.refreshWordField();

//        binding.setWord(words);
//        binding.topBar.setTitle("characteristic");

    }

    @Override
    protected void initListener() {
        super.initListener();

        binding.know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.knowWord();
            }
        });

        binding.notKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.notKnowWord();

            }
        });

    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_remember_word;
    }

    @Override
    protected KnowViewModel createViewModel() {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(KnowViewModel.class);
    }
}
