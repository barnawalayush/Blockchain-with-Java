package blockchain.Model;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Message {

    private StringBuilder dataOfBlock;
    private PublicKey publicKey;
    private byte[] signature;
    private PrivateKey privateKey;

    public Message() {
    }

    public Message(StringBuilder dataOfBlock, PublicKey publicKey, byte[] signature, PrivateKey privateKey) {
        this.dataOfBlock = dataOfBlock;
        this.publicKey = publicKey;
        this.signature = signature;
        this.privateKey = privateKey;
    }

    public StringBuilder getDataOfBlock() {
        return dataOfBlock;
    }

    public void setDataOfBlock(StringBuilder dataOfBlock) {
        this.dataOfBlock = dataOfBlock;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
