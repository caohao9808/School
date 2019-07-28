package org.fengye.school.model.bmob;

import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject {

    private User user;

    private String title;

    private String content;

    private Integer numberOfAnswers;



    public Question() {
    }

    public Question(String objId) {
        setObjectId(objId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(Integer numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(user, question.user) &&
                Objects.equals(title, question.title) &&
                Objects.equals(content, question.content) &&
                Objects.equals(numberOfAnswers, question.numberOfAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, title, content, numberOfAnswers);
    }
}
