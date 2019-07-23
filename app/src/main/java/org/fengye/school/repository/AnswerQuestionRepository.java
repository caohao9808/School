package org.fengye.school.repository;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bean.Answer;
import org.fengye.school.model.bean.Question;
import org.fengye.school.model.bean.User;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuestionRepository extends BaseRepository<List<Answer>> {

    @Override
    public List<Answer> getDataByPosition(int position, int size) {


        List<Answer> answers = new ArrayList<>();

        for (int i = position; i < position + size; i++) {

            Question question = new Question();
            question.setTitle("提出问题" + i + "问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
            User user = new User();
            user.setNickname("湖北工程学院" + i);
            user.setAvatarUrl("https://api.uomg.com/api/rand.avatar?t=" + i);

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

    @Override
    public void getDataByPosition(final int position, final int size, final QueryListener<List<Answer>> listener) {
        listener.onStart();

        List<Answer> answers = new ArrayList<>();

        for (int i = position; i < position + size && i < 45; i++) {

            Question question = new Question();
            question.setTitle("提出问题" + i + "问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题问题？");
            User user = new User();
            user.setNickname("湖北工程学院" + i);
            user.setAvatarUrl("https://api.uomg.com/api/rand.avatar?t=" + i);

            Answer answer = new Answer();
            answer.setContent(i + "，回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复");
            answer.setQuestion(question);
            answer.setUser(user);
            answers.add(answer);
        }
        listener.onSuccess(answers);
        listener.onFinish();


    }

}
