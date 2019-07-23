package org.fengye.school.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bean.Answer;
import org.fengye.school.repository.AnswerQuestionRepository;
import org.fengye.school.util.PageUtil;

import java.util.List;

public abstract class BasePageViewModel<M> extends ViewModel {


    protected PageUtil pageUtil = new PageUtil();

    private MutableLiveData<List<M>> newLiveData = new MutableLiveData<>();

    private MutableLiveData<List<M>> moreLiveData = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    protected boolean hasMoreData = true;

    public MutableLiveData<List<M>> getNewLiveData() {
        return newLiveData;
    }

    public MutableLiveData<List<M>> getMoreLiveData() {
        return moreLiveData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }


    public void refresh() {
        pageUtil.refresh();

        getData(true);

    }

    public void loadMore() {
        pageUtil.addPage();

        getData(false);


    }

    protected abstract void getData(boolean isRefresh) ;


}
