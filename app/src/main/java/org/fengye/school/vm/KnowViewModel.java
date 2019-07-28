package org.fengye.school.vm;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.fengye.school.listener.AbsQueryListener;
import org.fengye.school.model.bmob.Thesaurus;
import org.fengye.school.model.sqlite.TodayWord;
import org.fengye.school.model.sqlite.LearnPlan;
import org.fengye.school.model.sqlite.Words;
import org.fengye.school.repository.WordRepository;
import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class KnowViewModel extends ViewModel {

    private MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private WordRepository repository;
    private boolean showEnglish = true;
    public ObservableField<String> wordShow = new ObservableField<>("");

    private MutableLiveData<List<Thesaurus>> thesaurus = new MutableLiveData<>();

    public ObservableField<LearnPlan> studyPlanField = new ObservableField<>();


    public ObservableField<Integer> allAlreadyLearn = new ObservableField<>(0);

    public ObservableField<Integer> todayWords = new ObservableField<>(0);

    public ObservableField<Integer> todayAlreadyLearn = new ObservableField<>(0);

    public ObservableField<Integer> notKnowWords = new ObservableField<>(0);


    public ObservableField<Words> wordsField = new ObservableField<>();


    public KnowViewModel() {
        repository = new WordRepository();
    }

    private MutableLiveData<TodayWord> todaySent = new MutableLiveData<>();

    public MutableLiveData<TodayWord> getTodaySent() {
        return todaySent;
    }

    public void refreshTodaySent() {

        repository.getTodaySent(new AbsQueryListener<TodayWord>() {
            @Override
            public void onSuccess(TodayWord todayWord) {
                super.onSuccess(todayWord);
                getTodaySent().setValue(todayWord);
                changeEnglish();
            }
        });
    }

    public View.OnClickListener changeShowEnglish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEnglish = !showEnglish;
            changeEnglish();
        }
    };

    private void changeEnglish() {
        if (showEnglish) {
            wordShow.set(todaySent.getValue().getContent());
        } else {
            wordShow.set(todaySent.getValue().getNote());

        }
    }

    public void refreshWordField() {
        Words word = repository.getWord(studyPlanField.get());
        if (word != null) {
            wordsField.set(word);
        } else {

        }
    }

    public void notKnowWord() {

        Words words = wordsField.get();
        words.setWordStatus(-1);
        words.setStudyTime(new Date());
        words.save();

        todayWords.set((todayWords.get()) + 1);

        notKnowWords.set((notKnowWords.get()) + 1);

        refreshWordField();
    }

    public void knowWord() {

        Words words = wordsField.get();
        words.setWordStatus(1);
        words.setStudyTime(new Date());
        words.save();

        todayWords.set((todayWords.get()) + 1);
        allAlreadyLearn.set((allAlreadyLearn.get()) + 1);
        todayAlreadyLearn.set((todayAlreadyLearn.get()) + 1);


        refreshWordField();
    }

    public MutableLiveData<List<Thesaurus>> getThesaurus() {
        return thesaurus;
    }

    public void showThesaurus() {

        repository.getThesaurus(new AbsQueryListener<List<Thesaurus>>() {
            @Override
            public void onStart() {
                super.onStart();
                viewStatus.setValue(ViewStatus.LOADING);
            }

            @Override
            public void onSuccess(List<Thesaurus> thesa) {
                super.onSuccess(thesa);
                thesaurus.postValue(thesa);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                error.setValue(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                viewStatus.setValue(ViewStatus.IDLE);
            }
        });

    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public void downThesaurus(final Thesaurus thesaurus) {

        List<LearnPlan> learnPlans = LitePal.where("planId = ?", thesaurus.getObjectId()).find(LearnPlan.class);
        if (learnPlans != null && learnPlans.size() > 0) {
            stopAllPlan();
            LearnPlan learnPlan = learnPlans.get(0);
            learnPlan.setCurrentPlan(true);
            learnPlan.setPlanName(thesaurus.getName());
            learnPlan.setPlanTotal(thesaurus.getTotal());
            learnPlan.save();
//            studyPlanField.set(learnPlan);
            definePlan(learnPlan);
            return;
        }

        String fileUrl = thesaurus.getWordsFile().getFileUrl();
        String replaceFirst = fileUrl.replaceFirst("bmob-cdn-16813.b0.upaiyun.com", "bm.fylala.com");

        OkGo.<List<Words>>get(replaceFirst)
                .execute(new AbsCallback<List<Words>>() {
                    @Override
                    public void onStart(Request<List<Words>, ? extends Request> request) {
                        super.onStart(request);
                        viewStatus.setValue(ViewStatus.LOADING);
                    }

                    @Override
                    public void onSuccess(Response<List<Words>> response) {
                        if (response.body() != null && response.body().size() > 0) {
                            LearnPlan learnPlan = new LearnPlan(thesaurus);
                            stopAllPlan();
                            learnPlan.setCurrentPlan(true);
                            learnPlan.save();
//                            studyPlanField.set(learnPlan);
                            definePlan(learnPlan);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        viewStatus.setValue(ViewStatus.IDLE);
                    }

                    @Override
                    public List<Words> convertResponse(okhttp3.Response response) throws Throwable {

                        String body = response.body().string();
                        Gson gson = new Gson();
                        List<Words> words = gson.fromJson(body, new TypeToken<List<Words>>() {
                        }.getType());
                        for (Words word : words) {
                            word.setPlanId(thesaurus.getObjectId());
                        }
                        LitePal.saveAll(words);
                        return words;
                    }
                });

    }

    public void definePlan() {
        LearnPlan learnPlan = repository.getLearnPlan();
        definePlan(learnPlan);
    }

    public void definePlan(LearnPlan learnPlan ) {
        studyPlanField.set(learnPlan);
        if (learnPlan != null) {

            allAlreadyLearn.set(repository.getAllAlreadyLearn(learnPlan));
            todayAlreadyLearn.set(repository.getTodayAlreadyLearn(learnPlan));
            notKnowWords.set(repository.getNotKnowWords(learnPlan));
            todayWords.set(repository.getTodayWords(learnPlan));
        }
    }

    private void stopAllPlan() {
        List<LearnPlan> learnPlans = LitePal.where("currentPlan = ?", "1").find(LearnPlan.class);
        if (learnPlans != null) {
            for (LearnPlan plan : learnPlans) {
                plan.setCurrentPlan(false);
            }
        }
        LitePal.saveAll(learnPlans);
    }
}
