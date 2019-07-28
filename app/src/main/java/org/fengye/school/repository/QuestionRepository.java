package org.fengye.school.repository;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.listener.AbsUpdateListener;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.listener.SimpleListener;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.model.bmob.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class QuestionRepository {

    public List<Question> getDataByPosition(int position, int size) {

        List<Question> questions = new ArrayList<>();
        for (int i = position; i < position + size; i++) {
            Question question = new Question();
            question.setTitle("提出问题" + i + "，问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
            questions.add(question);
        }

        return questions;
    }

    public void getDataByPosition(final int position, final int size, final QueryListener<List<Question>> listener) {

        listener.onStart();
        BmobQuery<Question> query = new BmobQuery<>();
        query.include("user")
                .order("-createdAt")
                .setSkip(position)
                .setLimit(size)
                .findObjects(new FindListener<Question>() {
                    @Override
                    public void done(List<Question> list, BmobException e) {
                        if (e == null) {
                            listener.onSuccess(list);
                        } else {
                            listener.onError(e.toString());

                        }
                        listener.onFinish();
                    }
                });
    }

    public void getDataByPosition(User user, final int position, final int size, final QueryListener<List<Question>> listener) {

        listener.onStart();
        BmobQuery<Question> query = new BmobQuery<>();
        if (user != null) {
            query.addWhereEqualTo("user", new User(user.getObjectId()));
        }
        query.include("user")
                .order("-createdAt")
                .setSkip(position)
                .setLimit(size)
                .findObjects(new FindListener<Question>() {
                    @Override
                    public void done(List<Question> list, BmobException e) {
                        if (e == null) {
                            listener.onSuccess(list);
                        } else {
                            listener.onError(e.toString());

                        }
                        listener.onFinish();
                    }
                });
    }


    public void getNewData(int size, final QueryListener<List<Question>> listener) {
        getDataByPosition(0, size, listener);
    }


    public void postQuestion(final Question question, final SimpleListener listener) {
        listener.onStart();
        question.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    User user = question.getUser();
                    user.increment("numberOfQuestion");
                    user.update(new AbsUpdateListener());
                    listener.onSuccess();
                } else {
                    listener.onError(e.toString());
                }
                listener.onFinish();

            }
        });
    }

}
