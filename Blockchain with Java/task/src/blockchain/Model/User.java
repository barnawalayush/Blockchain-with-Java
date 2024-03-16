package blockchain.Model;

public class User {

    String name;
    int virtualCoin;

    public User(String name, int virtualCoin) {
        this.name = name;
        this.virtualCoin = virtualCoin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVirtualCoin() {
        return virtualCoin;
    }

    public void setVirtualCoin(int virtualCoin) {
        this.virtualCoin = virtualCoin;
    }
}
