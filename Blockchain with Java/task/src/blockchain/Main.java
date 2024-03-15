package blockchain;

import blockchain.Threads.MyThread;

import java.security.MessageDigest;
import java.util.*;

public class Main {

    private static final List<Block> blockChain = new ArrayList<>();

    public static void main(String[] args) {

        List<Thread> listOfThread = new ArrayList<>();

        int minerNum =1;
        while(blockChain.size() < 5){
            Thread thread = new MyThread(blockChain, minerNum);
            listOfThread.add(thread);
            minerNum++;
            thread.start();
        }

        for(Thread thread: listOfThread){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String blockData = "no message";
        for(Block block: blockChain){
            System.out.println("Block:");
            System.out.println("Created by miner # " + block.getMinerNum());
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Magic number: " + block.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHashCode());
            System.out.println("Hash of the block: ");
            System.out.println(block.getCurrentHashCode());
            if(blockData.equals("no message") || blockData.isEmpty())System.out.println("Block data: " + blockData);
            else{
                System.out.println("Block data: ");
                System.out.println(blockData);
            }
            System.out.println("Block was generating for " + block.getTimeToGenerate() + " seconds");
            if(block.getTimeToGenerate() <= 0){
                System.out.println("N was increased to " + block.getNumberOfZeros());
            }else if(block.getTimeToGenerate() >= 65){
                System.out.println("N was decreased to " + block.getNumberOfZeros());
            }else{
                System.out.println("N stays same");
            }
            System.out.println();
            blockData = block.getBlockData();
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

    public static ArrayList<String> generateHashCode(String id, String timeStamp, String numberOfZero){
        String magicNumber;
        Random random = new Random(0);
        String newBlockHashCode;
        Block lastBlock = null;
        if(!blockChain.isEmpty())
            lastBlock = blockChain.get(blockChain.size()-1);
        int count = 0;
        while(true){
            magicNumber = String.valueOf(random.nextInt());
            if(lastBlock != null)
                newBlockHashCode = getHashValue(id + timeStamp + lastBlock.getCurrentHashCode() + magicNumber);
            else newBlockHashCode = getHashValue(id + timeStamp + "0" + magicNumber);
            for(int i=0; i<newBlockHashCode.length(); i++){
                if(newBlockHashCode.charAt(i) == '0'){
                    count++;
                }else break;
            }
            if(count == Integer.parseInt(numberOfZero)){
                break;
            }else count = 0;
        }

        ArrayList<String> list = new ArrayList<>();
        list.add(magicNumber);
        list.add(newBlockHashCode);
        if(lastBlock != null)list.add(lastBlock.getCurrentHashCode());
        return list;
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
