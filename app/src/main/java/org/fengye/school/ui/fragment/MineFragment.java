package org.fengye.school.ui.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListSectionHeaderFooterView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.fengye.school.R;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.databinding.FragmentMineBinding;
import org.fengye.school.databinding.FragmentQuestionBinding;
import org.fengye.school.ui.view.CustomGroupListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<FragmentMineBinding> {

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void initView() {
        super.initView();
//        binding.topBar.setTitle("我的");

        List<CustomGroupListView.ItemBean> questions = new ArrayList<>();
        questions.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_questions),"我的提问",listener));
        questions.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_answer),"我的回答",listener));
        binding.groupList.addSession("问答",questions);

        List<CustomGroupListView.ItemBean> knowledges = new ArrayList<>();
        knowledges.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_study),"学习",listener));
        knowledges.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_inventory),"清单",listener));
        knowledges.add(new CustomGroupListView.ItemBean(getDrawableWithTheme(R.drawable.ic_study_add),"添加",listener));
        binding.groupList.addSession("知识",knowledges);



    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }
}
