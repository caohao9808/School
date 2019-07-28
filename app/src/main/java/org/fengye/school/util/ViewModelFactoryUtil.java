package org.fengye.school.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.fengye.school.ui.fragment.AllQuestionFragment;
import org.fengye.school.vm.AllQuestionViewModel;
import org.fengye.school.vm.AnswerQuestionViewModel;
import org.fengye.school.vm.KnowViewModel;
import org.fengye.school.vm.MyAnswerQuestionViewModel;
import org.fengye.school.vm.MyQuestionViewModel;
import org.fengye.school.vm.QuestionViewModel;
import org.fengye.school.vm.RecordViewModel;
import org.fengye.school.vm.UserViewModel;

public class ViewModelFactoryUtil extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactoryUtil INSTANCE;

    private ViewModelFactoryUtil() {
    }

    public static ViewModelFactoryUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactoryUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactoryUtil();
                }
            }
        }

        return INSTANCE;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(QuestionViewModel.class)) {

            return (T) new QuestionViewModel();

        } else if (modelClass.isAssignableFrom(AnswerQuestionViewModel.class)) {

            return (T) new AnswerQuestionViewModel();

        }else if (modelClass.isAssignableFrom(KnowViewModel.class)) {

            return (T) new KnowViewModel();

        }else if (modelClass.isAssignableFrom(RecordViewModel.class)) {

            return (T) new RecordViewModel();

        }else if (modelClass.isAssignableFrom(AllQuestionViewModel.class)) {

            return (T) new AllQuestionViewModel();

        }else if (modelClass.isAssignableFrom(UserViewModel.class)) {

            return (T) new UserViewModel();

        }else if (modelClass.isAssignableFrom(MyQuestionViewModel.class)) {

            return (T) new MyQuestionViewModel();

        }else if (modelClass.isAssignableFrom(MyAnswerQuestionViewModel.class)) {

            return (T) new MyAnswerQuestionViewModel();

        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }


}
