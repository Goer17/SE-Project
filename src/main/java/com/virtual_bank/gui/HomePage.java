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
//import com.virtual_bank.gui.common.CutePanel;
import com.virtual_bank.gui.common.CuteScrollPane;

public class HomePage extends JPanel {
    private JPanel missionPanel;
    private JPanel missionListPanel;
    private JPanel targetPanel;

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
                XMLDBManager.eraseMission(mission.getMid());
                String username = baseFrame.sessionManager.getUsername();
                User user = XMLDBManager.findUser(username);
                int money = user.getMoney() + mission.getReward();
                user.setMoney(money);
                XMLDBManager.saveUser(user);

                Transaction transaction = new Transaction(user.getUid(), mission.getContent(), mission.getReward());
                XMLDBManager.addTransaction(transaction);

                baseFrame.refresh();
            }
        });
        itemPanel.add(label);
        itemPanel.add(button);
        
        this.missionListPanel.add(itemPanel);
    }

    private void initMissionPanel(BaseFrame baseFrame) {
        this.missionPanel = new JPanel();
        this.missionPanel.setLayout(new BorderLayout());
        this.missionPanel.setPreferredSize(new Dimension(450, 0));

        JPanel missionTitlePanel = new JPanel();
        missionTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        missionTitlePanel.add(new CuteLabel("<html><h3>Missions:</h3></html>"));
        this.missionPanel.add(missionTitlePanel, BorderLayout.NORTH);

        JPanel missionContentPanel = new JPanel();
        missionContentPanel.setLayout(new BoxLayout(missionContentPanel, BoxLayout.Y_AXIS));

        List<Mission> missions = XMLDBManager.getMissionsList();
        if (missions.size() == 0) {
            missionContentPanel.add(new CuteLabel("<html><h3>No missions available</h3></html>"));
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
                        // Your action listener code here
                        XMLDBManager.eraseMission(mission.getMid());
                        String username = baseFrame.sessionManager.getUsername();
                        User user = XMLDBManager.findUser(username);
                        int money = user.getMoney() + mission.getReward();
                        user.setMoney(money);
                        XMLDBManager.saveUser(user);

                        Transaction transaction = new Transaction(user.getUid(), mission.getContent(), mission.getReward());
                        XMLDBManager.addTransaction(transaction);

                        baseFrame.refresh();
                    }
                });
                itemPanel.add(label);
                itemPanel.add(button);
                missionContentPanel.add(itemPanel);
            }
        }

        CuteScrollPane missionScrollPane = new CuteScrollPane(missionContentPanel);
        this.missionPanel.add(missionScrollPane, BorderLayout.CENTER);

        this.add(this.missionPanel, BorderLayout.EAST);
    }


    private void initTargetPanel(BaseFrame baseFrame) {
        this.targetPanel = new JPanel();
        this.targetPanel.setLayout(new BorderLayout());
        List<Integer> targets = XMLDBManager.getTargets();
        String username = baseFrame.sessionManager.getUsername();
        int money = XMLDBManager.findUser(username).getMoney();
        DefaultListModel<String> targetModel = new DefaultListModel<>();
        int nextTarget = -1;
        for (int i = 0; i < targets.size(); i++) {
            if (money >= targets.get(i)) {
                targetModel.addElement("" + targets.get(i) + "\t\t" + "√");
            }
            else {
                targetModel.addElement("" + targets.get(i));
                if (nextTarget == -1) {
                    nextTarget = targets.get(i);
                }
            }
        }
        if (nextTarget > 0) {
            this.targetPanel.add(new JLabel("<html><h3>Progress on your next goal: " + money + "/" + nextTarget + "</h3></html>"), BorderLayout.NORTH);
        }
        CuteList<String> targeJList = new CuteList<>(targetModel);
        targeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CuteScrollPane targetScrollPane = new CuteScrollPane(targeJList);
        this.targetPanel.add(targetScrollPane, BorderLayout.CENTER);
        this.add(this.targetPanel, BorderLayout.WEST);
    }

    public HomePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setLayout(new BorderLayout());
            this.add(new JLabel("<html><h2>Hello, " + baseFrame.sessionManager.getUsername() + ".👋</h2></html>."), BorderLayout.NORTH);
            this.initMissionPanel(baseFrame);
            this.initTargetPanel(baseFrame);
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER);
        }
    }
}