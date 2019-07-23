package org.fengye.school.repository;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository extends BaseRepository<List<Question>> {

    @Override
    public List<Question> getDataByPosition(int position, int size) {

        List<Question> questions = new ArrayList<>();
        for (int i = position; i < position + size; i++) {
            Question question = new Question();
            question.setTitle("提出问题" + i+"，问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
            questions.add(question);
        }

        return questions;
    }

    @Override
    public void getDataByPosition(final int position, final int size, final QueryListener<List<Question>> listener) {

        listener.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = new ArrayList<>();
                for (int i = position; i < position + size; i++) {
                    Question question = new Question();
                    question.setTitle("提出问题" + i+"，问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
                    questions.add(question);
                }
                listener.onSuccess(questions);
                listener.onFinish();

            }
        }).start();


    }
}
