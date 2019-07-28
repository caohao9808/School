package org.fengye.school.model.bmob;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String nickname;

    private String avatar;

    private Integer numberOfAnswers;

    private Integer numberOfQuestion;

    public User() {
    }

    public User(String objId) {
        setObjectId(objId);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public Integer getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(Integer numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public Integer getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(Integer numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }
}
