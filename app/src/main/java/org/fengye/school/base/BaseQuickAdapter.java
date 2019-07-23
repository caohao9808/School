package org.fengye.school.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickAdapter<T, VH  extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mData = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void addData(List<T> data) {
        mData.addAll(data);
        notifyItemRangeInserted(mData.size()-data.size(),data.size());
    }
}
