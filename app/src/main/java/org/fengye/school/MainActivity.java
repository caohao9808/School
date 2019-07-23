package org.fengye.school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import org.fengye.school.base.BaseActivity;
import org.fengye.school.base.BaseFragment;
import org.fengye.school.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected int getContextViewId() {
        return R.id.main_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);

        BaseFragment fragment = getFirstFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
    private BaseFragment getFirstFragment() {
        return MainFragment.newInstance();
    }
}
