package dbconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "Kuttyma@24");
            System.out.println("DB Connected Successfully");
            System.out.println();
            System.out.println("********** Welcome to the Banking System **********");
            System.out.println("Choose an option: ");
            System.out.println("1. Admin Operations");
            System.out.println("2. User Operations");
            System.out.println("3. Account Operations");
            System.out.println("4. Transaction Operations");
            System.out.println("5. Exit");
            int choice = sc.nextInt();

            while (choice != 5) {
                switch (choice) {
                    case 1:
                        adminOperations(sc, con);
                        break;
                    case 2:
                        userOperations(sc, con);
                        break;
                    case 3:
                        accountOperations(sc, con);
                        break;
                    case 4:
                        transactionOperations(sc, con);
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose 1, 2, 3, 4, or 5.");
                }
                System.out.println();
                System.out.println("Choose an option: ");
                System.out.println("1. Admin Operations");
                System.out.println("2. User Operations");
                System.out.println("3. Account Operations");
                System.out.println("4. Transaction Operations");
                System.out.println("5. Exit");
                choice = sc.nextInt();
            }

            System.out.println("Exiting...");
            con.close();
            sc.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adminOperations(Scanner sc, Connection con) {
        try {
            System.out.println("Admin Operations:");
            System.out.println("1. Insert a new admin");
            System.out.println("2. Update an admin's password");
            System.out.println("3. Delete an admin");
            System.out.println("4. View all admins");
            System.out.println("5. Back to Main Menu");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    insertAdmin(sc, con);
                    break;
                case 2:
                    updateAdminPassword(sc, con);
                    break;
                case 3:
                    deleteAdmin(sc, con);
                    break;
                case 4:
                    viewAllAdmins(con);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, 3, 4, or 5.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAdmin(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "INSERT INTO admin (Admin_Id, Username, Password) VALUES (?, ?, ?)";
            System.out.println("Enter the Admin Id: ");
            int id = sc.nextInt();
            System.out.println("Enter the Admin Username:");
            String username = sc.next();
            System.out.println("Enter the Password:");
            String password = sc.next();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setString(2, username);
            pst.setString(3, password);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Admin inserted successfully");
            } else {
                System.out.println("Admin insertion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAdminPassword(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "UPDATE admin SET Password = ? WHERE Admin_Id = ?";
            System.out.println("Enter the Admin Id to update password: ");
            int id = sc.nextInt();
            System.out.println("Enter the new Password:");
            String password = sc.next();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, password);
            pst.setInt(2, id);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Admin password updated successfully");
            } else {
                System.out.println("Admin password update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAdmin(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "DELETE FROM admin WHERE Admin_Id = ?";
            System.out.println("Enter the Admin Id to delete:");
            int id = sc.nextInt();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Admin deleted successfully");
            } else {
                System.out.println("Admin deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllAdmins(Connection con) throws SQLException {
        try {
            String query = "SELECT * FROM admin";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Admin_Id");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                System.out.println("Admin Id: " + id);
                System.out.println("Admin Username: " + username);
                System.out.println("Admin Password: " + password);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void userOperations(Scanner sc, Connection con) {
        try {
            System.out.println("User Operations:");
            System.out.println("1. Insert a new user");
            System.out.println("2. Update user details");
            System.out.println("3. Delete a user");
            System.out.println("4. View all users");
            System.out.println("5. Back to Main Menu");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    insertUser(sc, con);
                    break;
                case 2:
                    updateUser(sc, con);
                    break;
                case 3:
                    deleteUser(sc, con);
                    break;
                case 4:
                    viewAllUsers(con);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, 3, 4, or 5.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "INSERT INTO user (User_Id, Name, Email, Password) VALUES (?, ?, ?, ?)";
            System.out.println("Enter User Id: ");
            int userId = sc.nextInt();
            System.out.println("Enter Name:");
            String name = sc.next();
            System.out.println("Enter Email:");
            String email = sc.next();
            System.out.println("Enter Password:");
            String password = sc.next();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setString(2, name);
            pst.setString(3, email);
            pst.setString(4, password);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("User inserted successfully");
            } else {
                System.out.println("User insertion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "UPDATE user SET Name = ?, Email = ?, Password = ? WHERE User_Id = ?";
            System.out.println("Enter User Id to update details: ");
            int userId = sc.nextInt();
            System.out.println("Enter new Name:");
            String name = sc.next();
            System.out.println("Enter new Email:");
            String email = sc.next();
            System.out.println("Enter new Password:");
            String password = sc.next();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setInt(4, userId);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("User details updated successfully");
            } else {
                System.out.println("User details update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "DELETE FROM user WHERE User_Id = ?";
            System.out.println("Enter the User Id to delete:");
            int userId = sc.nextInt();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllUsers(Connection con) throws SQLException {
        try {
            String query = "SELECT * FROM user";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_Id");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String password = rs.getString("Password");

                System.out.println("User Id: " + userId);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void accountOperations(Scanner sc, Connection con) {
        try {
            System.out.println("Account Operations:");
            System.out.println("1. Insert a new account");
            System.out.println("2. Update account details");
            System.out.println("3. Delete an account");
            System.out.println("4. View all accounts");
            System.out.println("5. Back to Main Menu");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    insertAccount(sc, con);
                    break;
                case 2:
                    updateAccount(sc, con);
                    break;
                case 3:
                    deleteAccount(sc, con);
                    break;
                case 4:
                    viewAllAccounts(con);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, 3, 4, or 5.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAccount(Scanner sc, Connection con) throws SQLException {
        try {
        	 System.out.println("Enter User Id for the new account: ");
             int userId = sc.nextInt();

             // Check if User_Id exists in the user table
             String checkUserQuery = "SELECT COUNT(*) FROM user WHERE User_Id = ?";
             PreparedStatement checkUserPst = con.prepareStatement(checkUserQuery);
             checkUserPst.setInt(1, userId);
             ResultSet rs = checkUserPst.executeQuery();
             rs.next();
             int userCount = rs.getInt(1);

             if (userCount == 0) {
                 System.out.println("User Id does not exist. Please enter a valid User Id.");
                 return;
             }

             System.out.println("Enter initial Balance:");
             double balance = sc.nextDouble();
             
             
            String query = "INSERT INTO account (User_Id, Balance) VALUES (?, ?)";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setDouble(2, balance);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Account inserted successfully");
            } else {
                System.out.println("Account insertion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAccount(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "UPDATE account SET Balance = ? WHERE Account_Id = ?";
            System.out.println("Enter Account Id to update balance: ");
            int accountId = sc.nextInt();
            System.out.println("Enter new Balance:");
            double balance = sc.nextDouble();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, balance);
            pst.setInt(2, accountId);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Account balance updated successfully");
            } else {
                System.out.println("Account balance update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "DELETE FROM account WHERE Account_Id = ?";
            System.out.println("Enter the Account Id to delete:");
            int accountId = sc.nextInt();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountId);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Account deleted successfully");
            } else {
                System.out.println("Account deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllAccounts(Connection con) throws SQLException {
        try {
            String query = "SELECT * FROM account";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("Account_Id");
                int userId = rs.getInt("User_Id");
                double balance = rs.getDouble("Balance");

                System.out.println("Account Id: " + accountId);
                System.out.println("User Id: " + userId);
                System.out.println("Balance: " + balance);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transactionOperations(Scanner sc, Connection con) {
        try {
            System.out.println("Transaction Operations:");
            System.out.println("1. Insert a new transaction");
            System.out.println("2. View all transactions");
            System.out.println("3. Search transactions by Account Id");
            System.out.println("4. Delete a transaction");
            System.out.println("5. Back to Main Menu");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    insertTransaction(sc,con);
                    break;
                case 2:
                    viewAllTransactions(con);
                    break;
                case 3:
                    searchTransactions(sc, con);
                    break;
                case 4:
                    deleteTransaction(sc, con);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, 3, 4, or 5.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public static void insertTransaction(Scanner sc, Connection con) {
        try {
            System.out.println("Enter Account Id for the transaction: ");
            int accountId = sc.nextInt();

            // Check if Account_Id exists in the account table
            String checkAccountQuery = "SELECT COUNT(*) FROM account WHERE Account_Id = ?";
            PreparedStatement checkAccountPst = con.prepareStatement(checkAccountQuery);
            checkAccountPst.setInt(1, accountId);
            ResultSet rs = checkAccountPst.executeQuery();
            rs.next();
            int accountCount = rs.getInt(1);

            if (accountCount == 0) {
                System.out.println("Account Id does not exist. Please enter a valid Account Id.");
                return;
            }

            // Fetch current available balance from account table
            String fetchBalanceQuery = "SELECT Balance FROM account WHERE Account_Id = ?";
            PreparedStatement fetchBalancePst = con.prepareStatement(fetchBalanceQuery);
            fetchBalancePst.setInt(1, accountId);
            ResultSet balanceRs = fetchBalancePst.executeQuery();
            balanceRs.next();
            double currentBalance = balanceRs.getDouble("Balance");

            System.out.println("Enter transaction type (DEPOSIT, WITHDRAW, or TRANSFER):");
            String type = sc.next().toUpperCase();
            sc.nextLine(); // Consume newline character after reading type

            // Validate transaction type
            if (!Arrays.asList("DEPOSIT", "WITHDRAW", "TRANSFER").contains(type)) {
                System.out.println("Invalid transaction type. Transaction aborted.");
                return;
            }

            double amount = 0.0;
            int fromAccountId = 0;
            int toAccountId = 0;
            double oldBalance = currentBalance;

            if (type.equals("DEPOSIT")) {
                System.out.println("Enter transaction Amount:");
                amount = sc.nextDouble();
                sc.nextLine(); // Consume newline character after reading amount
                oldBalance = currentBalance;
                currentBalance += amount; // Update balance for deposit

                // Update account balance in the database
                String updateBalanceQuery = "UPDATE account SET Balance = ? WHERE Account_Id = ?";
                PreparedStatement updateBalancePst = con.prepareStatement(updateBalanceQuery);
                updateBalancePst.setDouble(1, currentBalance);
                updateBalancePst.setInt(2, accountId);
                updateBalancePst.executeUpdate();

            } else if (type.equals("WITHDRAW")) {
                fromAccountId = accountId; // Withdrawal from the same account
                System.out.println("Enter transaction Amount:");
                amount = sc.nextDouble();
                sc.nextLine(); // Consume newline character after reading amount
                if (amount > currentBalance) {
                    System.out.println("Insufficient funds. Transaction aborted.");
                    return;
                }
                oldBalance = currentBalance;
                currentBalance -= amount; // Update balance for withdrawal

                // Update account balance in the database
                String updateBalanceQuery = "UPDATE account SET Balance = ? WHERE Account_Id = ?";
                PreparedStatement updateBalancePst = con.prepareStatement(updateBalanceQuery);
                updateBalancePst.setDouble(1, currentBalance);
                updateBalancePst.setInt(2, accountId);
                updateBalancePst.executeUpdate();

            } else if (type.equals("TRANSFER")) {
                System.out.println("Enter from Account Id:");
                fromAccountId = sc.nextInt();
                sc.nextLine(); // Consume newline character after reading fromAccountId

                // Validate fromAccountId
                checkAccountPst.setInt(1, fromAccountId);
                rs = checkAccountPst.executeQuery();
                rs.next();
                accountCount = rs.getInt(1);

                if (accountCount == 0) {
                    System.out.println("From Account Id does not exist. Transaction aborted.");
                    return;
                }

                System.out.println("Enter to Account Id:");
                toAccountId = sc.nextInt();
                sc.nextLine(); // Consume newline character after reading toAccountId

                // Validate toAccountId
                checkAccountPst.setInt(1, toAccountId);
                rs = checkAccountPst.executeQuery();
                rs.next();
                accountCount = rs.getInt(1);

                if (accountCount == 0) {
                    System.out.println("To Account Id does not exist. Transaction aborted.");
                    return;
                }

                System.out.println("Enter transaction Amount:");
                amount = sc.nextDouble();
                sc.nextLine(); // Consume newline character after reading amount

                // Fetch old balance for fromAccountId and toAccountId
                String fetchFromBalanceQuery = "SELECT Balance FROM account WHERE Account_Id = ?";
                PreparedStatement fetchFromBalancePst = con.prepareStatement(fetchFromBalanceQuery);
                fetchFromBalancePst.setInt(1, fromAccountId);
                ResultSet fromBalanceRs = fetchFromBalancePst.executeQuery();
                fromBalanceRs.next();
                double fromAccountBalance = fromBalanceRs.getDouble("Balance");

                String fetchToBalanceQuery = "SELECT Balance FROM account WHERE Account_Id = ?";
                PreparedStatement fetchToBalancePst = con.prepareStatement(fetchToBalanceQuery);
                fetchToBalancePst.setInt(1, toAccountId);
                ResultSet toBalanceRs = fetchToBalancePst.executeQuery();
                toBalanceRs.next();
                double toAccountBalance = toBalanceRs.getDouble("Balance");

                oldBalance = fromAccountBalance;
                if (amount > fromAccountBalance) {
                    System.out.println("Insufficient funds in from account. Transaction aborted.");
                    return;
                }

                // Update balances for transfer
                fromAccountBalance -= amount;
                toAccountBalance += amount;

                // Update account balances in the database
                String updateFromBalanceQuery = "UPDATE account SET Balance = ? WHERE Account_Id = ?";
                PreparedStatement updateFromBalancePst = con.prepareStatement(updateFromBalanceQuery);
                updateFromBalancePst.setDouble(1, fromAccountBalance);
                updateFromBalancePst.setInt(2, fromAccountId);
                updateFromBalancePst.executeUpdate();

                String updateToBalanceQuery = "UPDATE account SET Balance = ? WHERE Account_Id = ?";
                PreparedStatement updateToBalancePst = con.prepareStatement(updateToBalanceQuery);
                updateToBalancePst.setDouble(1, toAccountBalance);
                updateToBalancePst.setInt(2, toAccountId);
                updateToBalancePst.executeUpdate();

                // Set the new current balance for the from account
                currentBalance = fromAccountBalance;
            }

            LocalDateTime transactionDate = LocalDateTime.now();
            String transactionType = type;

            // Insert transaction into transactions table
            String query = "INSERT INTO transactions (accountID, type, Amount, transactionDate, Transaction_Type, from_account_id, to_account_id, old_balance_amt, current_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountId);
            pst.setString(2, type);
            pst.setDouble(3, amount);
            pst.setObject(4, transactionDate);
            pst.setString(5, transactionType);
            pst.setInt(6, fromAccountId);
            pst.setInt(7, toAccountId);
            pst.setDouble(8, oldBalance);
            pst.setDouble(9, currentBalance);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Transaction inserted successfully");
                System.out.println("Old Balance: " + oldBalance);
                System.out.println("Current Balance: " + currentBalance);
            } else {
                System.out.println("Transaction insertion failed");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            sc.nextLine(); // clear the invalid input from the scanner
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        
  
    public static void viewAllTransactions(Connection con) throws SQLException {
        try {
            String query = "SELECT * FROM transactions";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("TransactionID");
                int accountId = rs.getInt("accountID");
                String type = rs.getString("type");
                double amount = rs.getDouble("Amount");
                String transactionDate = rs.getString("transactionDate");
                String transactionType = rs.getString("Transaction_Type");
                int fromAccountId = rs.getInt("from_account_id");
                int toAccountId = rs.getInt("to_account_id");
                double oldBalance=rs.getDouble("old_balance_amt");
                double currentBalance=rs.getDouble("current_balance");
                

                System.out.println("Transaction Id: " + transactionId);
                System.out.println("Account Id: " + accountId);
                System.out.println("Type: " + type);
                System.out.println("Amount: " + amount);
                System.out.println("Date: " + transactionDate);
                System.out.println("Transaction Type: " + transactionType);
                System.out.println("Old Balance: "+oldBalance);
                System.out.println("Current Balance: "+currentBalance);
                if (transactionType.equals("TRANSFER")) {
                    System.out.println("From Account Id: " + fromAccountId);
                    System.out.println("To Account Id: " + toAccountId);
                }
                System.out.println("------------------------");
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchTransactions(Scanner sc, Connection con) throws SQLException {
        try {
            System.out.println("Enter Account Id to search transactions:");
            int accountId = sc.nextInt();

            String query = "SELECT * FROM transactions WHERE accountID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int transactionId = rs.getInt("TransactionID");
                int accId = rs.getInt("accountID");
                String type = rs.getString("type");
                double amount = rs.getDouble("Amount");
                String transactionDate = rs.getString("transactionDate");
                String transactionType = rs.getString("Transaction_Type");
                int fromAccountId = rs.getInt("from_account_id");
                int toAccountId = rs.getInt("to_account_id");

                System.out.println("Transaction Id: " + transactionId);
                System.out.println("Account Id: " + accId);
                System.out.println("Type: " + type);
                System.out.println("Amount: " + amount);
                System.out.println("Date: " + transactionDate);
                System.out.println("Transaction Type: " + transactionType);
                if (transactionType.equals("TRANSFER")) {
                    System.out.println("From Account Id: " + fromAccountId);
                    System.out.println("To Account Id: " + toAccountId);
                }
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTransaction(Scanner sc, Connection con) throws SQLException {
        try {
            String query = "DELETE FROM transactions WHERE TransactionID = ?";
            System.out.println("Enter the Transaction Id to delete:");
            int transactionId = sc.nextInt();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, transactionId);

            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Transaction deleted successfully");
            } else {
                System.out.println("Transaction deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
