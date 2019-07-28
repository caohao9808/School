package org.fengye.school.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fengye.school.base.BaseQuickAdapter;
import org.fengye.school.databinding.ItemAnswerBinding;
import org.fengye.school.model.bmob.Answer;

public class AnswerListAdapter extends BaseQuickAdapter<Answer, AnswerListAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    public AnswerListAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Answer item = getItem(position);
        holder.bind(item);
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClickListener(position,item);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemAnswerBinding binding;

        public ViewHolder(@NonNull ItemAnswerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Answer answer) {
            binding.setAnswer(answer);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(int position, Answer answer);
    }
}
