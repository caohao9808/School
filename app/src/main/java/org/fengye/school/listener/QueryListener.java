package org.fengye.school.listener;

public interface QueryListener<T> {


    void onStart();

    void onFinish();

    void onSuccess(T t);

    void onError(String msg);

}
