package blockchain;

public class Block {

    private String id;
    private String timestamp;
    private String previousHashCode;
    private String currentHashCode;
    private String magicNumber;
    long timeToGenerate;

    public Block(String id, String timestamp, String previousHashCode, String currentHashCode, String magicNumber, long timeToGenerate) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousHashCode = previousHashCode;
        this.currentHashCode = currentHashCode;
        this.magicNumber = magicNumber;
        this.timeToGenerate = timeToGenerate;
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

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public long getTimeToGenerate() {
        return timeToGenerate;
    }

    public void setTimeToGenerate(long timeToGenerate) {
        this.timeToGenerate = timeToGenerate;
    }
}
