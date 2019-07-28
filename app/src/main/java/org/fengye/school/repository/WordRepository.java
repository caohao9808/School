package org.fengye.school.repository;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bmob.Thesaurus;
import org.fengye.school.model.sqlite.TodayWord;
import org.fengye.school.model.sqlite.LearnPlan;
import org.fengye.school.model.sqlite.Words;
import org.litepal.LitePal;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class WordRepository {


    public void getTodaySent(final QueryListener<TodayWord> listener) {

        TodayWord last = LitePal.findLast(TodayWord.class);
        if (last != null) {
            listener.onSuccess(last);
            Date createDate = last.getCreateDate();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            boolean before = createDate.before(calendar.getTime());
            if (before) {
                getSent(listener);
            }
        } else {
            getSent(listener);
        }

        OkGo.<TodayWord>get("http://open.iciba.com/dsapi/")
                .execute(new AbsCallback<TodayWord>() {

                    @Override
                    public void onSuccess(Response<TodayWord> response) {
                        if (response.body() != null) {
                            listener.onSuccess(response.body());
                        }
                    }

                    @Override
                    public TodayWord convertResponse(okhttp3.Response response) throws Throwable {

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            TodayWord todayWord = gson.fromJson(response.body().string(), TodayWord.class);
                            todayWord.setCreateDate(new Date());
                            todayWord.save();
                            return todayWord;
                        }
                        return null;
                    }
                });


    }

    private void getSent(final QueryListener<TodayWord> listener) {


        OkGo.<TodayWord>get("http://open.iciba.com/dsapi/")
                .execute(new AbsCallback<TodayWord>() {
                    @Override
                    public void onSuccess(Response<TodayWord> response) {
                        if (response.body() != null) {
                            listener.onSuccess(response.body());
                        }
                    }

                    @Override
                    public TodayWord convertResponse(okhttp3.Response response) throws Throwable {

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            TodayWord todayWord = gson.fromJson(response.body().string(), TodayWord.class);
                            todayWord.setCreateDate(new Date());
                            todayWord.save();
                            return todayWord;
                        }
                        return null;
                    }
                });


    }

    public Words getWord(LearnPlan learnPlan) {
        if (learnPlan == null) {
            return null;
        }

        int count = LitePal.where("planId = ? and wordStatus < ?", learnPlan.getPlanId(), "1").count(Words.class);
        Random random = new Random();
        int i = random.nextInt(count);


        List<Words> words = LitePal.where("planId = ? and wordStatus < ?", learnPlan.getPlanId(), "1").offset(i).limit(1).find(Words.class);

        return words != null && words.size() > 0 ? words.get(0) : null;
    }

    public LearnPlan getLearnPlan() {

        List<LearnPlan> learnPlans = LitePal.where("currentPlan = ?", "1").find(LearnPlan.class);

        return learnPlans != null && learnPlans.size() > 0 ? learnPlans.get(0) : null;
    }

    public int getAllAlreadyLearn(LearnPlan learnPlan) {
        int count = LitePal.where("planId = ? and wordStatus = ?", learnPlan.getPlanId(), "1").count(Words.class);

        return count;
    }

    public int getTodayWords(LearnPlan learnPlan) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int count = LitePal.where("planId = ? and studyTime > ?", learnPlan.getPlanId(), "" + calendar.getTimeInMillis()).count(Words.class);
        return count;
    }

    public int getTodayAlreadyLearn(LearnPlan learnPlan) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int count = LitePal.where("planId = ? and wordStatus = ? and  studyTime > ?", learnPlan.getPlanId(), "1", "" + calendar.getTimeInMillis()).count(Words.class);
        return count;
    }

    public int getNotKnowWords(LearnPlan learnPlan) {
        int count = LitePal.where("planId = ? and wordStatus = ? ", learnPlan.getPlanId(), "-1").count(Words.class);
        return count;
    }

    public void getThesaurus(final QueryListener<List<Thesaurus>> listener) {
        listener.onStart();
        BmobQuery<Thesaurus> query = new BmobQuery<>();
        query.order("priority")
                .findObjects(new FindListener<Thesaurus>() {
                    @Override
                    public void done(List<Thesaurus> list, BmobException e) {

                        if (e == null && list != null && list.size() > 0) {
                            listener.onSuccess(list);
                        } else {
                            listener.onError("无数据");
                        }
                        listener.onFinish();
                    }
                });
    }

}
