package org.fengye.school.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {

    public MutableLiveData<Boolean> refreshQuestion = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshAnswer = new MutableLiveData<>();

}
