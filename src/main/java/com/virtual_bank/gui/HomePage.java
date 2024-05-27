package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.virtual_bank.core.*;
import com.virtual_bank.gui.common.Button;
import com.virtual_bank.gui.common.CuteLabel;
import com.virtual_bank.gui.common.CuteList;
import com.virtual_bank.gui.common.CuteScrollPane;

public class HomePage extends JPanel {
    private JPanel missionPanel;  // 任务面板
    private JPanel missionListPanel;  // 任务列表面板
    private JPanel targetPanel;  // 存款目标面板

    // 添加任务项到任务列表面板
    private void addItem(Mission mission, BaseFrame baseFrame) {
        JPanel itemPanel = new JPanel();
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String description = mission.description();
        Label label = new Label(description);
        Button button = new Button("Done");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XMLDBManager.eraseMission(mission.getMid());  // 删除任务
                String username = baseFrame.sessionManager.getUsername();
                User user = XMLDBManager.findUser(username);
                int money = user.getMoney() + mission.getReward();  // 更新用户余额
                user.setMoney(money);
                XMLDBManager.saveUser(user);  // 保存用户信息

                Transaction transaction = new Transaction(user.getUid(), mission.getContent(), mission.getReward());
                XMLDBManager.addTransaction(transaction);  // 添加交易记录

                baseFrame.refresh();  // 刷新页面
            }
        });
        itemPanel.add(label);  // 描述标签
        itemPanel.add(button);  // 完成按钮
        
        this.missionListPanel.add(itemPanel);
    }

    // 任务面板初始化
    private void initMissionPanel(BaseFrame baseFrame) {
        this.missionPanel = new JPanel();
        this.missionPanel.setLayout(new BorderLayout());
        this.missionPanel.setPreferredSize(new Dimension(450, 0));

        JPanel missionTitlePanel = new JPanel();
        missionTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        missionTitlePanel.add(new CuteLabel("<html><h3>Missions:</h3></html>"));
        this.missionPanel.add(missionTitlePanel, BorderLayout.NORTH);  // 添加标题

        JPanel missionContentPanel = new JPanel();
        missionContentPanel.setLayout(new BoxLayout(missionContentPanel, BoxLayout.Y_AXIS));

        List<Mission> missions = XMLDBManager.getMissionsList();  // 获取任务列表
        if (missions.size() == 0) {
            missionContentPanel.add(new CuteLabel("<html><h3>No missions available</h3></html>"));  // 无任务提示
        } else {
            for (Mission mission : missions) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                String description = mission.description();
                CuteLabel label = new CuteLabel(description);
                Button button = new Button("Done");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 任务完成事件
                        XMLDBManager.eraseMission(mission.getMid());  // 删除任务
                        String username = baseFrame.sessionManager.getUsername();
                        User user = XMLDBManager.findUser(username);
                        int money = user.getMoney() + mission.getReward();  // 更新用户金额
                        user.setMoney(money);
                        XMLDBManager.saveUser(user);  // 保存用户信息

                        Transaction transaction = new Transaction(user.getUid(), mission.getContent(), mission.getReward());
                        XMLDBManager.addTransaction(transaction);  // 添加交易记录

                        baseFrame.refresh();  // 刷新页面
                    }
                });
                itemPanel.add(label);
                itemPanel.add(button);
                missionContentPanel.add(itemPanel);
            }
        }

        CuteScrollPane missionScrollPane = new CuteScrollPane(missionContentPanel);  // 包装任务内容面板
        this.missionPanel.add(missionScrollPane, BorderLayout.CENTER);

        this.add(this.missionPanel, BorderLayout.EAST);  // 添加到主面板
    }

    // 目标面板初始化
    private void initTargetPanel(BaseFrame baseFrame) {
        this.targetPanel = new JPanel();
        this.targetPanel.setLayout(new BorderLayout());
        List<Integer> targets = XMLDBManager.getTargets();  // 获取目标列表
        String username = baseFrame.sessionManager.getUsername();
        int money = XMLDBManager.findUser(username).getMoney();  // 获取用户金额
        DefaultListModel<String> targetModel = new DefaultListModel<>();
        int nextTarget = -1;
        for (int i = 0; i < targets.size(); i++) {
            if (money >= targets.get(i)) {
                targetModel.addElement("" + targets.get(i) + "\t\t" + "√");  // 已完成的目标
            }
            else {
                targetModel.addElement("" + targets.get(i));  // 未完成的目标
                if (nextTarget == -1) {
                    nextTarget = targets.get(i);  // 下一个目标
                }
            }
        }
        if (nextTarget > 0) {
            this.targetPanel.add(new JLabel("<html><h3>Progress on your next goal: " + money + "/" + nextTarget + "</h3></html>"), BorderLayout.NORTH);  // 下一个目标的进度
        }
        CuteList<String> targeJList = new CuteList<>(targetModel);  // 目标列表
        targeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CuteScrollPane targetScrollPane = new CuteScrollPane(targeJList);
        this.targetPanel.add(targetScrollPane, BorderLayout.CENTER);
        this.add(this.targetPanel, BorderLayout.WEST);  // 添加目标面板到主面板
    }

    public HomePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setLayout(new BorderLayout());
            this.add(new JLabel("<html><h2>Hello, " + baseFrame.sessionManager.getUsername() + ".👋</h2></html>."), BorderLayout.NORTH);  // 欢迎消息
            this.initMissionPanel(baseFrame);  // 初始化任务面板
            this.initTargetPanel(baseFrame);  // 初始化目标面板
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER);  // 未登录提示
        }
    }
}
