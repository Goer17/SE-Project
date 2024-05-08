package test;

import com.virtual_bank.core.*;

public class Test {
    public static void main(String[] args) {
        Mission mission = new Mission("#new", "Washing clothes", 1000);
        XMLDBManager.addMission(mission);
        mission = new Mission("#new", "Doing homework", 200);
        XMLDBManager.addMission(mission);
    }
}