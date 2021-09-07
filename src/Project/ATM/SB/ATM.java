package Project.ATM.SB;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ATM {

    public static void main (String[] Args) throws NoSuchAlgorithmException {

        // init scanner
        Scanner sc = new Scanner(System.in);

        // init bank
        Bank theBank = new Bank("Bank of Sample & Project");

        // add a sample user and creates a savings account for them
        User aUser = theBank.addUser("John", "Doe", "1234");

        // adds a checking account for the user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            //stay in login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank,sc);


            //stay in main menu until user quits
            ATM.printUserMenu(curUser,sc);

        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        // inits
        String userID;
        String pinNumber;
        User authUser;

            // prompt the user for user ID and Pin combo until correct
            do {

                System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
                System.out.print("Enter user ID: ");
                userID = sc.nextLine();
                System.out.printf("Enter PIN: ");
                pinNumber = sc.nextLine();

                // try to get the user object related to ID and PIN combo
                authUser = theBank.userLogin(userID, pinNumber);
                if (authUser == null) {
                    System.out.println("Incorrect User ID and/or PIN." + "Please try again.");

                }

            } while (authUser == null);     // continue to loop until successful login

            return authUser;

    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of a user's accounts
        theUser.printAccountsSummary();

        // inits
        int choice;

        //user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show Account Transaction History");
            System.out.println(" 2) Withdrawal");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Please Enter Choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5 ) {
                System.out.println("Invalid option chosen. Please choose 1-5.");
            }
        } while (choice < 1 || choice > 5);

            // process choice 1-5
            switch (choice) {

                case 1:
                    ATM.showTransactionHistory(theUser, sc);
                    break;
                case 2:
                    ATM.withdrawalFunds(theUser,sc);
                    break;
                case 3:
                    ATM.depositFunds(theUser,sc);
                    break;
                case 4:
                    ATM.transferFunds(theUser,sc);
                    break;
                case 5:
                    // goobble up the rest of the previous line
                    sc.nextLine();
                    break;
            }

            // recursive function for case 5 "quit". Calling "printUserMenu" within "printUserMenu"
            // display this menu unless the user quits
            if (choice != 5) {
                ATM.printUserMenu(theUser, sc);
            }
        }




    public static void showTransactionHistory(User theUser, Scanner sc) {

        int theAcct;

        // get account which you want to see transaction history
        do {

            System.out.printf("Enter account number (1-%d) of the account\n" +
                            "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print the transaction history
        theUser.printAcctTransHistory(theAcct);

    }

    private static void transferFunds(User theUser, Scanner sc) {

        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // Get the account to transfer from
        do {
            System.out.printf("Enter the Number (1 -%d) of the Account\n" +
                    "to Transfer From: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);


        // Get the account to transfer to
        do {
            System.out.printf("Enter the Number (1 -%d) of the Account\n" +
                    "to Transfer To: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // Get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max: $%.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount <0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        // Do transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to Account %s",
                theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer from Account %s",
                theUser.getAcctUUID(fromAcct)));

    }

    public static void withdrawalFunds(User theUser, Scanner sc) {

        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // Get the account to transfer from
        do {
            System.out.printf("Enter the Number (1 -%d) of the Account\n" +
                    "to Withdraw From: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // Get the amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max: $%.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount <0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        // gobble up the rest of the previous input line
        sc.nextLine();

        // Now get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);

    }

    public static void depositFunds(User theUser, Scanner sc) {

        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // Get the account to transfer from
        do {
            System.out.printf("Enter the Number (1 -%d) of the Account\n" +
                    "to Deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);

        // Get the amount to deposit
        do {
            System.out.printf("Enter the amount to deposit (max: $%.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount <0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while(amount < 0);

        // gobble up the rest of the previous input line
        sc.nextLine();

        // Now get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}
