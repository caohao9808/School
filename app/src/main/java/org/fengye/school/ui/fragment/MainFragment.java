package org.fengye.school.ui.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import org.fengye.school.R;
import org.fengye.school.adapter.VPAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.base.BaseSimpleFragment;
import org.fengye.school.databinding.FragmentMainBinding;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.listener.AbsUpdateListener;
import org.fengye.school.model.bmob.User;
import org.fengye.school.repository.UserRepository;
import org.fengye.school.vm.UserViewModel;

import java.util.ArrayList;
public class MainFragment extends BaseFragment<FragmentMainBinding, UserViewModel> {

    private UserRepository repository = new UserRepository();

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        super.initView();
        initPager();
    }

    @Override
    protected void initData() {
        super.initData();
        User currentUser = repository.getCurrentUser();
        if (currentUser != null) {
            viewModel.user.set(currentUser);
            repository.fetchUserInfo(new AbsQueryListener<User>() {
                @Override
                public void onSuccess(User user) {
                    super.onSuccess(user);
                    viewModel.user.set(user);
                }
            });
        }

    }

    private void initPager() {


        ArrayList<BaseSimpleFragment> fragments = new ArrayList<>();
        fragments.add(AnswerFragment.newInstance());
        fragments.add(KnowFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager(), fragments);

        binding.pager.setAdapter(vpAdapter);
        binding.bottomBar.setupWithViewPager(binding.pager, true);
        binding.pager.setOffscreenPageLimit(2);
        binding.pager.setCurrentItem(0);
//        binding.bottomBar.setCurrentItem()
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(getActivity(),viewModelFactory).get(UserViewModel.class);
    }
}
