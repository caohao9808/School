package org.fengye.school.model.sqlite;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

public class TodayWord extends LitePalSupport implements Serializable {

    /**
     * sid : 3461
     * tts : http://news.iciba.com/admin/tts/2019-07-22-day.mp3
     * content : If you really love someone, the whole life will not be enough. You need time to know, to forgive and to love. All this needs a very big mind.
     * note : 爱的次数不需多，只需真爱。真爱需要时间去经营，需要用心去了解，需要胸襟去包容。
     * love : 1741
     * translation : 小编的话：爱的次数不需多，只需真爱。真爱需要时间去经营，需要用心去了解，需要胸襟去包容。
     * picture : http://cdn.iciba.com/news/word/20190722.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20190722b.jpg
     * caption : 词霸每日一句
     * dateline : 2019-07-22
     * s_pv : 0
     * sp_pv : 0
     * tags : null
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2019-07-22.jpg
     */

    private String sid;
    private String tts;
    private String content;
    private String note;
    private String love;
    private String translation;
    private String picture;
    private String picture2;
    private String caption;
    private String dateline;

    private Date createDate;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
