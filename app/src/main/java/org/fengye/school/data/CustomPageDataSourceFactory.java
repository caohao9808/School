package org.fengye.school.data;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.repository.QuestionRepository;

public class CustomPageDataSourceFactory<M, R extends BaseRepository> extends DataSource.Factory<Integer, M> {


    private R repository;


    public CustomPageDataSourceFactory(R repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public DataSource<Integer, M> create() {
        return new CustomPageDataSource<M, R>(repository);
    }

}
