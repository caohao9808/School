package org.fengye.school.listener;

public abstract class AbsQueryListener<T> implements QueryListener<T> {

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onError(String msg) {

    }
}
