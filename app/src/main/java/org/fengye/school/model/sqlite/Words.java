package org.fengye.school.model.sqlite;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Words extends LitePalSupport implements Serializable {

    private String planId;

    private String word;

    private String exp;

    // 1 已学习  -1 不认识
    @Column(defaultValue = "0")
    private Integer wordStatus;

    private Date studyTime;

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(Integer wordStatus) {
        this.wordStatus = wordStatus;
    }

    public void setStudyTime(Date studyTime) {
        this.studyTime = studyTime;
    }

    public Date getStudyTime() {
        return studyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Words words = (Words) o;
        return Objects.equals(planId, words.planId) &&
                Objects.equals(word, words.word) &&
                Objects.equals(exp, words.exp) &&
                Objects.equals(wordStatus, words.wordStatus) &&
                Objects.equals(studyTime, words.studyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, word, exp, wordStatus, studyTime);
    }
}

