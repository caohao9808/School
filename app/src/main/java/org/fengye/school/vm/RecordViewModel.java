package org.fengye.school.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import org.fengye.school.data.CustomPageDataSourceFactory;
import org.fengye.school.model.sqlite.Words;
import org.fengye.school.repository.RecordRepository;

public class RecordViewModel extends ViewModel {


    PagedList.Config config = new PagedList.Config.Builder().setPageSize(10)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .build();

    private LiveData<PagedList<Words>> allAlreadyLearn =
            new LivePagedListBuilder<Integer, Words>(
                    new CustomPageDataSourceFactory<Words, RecordRepository>(new RecordRepository(0))
                    , config).build();



    private LiveData<PagedList<Words>> todayWords =
            new LivePagedListBuilder<Integer, Words>(
                    new CustomPageDataSourceFactory<Words, RecordRepository>(new RecordRepository(1))
                    , config).build();



    private LiveData<PagedList<Words>> todayAlreadyLearn =
            new LivePagedListBuilder<Integer, Words>(
                    new CustomPageDataSourceFactory<Words, RecordRepository>(new RecordRepository(2))
                    , config).build();



    private LiveData<PagedList<Words>> notKnowWords =
            new LivePagedListBuilder<Integer, Words>(
                    new CustomPageDataSourceFactory<Words, RecordRepository>(new RecordRepository(3))
                    , config).build();





    public RecordViewModel() {

    }

    public LiveData<PagedList<Words>> getAllAlreadyLearn() {
        return allAlreadyLearn;
    }

    public LiveData<PagedList<Words>> getNotKnowWords() {
        return notKnowWords;
    }

    public LiveData<PagedList<Words>> getTodayAlreadyLearn() {
        return todayAlreadyLearn;
    }

    public LiveData<PagedList<Words>> getTodayWords() {
        return todayWords;
    }
}
