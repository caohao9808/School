package org.fengye.school.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import org.fengye.school.model.bmob.User;

public class UserViewModel extends ViewModel {

    public ObservableField<User> user = new ObservableField<>();

}
