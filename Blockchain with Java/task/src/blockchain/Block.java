package blockchain;

public class Block {

    private String id;
    private String timestamp;
    private String previousHashCode;
    private String currentHashCode;

    public Block(String id, String timestamp, String previousHashCode, String currentHashCode) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousHashCode = previousHashCode;
        this.currentHashCode = currentHashCode;
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
}
