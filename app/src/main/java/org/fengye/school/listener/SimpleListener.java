package org.fengye.school.listener;

public interface SimpleListener {

    void onStart();

    void onError(String msg);

    void onSuccess();

    void onFinish();


}
