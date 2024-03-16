package blockchain.Model;

public class Block {

    private String id;
    private int minerNum;
    private String timestamp;
    private String previousHashCode;
    private String currentHashCode;
    private String magicNumber;
    long timeToGenerate;
    String numberOfZeros;
    String blockData;
    private Message message;

    public Block(){}

    public Block(String id, String timestamp, String previousHashCode, String currentHashCode, String magicNumber, long timeToGenerate, String numberOfZeros, int minerNum, Message message) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousHashCode = previousHashCode;
        this.currentHashCode = currentHashCode;
        this.magicNumber = magicNumber;
        this.timeToGenerate = timeToGenerate;
        this.numberOfZeros = numberOfZeros;
        this.minerNum = minerNum;
//        this.blockData = blockData;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousHashCode() {
        return previousHashCode;
    }

    public void setPreviousHashCode(String previousHashCode) {
        this.previousHashCode = previousHashCode;
    }

    public String getCurrentHashCode() {
        return currentHashCode;
    }

    public void setCurrentHashCode(String currentHashCode) {
        this.currentHashCode = currentHashCode;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public int getMinerNum() {
        return minerNum;
    }

    public void setMinerNum(int minerNum) {
        this.minerNum = minerNum;
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public long getTimeToGenerate() {
        return timeToGenerate;
    }

    public void setTimeToGenerate(long timeToGenerate) {
        this.timeToGenerate = timeToGenerate;
    }


    public String getNumberOfZeros() {
        return numberOfZeros;
    }

    public void setNumberOfZeros(String numberOfZeros) {
        this.numberOfZeros = numberOfZeros;
    }

//    public String getBlockData() {
//        return blockData;
//    }
//
//    public void setBlockData(String blockData) {
//        this.blockData = blockData;
//    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}