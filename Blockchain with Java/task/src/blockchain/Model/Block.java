package blockchain.Model;

import java.util.List;

public class Block {

    private String id;
    private int minerNum;
    private String timestamp;
    private String previousHashCode;
    private String currentHashCode;
    private String magicNumber;
    long timeToGenerate;
    private String numberOfZeros;
    private Message message;
    private List<Transaction> transactionList;

    public Block(){}

    public Block(String id, String timestamp, String previousHashCode, String currentHashCode, String magicNumber, long timeToGenerate, String numberOfZeros, int minerNum, Message message, List<Transaction> transactionList) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousHashCode = previousHashCode;
        this.currentHashCode = currentHashCode;
        this.magicNumber = magicNumber;
        this.timeToGenerate = timeToGenerate;
        this.numberOfZeros = numberOfZeros;
        this.minerNum = minerNum;
        this.message = message;
        this.transactionList = transactionList;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPreviousHashCode() {
        return previousHashCode;
    }

    public String getCurrentHashCode() {
        return currentHashCode;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public int getMinerNum() {
        return minerNum;
    }

    public long getTimeToGenerate() {
        return timeToGenerate;
    }

    public String getNumberOfZeros() {
        return numberOfZeros;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

}
