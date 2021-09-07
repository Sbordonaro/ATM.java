package Project.ATM.SB;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    // Constructor for Bank
    // Creates a new Bank object with an empty list of Accounts and Users
    public Bank (String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }



    public String getNewUserUUID() {

        // user uuid initialization
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // continue to loop until we get a unique ID
        do {

            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();

            }

            //check to make sure the ID is actually unique
            nonUnique = false;
            for(User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }


        } while (nonUnique);


        return uuid;

    }

    public String getNewAccountUUID() {

        // account uuid initialization
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        // continue to loop until we get a unique ID
        do {

            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();

            }

            //check to make sure the ID is actually unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }


        } while (nonUnique);


        return uuid;
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public User addUser (String firstName, String lastName, String pinNumber) throws NoSuchAlgorithmException {

        // create a new user object and add it to our list
        User newUser = new User(firstName, lastName, pinNumber, this);
        this.users.add(newUser);

        // create a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pinNumber) {

        // first search through list of users
        for (User u : this.users) {

            // check user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pinNumber)) {
                return u;
            }
        }
        // if user ID or Pin are incorrect or non-existent, then returns null
        return null;
    }

    public String getName() {
        return this.name;
    }
}


