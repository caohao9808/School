package org.fengye.school.model.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import java.io.Serializable;

public class Thesaurus extends BmobObject implements Serializable {

    private Integer priority;

    private String name;

    private BmobFile wordsFile;

    private Integer total;

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getWordsFile() {
        return wordsFile;
    }

    public void setWordsFile(BmobFile wordsFile) {
        this.wordsFile = wordsFile;
    }
}
