package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentMineBinding;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.repository.WordRepository;
import org.fengye.school.ui.view.CustomGroupListView;
import org.fengye.school.vm.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<FragmentMineBinding, UserViewModel> {

    private UserRepository userRepository = new UserRepository();
    private WordRepository wordRepository = new WordRepository();

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
    }

    @Override
    protected void initView() {
        super.initView();
//        binding.topBar.setTitle("我的");

        List<CustomGroupListView.ItemBean> questions = new ArrayList<>();
        questions.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_questions), "我的提问", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRepository.isLogin()) {
                    startFragment(MyQuestionFragment.newInstance());
                } else {
                    toast("请先登录");
                }
            }
        }));
        questions.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_answer), "我的回答", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRepository.isLogin()) {
                    startFragment(MyAnswerFragment.newInstance());
                } else {
                    toast("请先登录");
                }
            }
        }));
        binding.groupList.addSession("问答", questions);

        List<CustomGroupListView.ItemBean> knowledges = new ArrayList<>();
        knowledges.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_inventory), "已学习", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wordRepository.getLearnPlan() == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(0));
            }
        }));
        knowledges.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_study_add), "不认识", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wordRepository.getLearnPlan()  == null) {
                    toast("请先选择学习计划");
                    return;
                }
                startFragment(WordRecordFragment.newInstance(3));
            }
        }));
        binding.groupList.addSession("知识", knowledges);


        List<CustomGroupListView.ItemBean> soft = new ArrayList<>();
        soft.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_about), "关于", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("曹灏");
            }
        }));
        binding.groupList.addSession("其他", soft);


    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void initData() {
        super.initData();
        binding.setViewModel(viewModel);
        if (viewModel.user.get() == null) {
            binding.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFragment(LoginFragment.newInstance());
                }
            });
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
    }
}
