package blockchain;

import java.security.MessageDigest;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter how many zeros the hash must start with: ");
        Scanner sc = new Scanner(System.in);

        int numberOfZero = sc.nextInt();

        Random random = new Random();

        List<Block> blockChain = new LinkedList<>();
//
        createBlock(blockChain, random, String.valueOf(numberOfZero));
        createBlock(blockChain, random, String.valueOf(numberOfZero));
        createBlock(blockChain, random, String.valueOf(numberOfZero));
        createBlock(blockChain, random, String.valueOf(numberOfZero));
        createBlock(blockChain, random, String.valueOf(numberOfZero));

        for(Block block: blockChain){
            System.out.println("Block:");
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Magic number: " + block.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHashCode());
            System.out.println("Hash of the block: ");
            System.out.println(block.getCurrentHashCode());
            System.out.println("Block was generating for " + block.getTimeToGenerate() + " seconds");
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

    private static void createBlock(List<Block> blockChain, Random random, String numberOfZero) {

        Block lastBlock = null;
        String timeStamp = String.valueOf(new Date().getTime());
        String magicNumber = null;
        long timeToGenerate;

        if(!blockChain.isEmpty()){

            lastBlock = blockChain.get(blockChain.size()-1);
            long id = Long.parseLong(lastBlock.getId());
            id += 1;

            String newBlockHashCode = null;
            int count = 0;
            long startTime = System.currentTimeMillis();
            while(true){
                magicNumber = String.valueOf(random.nextInt());
                newBlockHashCode = getHashValue(id + timeStamp + lastBlock.getCurrentHashCode() + magicNumber);
                for(int i=0; i<newBlockHashCode.length(); i++){
                    if(newBlockHashCode.charAt(i) == '0'){
                        count++;
                    }else break;
                }
                if(count == Integer.parseInt(numberOfZero)){
                    break;
                }else count = 0;
            }
            long endTime = System.currentTimeMillis();

            blockChain.add(new Block(String.valueOf(id), timeStamp, lastBlock.getCurrentHashCode(), newBlockHashCode, magicNumber, endTime-startTime));
//            System.out.println(hashCode);

        }else{
            String newBlockHashCode = null;
            int count = 0;
            long startTime = System.currentTimeMillis();
            while(true){
                magicNumber = String.valueOf(random.nextInt());
                newBlockHashCode = getHashValue("1" + timeStamp + "0" + magicNumber);
                System.out.println(newBlockHashCode);
                for(int i=0; i<newBlockHashCode.length(); i++){
                    if(newBlockHashCode.charAt(i) == '0'){
                        count++;
                    }else break;
                }
                if(count == Integer.parseInt(numberOfZero)){
                    break;
                }else count = 0;
            }
            long endTime = System.currentTimeMillis();
            blockChain.add(new Block("1", timeStamp, "0", newBlockHashCode, magicNumber, endTime-startTime));
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
