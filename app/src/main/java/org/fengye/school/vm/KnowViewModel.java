package org.fengye.school.vm;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bean.TodayWord;
import org.fengye.school.repository.WordRepository;

public class KnowViewModel extends ViewModel {

    private WordRepository repository;
    private boolean showEnglish = true;
    public ObservableField<String> wordShow = new ObservableField<>("");


    public KnowViewModel() {
        repository = new WordRepository();
    }

    private MutableLiveData<TodayWord> todayWord = new MutableLiveData<>();

    public MutableLiveData<TodayWord> getTodayWord() {
        return todayWord;
    }

    public void refreshWord() {

        repository.getTodayWord(new AbsQueryListener<TodayWord>() {
            @Override
            public void onSuccess(TodayWord todayWord) {
                super.onSuccess(todayWord);
                getTodayWord().setValue(todayWord);
                changeEnglish();
            }
        });
    }

    public View.OnClickListener changeShowEnglish=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEnglish = !showEnglish;
            changeEnglish();
        }
    };
    private void changeEnglish() {
        if (showEnglish) {
            wordShow.set(todayWord.getValue().getContent());
        } else {
            wordShow.set(todayWord.getValue().getNote());

        }
    }
}
