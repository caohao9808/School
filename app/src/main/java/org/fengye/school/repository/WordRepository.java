package org.fengye.school.repository;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.bean.Question;
import org.fengye.school.model.bean.TodayWord;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WordRepository {


    public void getTodayWord(final QueryListener<TodayWord> listener) {

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
                getWord(listener);
            }
        } else {
            getWord(listener);
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

    private void getWord(final QueryListener<TodayWord> listener) {


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

}
