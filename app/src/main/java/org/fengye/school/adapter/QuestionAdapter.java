package org.fengye.school.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fengye.school.base.BaseQuickAdapter;
import org.fengye.school.databinding.ItemQuestionBinding;
import org.fengye.school.model.bmob.Question;

public class QuestionAdapter extends BaseQuickAdapter<Question, QuestionAdapter.ViewHolder> {


    private ItemClickListener itemClickListener;

    public QuestionAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Question item = getItem(position);
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

        ItemQuestionBinding binding;

        public ViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Question question) {
            binding.setQuestion(question);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(int position, Question question);
    }
}
