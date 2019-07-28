package org.fengye.school.vm;

import org.fengye.school.base.BasePageViewModel;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Answer;
import org.fengye.school.repository.AnswerQuestionRepository;
import org.fengye.school.repository.UserRepository;

import java.util.List;

public class MyAnswerQuestionViewModel extends BasePageViewModel<Answer> {

    private UserRepository userRepository = new UserRepository();

    private AnswerQuestionRepository answerQuestionRepository;


    public MyAnswerQuestionViewModel() {
        answerQuestionRepository = new AnswerQuestionRepository();
    }

    @Override
    protected void getData(final boolean isRefresh) {
        answerQuestionRepository.getDataByPosition(userRepository.getCurrentUser(),null,pageUtil.currentPage * pageUtil.pageSize,
                pageUtil.pageSize,
                new AbsQueryListener<List<Answer>>() {
                    @Override
                    public void onSuccess(List<Answer> questions) {
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


}
