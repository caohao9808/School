package org.fengye.school.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.fengye.school.callback.WordCallback;
import org.fengye.school.databinding.ItemWordRecordBinding;
import org.fengye.school.model.sqlite.Words;

public class RecordAdapter extends PagedListAdapter<Words,RecordAdapter.ViewHolder> {

    public RecordAdapter(@NonNull WordCallback callback) {
        super(callback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemWordRecordBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Words item = getItem(position);
        holder.bind(item);

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemWordRecordBinding binding;

        public ViewHolder(@NonNull ItemWordRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Words item) {
            binding.setWord(item);
        }
    }
}
