package com.virtual_bank.core;

import java.util.UUID;

public class Mission {
    private String mid; // Task ID
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

    public Mission(String mid, String content, int reward) { // Constructor, passing in the task ID, content, and reward
        if ("#new".equals(mid)) { // If the task ID is "#new", a new unique task ID is generated
            this.setMid(UUID.randomUUID().toString());
        }
        else {
            this.setMid(mid); // Otherwise, the passed task ID is used
        }
        this.setContent(content); // Set content
        this.setReward(reward); // Set reward
    }

    public String description() { // Description method, which returns a task description string
        return this.content + " (reward:" + this.reward + ")";
    }
}
