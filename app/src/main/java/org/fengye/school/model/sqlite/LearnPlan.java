package org.fengye.school.model.sqlite;

import org.fengye.school.model.bmob.Thesaurus;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class LearnPlan extends LitePalSupport implements Serializable {


    private String planId;

    private String planName;

    private Integer planTotal;

    @Column(defaultValue = "0")
    private boolean currentPlan;

    public LearnPlan() {

    }

    public LearnPlan(Thesaurus thesaurus) {
        this.planId = thesaurus.getObjectId();
        this.planName = thesaurus.getName();
        this.planTotal = thesaurus.getTotal();
    }

    public String getPlanId() {
        return planId;
    }

    public LearnPlan setPlanId(String planId) {
        this.planId = planId;
        return this;
    }

    public String getPlanName() {
        return planName;
    }

    public LearnPlan setPlanName(String planName) {
        this.planName = planName;
        return this;
    }

    public Integer getPlanTotal() {
        return planTotal;
    }

    public LearnPlan setPlanTotal(Integer planTotal) {
        this.planTotal = planTotal;
        return this;
    }

    public boolean isCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(boolean currentPlan) {
        this.currentPlan = currentPlan;
    }
}
