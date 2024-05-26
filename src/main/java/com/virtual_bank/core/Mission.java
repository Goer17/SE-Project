package com.virtual_bank.core;

import java.util.UUID;

public class Mission {
    private String mid; // 任务ID
    private String content; // 内容
    private int reward; // 奖励

    public String getMid() { // 获取任务ID
        return mid;
    }

    public void setMid(String mid) { // 设置任务ID
        this.mid = mid;
    }

    public String getContent() { // 获取内容
        return content;
    }

    public void setContent(String content) { // 设置内容
        this.content = content;
    }

    public int getReward() { // 获取奖励
        return reward;
    }

    public void setReward(int reward) { // 设置奖励
        this.reward = reward;
    }

    public Mission(String mid, String content, int reward) { // 构造函数，传入任务ID、内容和奖励
        if ("#new".equals(mid)) { // 如果任务ID为"#new"，则生成新的唯一任务ID
            this.setMid(UUID.randomUUID().toString());
        }
        else {
            this.setMid(mid); // 否则使用传入的任务ID
        }
        this.setContent(content); // 设置内容
        this.setReward(reward); // 设置奖励
    }

    public String description() { // 描述方法，返回任务描述字符串
        return this.content + " (reward:" + this.reward + ")";
    }
}
