package org.fengye.school.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.fengye.school.model.sqlite.Words;

import cn.bmob.v3.BmobObject;

public class WordCallback extends DiffUtil.ItemCallback<Words> {


    @Override
    public boolean areItemsTheSame(@NonNull Words oldItem, @NonNull Words newItem) {
        return oldItem.getWord().equals(newItem.getWord());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Words oldItem, @NonNull Words newItem) {
        return oldItem.equals(newItem);
    }

}
