package test;

import java.time.LocalDate;
import com.virtual_bank.core.*;


public class Test {
    public static void main(String[] args) {

        testFixedDeposit();
        testMission();
        testSessionManager();
        testTransaction();
        testUser();

        testUserTransactionIntegration();
        testFixedDepositIntegration();
        testXMLDBManagerIntegration();

        simulateUserLifecycle();

    }

    public static void testFixedDeposit() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);
        FixedDeposit fd = new FixedDeposit("001", 10000, 0.05, start, end);
        System.out.println("FixedDeposit Interest (should be around 500): " + fd.calculateInterest());
        System.out.println("FixedDeposit Maturity Amount (should be around 10500): " + fd.calculateMaturityAmount());
    }

    public static void testMission() {
        Mission newMission = new Mission("#new", "Complete this important task", 50);
        System.out.println("Mission ID (should not be #new): " + newMission.getMid());
        System.out.println("Mission Description (should be 'Complete this important task (reward:50)'): " + newMission.description());
    }

    public static void testSessionManager() {
        SessionManager sm = SessionManager.getInstance();
        sm.login("002", "userTest");
        System.out.println("SessionManager is logged in (should be true): " + sm.isLoggedIn());
        sm.logout();
        System.out.println("SessionManager is logged in (should be false): " + sm.isLoggedIn());
    }

    public static void testTransaction() {
        Transaction transaction = new Transaction("003", "deposit", 300);
        System.out.println("Transaction Type (should be 'deposit'): " + transaction.getType());
        System.out.println("Transaction Amount (should be 300): " + transaction.getAmount());
    }

    public static void testUser() {
        User user = new User("#new", "Alice", "password123", 1000);
        System.out.println("User Name (should be 'Alice'): " + user.getName());
        user.setMoney(1100);
        System.out.println("User Money after update (should be 1100): " + user.getMoney());
    }

    public static void testUserTransactionIntegration() {
        // Assume an existing user
        User user = new User("#new", "TestUser", "password", 1000);
        XMLDBManager.addUser(user);

        // Perform a deposit operation
        Transaction deposit = new Transaction(user.getUid(), "deposit", 500);
        XMLDBManager.addTransaction(deposit);

        // Check balance update
        user.setMoney(user.getMoney() + deposit.getAmount());
        System.out.println("Expected balance (should be 1500): " + user.getMoney());

        // Perform a withdrawal operation
        Transaction withdrawal = new Transaction(user.getUid(), "withdrawal", 300);
        XMLDBManager.addTransaction(withdrawal);

        // Check balance update after withdrawal
        user.setMoney(user.getMoney() - withdrawal.getAmount());
        System.out.println("Expected balance after withdrawal (should be 1200): " + user.getMoney());
    }

    public static void testFixedDepositIntegration() {
        // Assume user and deposit exist
        User user = new User("#new", "TestUser", "password", 1000);
        XMLDBManager.addUser(user);
        FixedDeposit fd = new FixedDeposit(user.getUid(), 1000, 0.05, LocalDate.now(), LocalDate.now().plusYears(1));
        XMLDBManager.addFixedDeposit(fd);

        // Check deposit details
        System.out.println("Fixed Deposit amount (should be 1000): " + fd.getAmount());

        // Simulate fixed deposit maturity processing
        fd.setEndDate(LocalDate.now());  // Set the end date to today
        double interest = fd.calculateInterest();
        user.setMoney((int)(user.getMoney() + (int)interest + fd.getAmount()));
        System.out.println("Expected balance after maturity (should include interest): " + user.getMoney());
    }

    public static void testXMLDBManagerIntegration() {
        // Test adding a user
        User newUser = new User("#new", "NewUser", "newpassword", 500);
        boolean addUserResult = XMLDBManager.addUser(newUser);
        System.out.println("Add user result (should be true): " + addUserResult);

        // Test finding a user
        User foundUser = XMLDBManager.findUser("NewUser");
        System.out.println("Found user name (should be 'NewUser'): " + (foundUser != null ? foundUser.getName() : "User not found"));

        // Test getting all users
        System.out.println("All users (should include 'NewUser'):");
        for (User user : XMLDBManager.getAllUsers(true)) {
            System.out.println(user.getName() + " - " + user.getMoney());
        }
    }

    public static void simulateUserLifecycle() {
        
        User newUser = new User("#new", "User123", "password", 1000);
        boolean addUserResult = XMLDBManager.addUser(newUser);
        System.out.println("User Registration (should be true): " + addUserResult);

        SessionManager session = SessionManager.getInstance();
        session.login(newUser.getUid(), newUser.getName());
        System.out.println("User Logged In (should be true): " + session.isLoggedIn());

        Transaction deposit = new Transaction(newUser.getUid(), "deposit", 200);
        XMLDBManager.addTransaction(deposit);
        System.out.println("Transaction Completed (deposit 200): Balance should be 1200, Actual: " + (newUser.getMoney() + deposit.getAmount()));

        System.out.println("Balance Inquiry: " + newUser.getMoney());
        
        session.logout();
        System.out.println("User Logged Out (should be false): " + session.isLoggedIn());
    }
}
