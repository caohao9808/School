package org.fengye.school.repository;

import org.fengye.school.base.BaseRepository;
import org.fengye.school.listener.QueryListener;
import org.fengye.school.model.sqlite.LearnPlan;
import org.fengye.school.model.sqlite.Words;
import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;

public class RecordRepository extends BaseRepository<Words> {

    private int type;
    private WordRepository wordRepository;
    private long time;
    private LearnPlan learnPlan;

    public RecordRepository(int type) {
        this.type = type;
        wordRepository = new WordRepository();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        time = calendar.getTimeInMillis();
        learnPlan = wordRepository.getLearnPlan();
    }

    @Override
    public List<Words> getDataByPosition(int position, int size) {

        List<Words> words = null;


        if (type == 0) {

            words = LitePal
                    .where("planId = ? and wordStatus = ?", learnPlan.getPlanId(), "1")
                    .order("-studyTime").offset(position)
                    .limit(size).find(Words.class);

        } else if (type == 1) {

            words = LitePal
                    .where("planId = ? and studyTime > ?", learnPlan.getPlanId(), "" + time)
                    .order("-studyTime").offset(position)
                    .limit(size).find(Words.class);

        } else if (type == 2) {

            words = LitePal
                    .where("planId = ? and wordStatus = ? and  studyTime > ?", learnPlan.getPlanId(), "1", "" + time)
                    .order("-studyTime").offset(position)
                    .limit(size).find(Words.class);

        } else if (type == 3) {

            words = LitePal
                    .where("planId = ? and wordStatus = ?", learnPlan.getPlanId(), "-1")
                    .order("-studyTime").offset(position)
                    .limit(size).find(Words.class);

        }


        return words;
    }

    @Override
    public List<Words> getDataByPosition(int position, int size, int type) {
        return null;
    }


}
