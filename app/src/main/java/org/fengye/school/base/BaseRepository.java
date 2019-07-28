package org.fengye.school.base;

import org.fengye.school.listener.QueryListener;

import java.util.List;

public abstract class BaseRepository<T> {

    public abstract List<T> getDataByPosition(int position, int size);


    public abstract List<T> getDataByPosition(int position, int size, int type);


}
