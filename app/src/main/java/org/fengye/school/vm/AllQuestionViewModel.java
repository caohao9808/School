package org.fengye.school.vm;

import org.fengye.school.base.BasePageViewModel;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.AnswerQuestionRepository;
import org.fengye.school.repository.QuestionRepository;

import java.util.List;

public class AllQuestionViewModel extends BasePageViewModel<Question> {


    private QuestionRepository repository;

    private Question question;


    public AllQuestionViewModel() {
        repository = new QuestionRepository();
    }

    @Override
    protected void getData(final boolean isRefresh) {

        repository.getDataByPosition(pageUtil.currentPage * pageUtil.pageSize,
                pageUtil.pageSize,
                new AbsQueryListener<List<Question>>() {
                    @Override
                    public void onSuccess(List<Question> questions) {
                        super.onSuccess(questions);
                        hasMoreData = !(questions.size() < pageUtil.pageSize);
                        if (isRefresh) {
                            getNewLiveData().setValue(questions);
                        } else {
                            getMoreLiveData().setValue(questions);
                        }
                    }
                });
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
