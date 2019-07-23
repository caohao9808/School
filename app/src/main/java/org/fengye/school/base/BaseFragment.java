package org.fengye.school.base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.qmuiteam.qmui.arch.QMUIFragment;

import org.fengye.school.R;

import es.dmoral.toasty.Toasty;

public abstract class BaseFragment<DB extends ViewDataBinding> extends QMUIFragment {

    public Integer themeColor;

    protected DB binding;

    @Nullable
    @Override
    public View onCreateView() {

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentViewId(), null, false);
        init();
        return binding.getRoot();
    }


    protected void init() {
        initView();
        initListener();
        initData();
    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }

    protected abstract int getContentViewId();


    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    public Drawable getDrawable(@DrawableRes int id, int tintColor) {
        Drawable drawable = getDrawable(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(tintColor);
        }
        return drawable;
    }

    public Drawable getDrawableWithTheme(@DrawableRes int id) {
        Drawable drawable = getDrawable(id);
        setThemeColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(themeColor);
        }
        return drawable;
    }



    public void setThemeColor() {
        if (themeColor == null) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.app_primary_color, typedValue, true);
            themeColor = typedValue.data;
        }
    }

    public void toast(String msg) {

        setThemeColor();

        Toasty.custom(getContext(), msg,
                null,
                themeColor,
                getColor(R.color.white),
                Toasty.LENGTH_SHORT, false, true).show();
    }
}
