package org.fengye.school.util;

import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import org.fengye.school.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class BindingUtil {

    @BindingAdapter({"imageUrl"})
    public static void imageUrl(AppCompatImageView imageView, String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        RequestOptions requestOptions = RequestOptions.bitmapTransform(new BlurTransformation(8, 1))
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }
    @BindingAdapter({"roundAvatarUrl"})
    public static void roundAvatarUrl(QMUIRadiusImageView imageView, String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }

}
