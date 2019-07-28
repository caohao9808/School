package org.fengye.school.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cn.bmob.v3.BmobObject;

public class CustomCallback<M extends BmobObject> extends DiffUtil.ItemCallback<M > {


    @Override
    public boolean areItemsTheSame(@NonNull M oldItem, @NonNull M newItem) {
        return oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull M oldItem, @NonNull M newItem) {
        return oldItem==newItem;
    }

}
