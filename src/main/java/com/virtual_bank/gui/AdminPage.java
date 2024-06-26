package com.virtual_bank.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.virtual_bank.core.*;
import com.virtual_bank.gui.common.*;


public class AdminPage extends JPanel {
    private JPanel rankPanel; 
    private JPanel missionPanel;


    private void initRankPanel() {
        this.rankPanel = new JPanel();
        this.rankPanel.setLayout(new BorderLayout());
        this.rankPanel.add(new CuteLabel("<html><h3>Rank</h3></html>"), BorderLayout.NORTH);
        
        // Ranklist
        List<User> users = XMLDBManager.getAllUsers(true);
        DefaultListModel<String> rankModel = new DefaultListModel<>();
        for (int i = 0; i < users.size(); i++) {
            rankModel.addElement("No." + i + " " + users.get(i).getName() + " -- " + users.get(i).getMoney());
        }
        CuteList<String> rankJList = new CuteList<>(rankModel);
        rankJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CuteScrollPane scrollPane = new CuteScrollPane(rankJList);
        this.rankPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(this.rankPanel, BorderLayout.WEST);
    }

    // mission panel
    private void initMissionPanel(BaseFrame baseFrame) {
        this.missionPanel = new JPanel();
        this.missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
        this.missionPanel.add(new CuteLabel("<html><h3>Missions</h3></html>"));
        this.missionPanel.setPreferredSize(new Dimension(300, 0));

        // get all mission
        List<Mission> missions = XMLDBManager.getMissionsList();
        DefaultListModel<String> missionModel = new DefaultListModel<>();
        for (Mission mission : missions) {
            missionModel.addElement(mission.description());
        }
        CuteList<String> missionJList = new CuteList<>(missionModel);
        missionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CuteScrollPane scrollPane = new CuteScrollPane(missionJList);
        this.missionPanel.add(scrollPane);

        // add new mission
        JPanel addMissionBox = new JPanel();
        addMissionBox.setLayout(new BoxLayout(addMissionBox, BoxLayout.Y_AXIS));

        addMissionBox.add(new CuteLabel("<html><h4>Add a new mission:</h4></html>"));
        addMissionBox.add(new CuteLabel("Mission content:"));
        CuteTextField missionContent = new CuteTextField(20);
        Dimension maxDimension = new Dimension(Integer.MAX_VALUE, missionContent.getPreferredSize().height);
        missionContent.setMaximumSize(maxDimension);
        addMissionBox.add(missionContent);

        addMissionBox.add(new CuteLabel("Mission reward:"));
        CuteTextField missionReward = new CuteTextField(10);
        missionReward.setMaximumSize(maxDimension);
        addMissionBox.add(missionReward);

        // add task event
        Button addButton = new Button("Add");
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
            } else {
                this.add(new CuteLabel("<html><h2>You have no access to this page.</h2></html>")); // Non-administrators cannot access it
            }
        } else {
            this.add(new CuteLabel("<html><h2>Please login first :)</h2></html>")); // Message for users not logged in
        }
    }    
}
