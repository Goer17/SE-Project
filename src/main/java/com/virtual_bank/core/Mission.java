package com.virtual_bank.core;

import java.util.UUID;

public class Mission {
    private String mid;
    private String content;
    private int reward;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public Mission(String mid, String content, int reward) {
        if ("#new".equals(mid)) {
            this.setMid(UUID.randomUUID().toString());
        }
        else {
            this.setMid(mid);
        }
        this.setContent(content);
        this.setReward(reward);
    }

    public String description() {
        return this.content + " (reward:" + this.reward + ")";
    }
}
