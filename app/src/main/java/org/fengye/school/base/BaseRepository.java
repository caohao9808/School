package org.fengye.school.base;

import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bean.Question;

import java.util.List;

public abstract class BaseRepository<T> {

    public abstract T getDataByPosition(int position, int size);

    public abstract void getDataByPosition(int position, int size, QueryListener<T> listener);

}
