package org.fengye.school.model.bmob;

import java.io.Serializable;
import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class Answer extends BmobObject implements Serializable {


    private User user;

    private Question question;

    private String content;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(user, answer.user) &&
                Objects.equals(question, answer.question) &&
                Objects.equals(content, answer.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, question, content);
    }
}
