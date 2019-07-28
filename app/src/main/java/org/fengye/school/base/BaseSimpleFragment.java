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
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.fengye.school.R;
import org.fengye.school.util.ViewModelFactoryUtil;
import org.fengye.school.vm.MessageViewModel;

import es.dmoral.toasty.Toasty;

public abstract class BaseSimpleFragment<DB extends ViewDataBinding> extends QMUIFragment {

    protected Integer themeColor;
    protected DB binding;

    protected ViewModelFactoryUtil viewModelFactory = ViewModelFactoryUtil.getInstance();

    protected QMUITipDialog tipDialog;

    protected MessageViewModel messageViewModel;


    @Nullable
    @Override
    public View onCreateView() {

        init();
        return binding.getRoot();
    }


    protected void init() {
        initDataBindAndViewModel();
        initView();
        initListener();
        initData();
    }


    protected void initDataBindAndViewModel() {
        messageViewModel = ViewModelProviders.of(getActivity()).get(MessageViewModel.class);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentViewId(), null, false);

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

    public void toast(Object o) {
        toast(o.toString());
    }


    public void toastSuccess(String msg) {
        Toasty.success(getContext(), msg, Toasty.LENGTH_SHORT, false).show();
    }

    public void toastError(String msg) {
        Toasty.error(getContext(), msg, Toasty.LENGTH_SHORT, false).show();
    }


    public void toast(String msg) {

        setThemeColor();

        Toasty.custom(getContext(), msg,
                null,
                themeColor,
                getColor(R.color.white),
                Toasty.LENGTH_SHORT, false, true).show();
    }


    public void tips(String msg, boolean cancelable) {
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create(cancelable);
        tipDialog.show();
    }

    public void tips() {
        tips("加载中...", true);
    }

    public void tips(String msg) {
        tips(msg, true);
    }

    public void cancelTip() {
        if (tipDialog != null && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }


    public MessageViewModel getMessage() {
        return messageViewModel;
    }
}
