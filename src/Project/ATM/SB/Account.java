package Project.ATM.SB;

import java.util.ArrayList;

public class Account {

    private String name;                                // The name of the account
    private String uuid;                                // The account ID Number
    private User holder;                                // The user object the owns this account
    private ArrayList<Transaction> transactions;        // The list of transactions for this account

    // Account Constructor
    /**
     *
     * @param name      the name of the account
     * @param holder    the user object that holds this account
     * @param theBank   the bank that issues the account
     */

    public Account (String name, User  holder, Bank theBank) {

        // set account name and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // initialize transactions
        this.transactions = new ArrayList<Transaction>();

    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {

        // get the account's balance
        double balance = this.getBalance();

        // format summary line depending on balance sign (negative or positive)
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s",this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s",this.uuid, balance, this.name);
        }

    }

        public double getBalance() {

            double balance = 0;
            for (Transaction t : this.transactions) {
                balance += t.getAmount();
            }
            return balance;

    }

    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1;  t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();

    }

    public void addTransaction(double amount, String memo) {

        //create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);

    }


}
