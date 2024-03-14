package blockchain;

import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static int numberOfZero = 0;

    public static void main(String[] args) {

        Random random = new Random();

        List<Block> blockChain = new LinkedList<>();
//
        createBlock(blockChain, random);
        createBlock(blockChain, random);
        createBlock(blockChain, random);
        createBlock(blockChain, random);
        createBlock(blockChain, random);

        for(Block block: blockChain){
            System.out.println("Block:");
            System.out.println("Created by miner # " + block.getMinerName());
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Magic number: " + block.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHashCode());
            System.out.println("Hash of the block: ");
            System.out.println(block.getCurrentHashCode());
            System.out.println("Block was generating for " + block.getTimeToGenerate() + " seconds");
            System.out.println("N value: " + block.getNumberOfZeros());
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

    private static void createBlock(List<Block> blockChain, Random random) {

        Block lastBlock = null;
        String timeStamp = String.valueOf(new Date().getTime());
        String magicNumber = null;
        long timeToGenerate;
        String minerName;

        ExecutorService executor = Executors.newCachedThreadPool();

        if(!blockChain.isEmpty()){

            lastBlock = blockChain.get(blockChain.size()-1);
            long id = Long.parseLong(lastBlock.getId());
            id += 1;

            String newBlockHashCode = null;
            int count = 0;
            long startTime = System.currentTimeMillis();

            Callable<ArrayList<String>> task = new MyCallable(magicNumber, String.valueOf(id), timeStamp, lastBlock, random, String.valueOf(numberOfZero));

            Future<ArrayList<String>> future = executor.submit(task);

//            ArrayList<String> list = generateHashCode(magicNumber, String.valueOf(id), timeStamp, lastBlock, random, numberOfZero);

            ArrayList<String> list = null;
            try {
                list = future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();

            newBlockHashCode = list.get(1);
            magicNumber = list.get(0);
            minerName = list.get(2);

            timeToGenerate = endTime-startTime;
            if(timeToGenerate <= 0) numberOfZero++;
            else if(timeToGenerate >= 65) numberOfZero--;


            blockChain.add(new Block(String.valueOf(id), timeStamp, lastBlock.getCurrentHashCode(), newBlockHashCode, magicNumber, endTime-startTime, minerName, String.valueOf(numberOfZero)));

        }else{

            String newBlockHashCode = null;
            int count = 0;
            long startTime = System.currentTimeMillis();

            Callable<ArrayList<String>> task = new MyCallable(magicNumber, "1", timeStamp, lastBlock, random, String.valueOf(numberOfZero));

            Future<ArrayList<String>> future = executor.submit(task);

//            ArrayList<String> list = generateHashCode(magicNumber, "1", timeStamp, lastBlock, random, numberOfZero);
            long endTime = System.currentTimeMillis();

            ArrayList<String> list = null;
            try {
                list = future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            newBlockHashCode = list.get(1);
            magicNumber = list.get(0);
            minerName = list.get(2);

            timeToGenerate = endTime-startTime;
            if(timeToGenerate <= 0) numberOfZero++;
            else if(timeToGenerate >= 65) numberOfZero--;

            blockChain.add(new Block("1", timeStamp, "0", newBlockHashCode, magicNumber, endTime-startTime, minerName, String.valueOf(numberOfZero)));
        }

    }

    private static ArrayList<String> generateHashCode(String magicNumber, String id, String timeStamp, Block lastBlock, Random random, String numberOfZero){
        String newBlockHashCode = null;
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

    static class MyCallable implements Callable<ArrayList<String>> {
        String magicNumber;
        String id;
        String timeStamp;
        Block lastBlock;
        Random random;
        String numberOfZero;

        public MyCallable(String magicNumber, String id, String timeStamp, Block lastBlock, Random random, String numberOfZero) {
            this.magicNumber = magicNumber;
            this.id = id;
            this.timeStamp = timeStamp;
            this.lastBlock = lastBlock;
            this.random = random;
            this.numberOfZero = numberOfZero;
        }

        @Override
        public ArrayList<String> call() {
            ArrayList<String> list = generateHashCode(magicNumber, id, timeStamp, lastBlock, random, numberOfZero);
            list.add(Thread.currentThread().getName());
            return list;
        }
    }

}
