package org.fengye.school.ui.fragment;

import android.os.Bundle;
import org.fengye.school.R;
import org.fengye.school.adapter.VPAdapter;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentMainBinding;
import java.util.ArrayList;
public class MainFragment extends BaseFragment<FragmentMainBinding> {


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

    private void initPager() {


        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(QuestionFragment.newInstance());
        fragments.add(KnowFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager(), fragments);

        binding.pager.setAdapter(vpAdapter);
        binding.bottomBar.setupWithViewPager(binding.pager, true);

    }

}
