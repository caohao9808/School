package org.fengye.school.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import org.fengye.school.base.BasePageViewModel;
import org.fengye.school.data.CustomPageDataSourceFactory;
import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bean.Answer;
import org.fengye.school.model.bean.Question;
import org.fengye.school.repository.AnswerQuestionRepository;
import org.fengye.school.repository.QuestionRepository;
import org.fengye.school.util.PageUtil;

import java.util.List;

public class QuestionViewModel  extends BasePageViewModel<Answer> {


    private AnswerQuestionRepository answerQuestionRepository;


    public QuestionViewModel() {
        answerQuestionRepository = new AnswerQuestionRepository();
    }

    @Override
    protected void getData(final boolean isRefresh) {
        answerQuestionRepository.getDataByPosition(pageUtil.currentPage * pageUtil.pageSize,
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
