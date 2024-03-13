package blockchain;

import java.security.MessageDigest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Block> blockChain = new LinkedList<>();

        createBlock(blockChain);
        createBlock(blockChain);
        createBlock(blockChain);
        createBlock(blockChain);
        createBlock(blockChain);

        for(Block block: blockChain){
            System.out.println("Block:");
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHashCode());
            System.out.println("Hash of the block: ");
            System.out.println(block.getCurrentHashCode());
            System.out.println();
        }
    }

    private static Boolean validateBlockChain(List<Block> blockChain) {

        String hashCodePreviousBlock = "0";
        for(Block block: blockChain){
            if(!block.getPreviousHashCode().equals(hashCodePreviousBlock))
                return false;
            hashCodePreviousBlock = block.getCurrentHashCode();
        }
        return true;
    }

    private static void createBlock(List<Block> blockChain) {

        Block lastBlock = null;
        String timeStamp = String.valueOf(new Date().getTime());

        if(!blockChain.isEmpty()){

            lastBlock = blockChain.get(blockChain.size()-1);
            long id = Long.parseLong(lastBlock.getId());
            id += 1;
            String newBlockHashCode = getHashValue(id + timeStamp + lastBlock.getCurrentHashCode());
            blockChain.add(new Block(String.valueOf(id), timeStamp, lastBlock.getCurrentHashCode(), newBlockHashCode));
//            System.out.println(hashCode);

        }else{
            String toHash = "1" + timeStamp + "0";
            String hashCode = getHashValue(toHash);
            blockChain.add(new Block("1", timeStamp, "0", hashCode));
        }

    }

    private static String getHashValue(String toHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(toHash.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }


}
