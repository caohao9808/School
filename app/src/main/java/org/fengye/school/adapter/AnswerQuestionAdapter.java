package org.fengye.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.fengye.school.base.BaseQuickAdapter;
import org.fengye.school.callback.CustomCallback;
import org.fengye.school.databinding.ItemAnswerQuestionBinding;
import org.fengye.school.model.bean.Answer;

public class AnswerQuestionAdapter extends BaseQuickAdapter<Answer, AnswerQuestionAdapter.ViewHolder> {


    private ItemClickListener itemClickListener;
    public AnswerQuestionAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAnswerQuestionBinding.inflate(
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

        ItemAnswerQuestionBinding binding;

        public ViewHolder(@NonNull ItemAnswerQuestionBinding binding) {
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
