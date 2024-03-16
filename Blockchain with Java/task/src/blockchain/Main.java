package blockchain;

import blockchain.Model.Block;
import blockchain.Model.Transaction;
import blockchain.Model.User;
import blockchain.Threads.MinerThread;

import java.security.MessageDigest;
import java.util.*;

public class Main {

    private static final List<Block> blockChain = new ArrayList<>();

    public static void main(String[] args) {

        List<User> giverList = new ArrayList<>();

        List<Transaction> transactionList = new ArrayList<>();

        giverList.add(new User("Bob", 0));
        giverList.add(new User("Alice", 0));
        giverList.add(new User("Sherlock", 0));
        giverList.add(new User("Antonio", 0));
        giverList.add(new User("Jessica", 0));

        List<Thread> listOfThread = new ArrayList<>();

        int minerNum = 1;
        while (blockChain.size() < 15) {
            Thread thread = new MinerThread(blockChain, minerNum, giverList, transactionList);
            listOfThread.add(thread);
            minerNum++;
            thread.start();
        }

        for (Thread thread : listOfThread) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        printBlockDetail(blockChain);

    }

    private static void printBlockDetail(List<Block> blockChain) {

        String blockData = "no transaction";
        for (Block block : blockChain) {
            System.out.println("Block:");
            System.out.println("Created by : miner" + block.getMinerNum());
            System.out.println("miner" + block.getMinerNum() + " gets 100 VC");
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Magic number: " + block.getMagicNumber());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHashCode());
            System.out.println("Hash of the block: ");
            System.out.println(block.getCurrentHashCode());

            List<Transaction> transactionlist = block.getTransactionList();
            StringBuilder dataOfBlock = new StringBuilder();
            for (Transaction transaction : transactionlist) {
                dataOfBlock.append(transaction.getGiver() + " sent " + transaction.getAmount() + "VC to " + transaction.getBorrower()).append("\n");
            }
            blockData = dataOfBlock.toString();
            if (!blockData.isEmpty()) blockData = blockData.substring(0, blockData.length() - 1);
            else blockData = "No transaction";

            System.out.println("Block data: ");
            System.out.println(blockData);
            System.out.println("Block was generating for " + block.getTimeToGenerate() + " seconds");
            if (block.getTimeToGenerate() <= 0) {
                System.out.println("N was increased to " + block.getNumberOfZeros());
            } else if (block.getTimeToGenerate() >= 15) {
                System.out.println("N was decreased to " + block.getNumberOfZeros());
            } else {
                System.out.println("N stays same");
            }
            System.out.println();
        }

    }

    public static ArrayList<String> generateHashCode(String id, String timeStamp, String numberOfZero) {
        String magicNumber;
        Random random = new Random(0);
        String newBlockHashCode;
        Block lastBlock = null;
        if (!blockChain.isEmpty())
            lastBlock = blockChain.get(blockChain.size() - 1);
        int count = 0;
        while (true) {
            magicNumber = String.valueOf(random.nextInt());
            if (lastBlock != null)
                newBlockHashCode = getHashValue(id + timeStamp + lastBlock.getCurrentHashCode() + magicNumber);
            else newBlockHashCode = getHashValue(id + timeStamp + "0" + magicNumber);
            for (int i = 0; i < newBlockHashCode.length(); i++) {
                if (newBlockHashCode.charAt(i) == '0') {
                    count++;
                } else break;
            }
            if (count == Integer.parseInt(numberOfZero)) {
                break;
            } else count = 0;
        }

        ArrayList<String> list = new ArrayList<>();
        list.add(magicNumber);
        list.add(newBlockHashCode);
        if (lastBlock != null) list.add(lastBlock.getCurrentHashCode());
        return list;
    }

    private static String getHashValue(String toHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(toHash.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
