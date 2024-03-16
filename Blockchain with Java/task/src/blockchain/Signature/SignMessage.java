package blockchain.Signature;

import blockchain.Model.Message;

import java.security.InvalidKeyException;
import java.security.Signature;


public class SignMessage {

    public static void sign(Message message) throws InvalidKeyException, Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(message.getPrivateKey());
        rsa.update(message.getDataOfBlock().toString().getBytes());
        message.setSignature(rsa.sign());
    }

}

