package Project.ATM.SB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;                    // firstname of the user
    private String lastName;                     // lastname of the user
    private String uuid;                         // ID number of the user
    private byte pinHash[];                      // user's PIN stored in MD5 Hash
    private  ArrayList<Account> accounts;        // list of accounts for the user

    // user constructor
    /**
     *
     * @param firstName     The user's first name
     * @param lastName      The user's last name
     * @param pinNumber     The user's account pin number
     * @param theBank       The Bank object that the user is a customer of
     * @throws NoSuchAlgorithmException
     */

    public User(String firstName, String lastName, String pinNumber, Bank theBank) throws NoSuchAlgorithmException {

        // sets user's first and lastname
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than the original pin value for security purposes
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pinNumber.getBytes());

        // Get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s,%s with ID %s created.\n",  lastName, firstName, this.uuid);


    }
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {

        System.out.printf("\n\n%s's Accounts Summary:\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());

        }

        System.out.println();

    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAccountBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

     public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount,memo);

     }

}



