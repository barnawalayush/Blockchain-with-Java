package blockchain.Model;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {

    String giver;
    String borrower;
    int amount;

    public Transaction(String giver, String borrower, int amount) {
        this.giver = giver;
        this.borrower = borrower;
        this.amount = amount;
    }

    public String getGiver() {
        return giver;
    }

    public String getBorrower() {
        return borrower;
    }

    public int getAmount() {
        return amount;
    }

}
