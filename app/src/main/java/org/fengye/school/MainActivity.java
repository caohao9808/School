package org.fengye.school;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.umeng.socialize.UMShareAPI;

import org.fengye.school.base.BaseActivity;
import org.fengye.school.base.BaseSimpleFragment;
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

        BaseSimpleFragment fragment = getFirstFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
    private BaseSimpleFragment getFirstFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
