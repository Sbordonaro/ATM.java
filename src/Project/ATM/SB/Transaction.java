package Project.ATM.SB;

import java.util.Date;

public class Transaction {

    private double amount;              // The transaction amount
    private Date timeStamp;             // The time and date of this transaction
    private String memo;                // The memo for the transaction (description)
    private Account inAccount;          // The account in which the transaction was performed


    //constructors  -- overloading the constructor here as we have two for "Transaction"

    /**
     *
     * @param amount        the amount of the transaction
     * @param inAccount     the account in which the transaction belongs to
     */

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    /**
     *
     * @param amount        the amount transacted
     * @param memo          the memo for the transaction
     * @param inAccount     the account the transaction belongs to
     */

    public Transaction(double amount, String memo, Account inAccount){

        // call the two arg constructor first
        this(amount, inAccount);

        // set the memo
        this.memo = memo;

    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {

        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timeStamp.toString(),
                this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timeStamp.toString(),
            -this.amount, this.memo);
        }
    }






}
