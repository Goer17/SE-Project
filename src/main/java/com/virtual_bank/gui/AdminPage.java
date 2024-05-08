package com.virtual_bank.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.virtual_bank.core.*;;

public class AdminPage extends JPanel {
    private JPanel rankPanel;
    private JPanel missionPanel;

    private void initRankPanel() {
        this.rankPanel = new JPanel();
        this.rankPanel.setLayout(new BorderLayout());
        this.rankPanel.add(new JLabel("<html><h3>Rank</h3></html>"), BorderLayout.NORTH);
        
        List<User> users = XMLDBManager.getAllUsers(true);
        DefaultListModel<String> rankModel = new DefaultListModel<>();
        for (int i = 0; i < users.size(); i++) {
            rankModel.addElement("No." + i + " " + users.get(i).getName() + " -- " + users.get(i).getMoney());
        }
        JList<String> rankJList = new JList<>(rankModel);
        rankJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(rankJList);
        this.rankPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(this.rankPanel, BorderLayout.WEST);
    }

    private void initMissionPanel(BaseFrame baseFrame) {
        this.missionPanel = new JPanel();
        this.missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
        this.missionPanel.add(new JLabel("<html><h3>Missions</h3></html>"));

        List<Mission> missions = XMLDBManager.getMissionsList();
        DefaultListModel<String> missionModel = new DefaultListModel<>();
        for (Mission mission : missions) {
            missionModel.addElement(mission.description());
        }
        JList<String> missionJList = new JList<>(missionModel);
        missionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(missionJList);
        this.missionPanel.add(scrollPane);
        
        JPanel addMissionBox = new JPanel();
        addMissionBox.setLayout(new BoxLayout(addMissionBox, BoxLayout.Y_AXIS));

        addMissionBox.add(new JLabel("<html><h4>Add a new mission:</h4></html>"));
        addMissionBox.add(new JLabel("Mission content:"));
        JTextField missionContent = new JTextField(20);
        Dimension maxDimension = new Dimension(Integer.MAX_VALUE, missionContent.getPreferredSize().height);
        missionContent.setMaximumSize(maxDimension);
        addMissionBox.add(missionContent);

        addMissionBox.add(new JLabel("Mission reward:"));
        JTextField missionReward = new JTextField(10);
        missionReward.setMaximumSize(maxDimension);
        addMissionBox.add(missionReward);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = missionContent.getText().strip();
                int reward = Integer.parseInt(missionReward.getText().strip());
                if (content.length() > 0 && reward > 0) {
                    Mission newMission = new Mission("#new", content, reward);
                    XMLDBManager.addMission(newMission);
                    baseFrame.refresh();
                }
            }
        });
        addMissionBox.add(addButton);
        this.missionPanel.add(addMissionBox);

        this.add(this.missionPanel, BorderLayout.EAST);
    }

    public AdminPage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            if ("admin".equals(baseFrame.sessionManager.getUsername())) {
                this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                this.setLayout(new BorderLayout());
                this.initRankPanel();
                this.initMissionPanel(baseFrame);
            }
            else {
                this.add(new JLabel("<html><h2>You have no access to this page.</h2></html>"));
            }
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"));
        }
    }    
}
