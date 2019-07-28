package org.fengye.school.vm;

import org.fengye.school.base.BasePageViewModel;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Question;
import org.fengye.school.repository.QuestionRepository;
import org.fengye.school.repository.UserRepository;

import java.util.List;

public class MyQuestionViewModel extends BasePageViewModel<Question> {

    private UserRepository userRepository = new UserRepository();

    private QuestionRepository repository;

    private Question question;


    public MyQuestionViewModel() {
        repository = new QuestionRepository();
    }

    @Override
    protected void getData(final boolean isRefresh) {


        repository.getDataByPosition(userRepository.getCurrentUser(), pageUtil.currentPage * pageUtil.pageSize,
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
