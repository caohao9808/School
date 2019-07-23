package org.fengye.school.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.listener.SimpleListener;
import org.fengye.school.model.bean.Question;
import org.fengye.school.repository.QuestionRepository;

import java.util.List;

public class CustomPageDataSource<M,R extends BaseRepository> extends PositionalDataSource<M> {

    private static final String TAG = "CustomPageDataSource";


    private R repository;

    public CustomPageDataSource(R repository) {
        this.repository = repository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<M> callback) {
//        List<M> mList = repository.getDataByPosition(0, params.pageSize);
//        callback.onResult(mList, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<M> callback) {
//        List<M> mList = repository.getDataByPosition(params.startPosition, params.loadSize);
//        callback.onResult(mList);
    }

}
