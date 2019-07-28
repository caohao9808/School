package org.fengye.school.repository;

import org.fengye.school.listener.AbsUpdateListener;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.listener.SimpleListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.model.bmob.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AnswerQuestionRepository {


    public List<Answer> getDataByPosition(int position, int size) {


        List<Answer> answers = new ArrayList<>();

        for (int i = position; i < position + size; i++) {

            Question question = new Question();
            question.setTitle("提出问题" + i + "问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
            User user = new User();
            user.setNickname("湖北工程学院" + i);
            user.setAvatar("https://api.uomg.com/api/rand.avatar?t=" + i);

            Answer answer = new Answer();
            answer.setContent(i + "，回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复");
            answer.setQuestion(question);
            answer.setUser(user);
            answers.add(answer);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return answers;
    }

    public void getDataByPosition(Question question, final int position, final int size, final QueryListener<List<Answer>> listener) {

        listener.onStart();
        BmobQuery<Answer> query = new BmobQuery<>();
        if (question != null) {
            query.addWhereEqualTo("question", new Question(question.getObjectId()));
        }

        query.include("user,question,question.user")
                .order("-createdAt")
                .setSkip(position)
                .setLimit(size)
                .findObjects(new FindListener<Answer>() {
                    @Override
                    public void done(List<Answer> list, BmobException e) {
                        if (e == null) {
                            listener.onSuccess(list);
                        } else {
                            listener.onError(e.toString());

                        }
                        listener.onFinish();
                    }
                });
    }


    public void getDataByPosition(User user, Question question, final int position, final int size, final QueryListener<List<Answer>> listener) {

        listener.onStart();
        BmobQuery<Answer> query = new BmobQuery<>();
        if (user != null) {
            query.addWhereEqualTo("user", new User(user.getObjectId()));
        }
        if (question != null) {
            query.addWhereEqualTo("question", new Question(question.getObjectId()));
        }

        query.include("user,question,question.user")
                .order("-createdAt")
                .setSkip(position)
                .setLimit(size)
                .findObjects(new FindListener<Answer>() {
                    @Override
                    public void done(List<Answer> list, BmobException e) {
                        if (e == null) {
                            listener.onSuccess(list);
                        } else {
                            listener.onError(e.toString());

                        }
                        listener.onFinish();
                    }
                });
    }

    public void postAnswer(final Answer answer, final SimpleListener listener) {
        listener.onStart();
        answer.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    Question question = answer.getQuestion();
                    User user = answer.getUser();
                    question.increment("numberOfAnswers");
                    user.increment("numberOfAnswers");
                    question.update(new AbsUpdateListener());
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
