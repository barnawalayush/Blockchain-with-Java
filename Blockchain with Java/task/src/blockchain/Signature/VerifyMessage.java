package blockchain.Signature;

import blockchain.Model.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

public class VerifyMessage {

    public static boolean verifySign(Message message) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(message.getPublicKey());
        sig.update(message.getDataOfBlock().toString().getBytes());

        return sig.verify(message.getSignature());
    }

}
