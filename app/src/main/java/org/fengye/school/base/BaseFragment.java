package org.fengye.school.base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.qmuiteam.qmui.arch.QMUIFragment;

import org.fengye.school.R;

import es.dmoral.toasty.Toasty;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends ViewModel> extends BaseSimpleFragment<DB> {

    protected VM viewModel;


    protected abstract VM createViewModel();

    @Override
    protected void initDataBindAndViewModel() {
        super.initDataBindAndViewModel();
        viewModel = createViewModel();
    }

}
