package blockchain.Threads;

import blockchain.*;
import blockchain.Model.Block;
import blockchain.Model.Message;
import blockchain.Model.Transaction;
import blockchain.Model.User;
import blockchain.Signature.VerifyMessage;
import blockchain.keyPair.GenerateKeys;

import java.util.*;

import static java.lang.Math.round;

public class MinerThread extends Thread{

    private final List<Block> blockChain;
    private Long id;
    private String timeStamp;
    String previousHashCode;
    private String currentHashCode;
    private String magicNumber;
    String timeToGenerate;
    private int numberOfZero;
    int minerNum;
    StringBuilder dataOfBlock;
    final List<User> giverList;
    final List<Transaction> transactionList;

    public MinerThread(List<Block> blockChain, int minerNum, List<User> giverList, List<Transaction> transactionList){
        this.blockChain = blockChain;
        this.minerNum = minerNum;
        this.giverList = giverList;
        this.transactionList = transactionList;
    }

    @Override
    public void run() {

        Message message = new Message();

        dataOfBlock = new StringBuilder();
        Long startTime = System.currentTimeMillis()/1000;
        Block lastBlock;

        if(blockChain.isEmpty()){
            id = 1L;
        }else{
            lastBlock = blockChain.get(blockChain.size()-1);
            id = Long.parseLong(lastBlock.getId());
            id++;
        }

        Thread chatThread = new DataReaderThread(blockChain, dataOfBlock, blockChain.size(), message);
        chatThread.start();

        if(!giverList.isEmpty()){
            Thread transactionThread = new TransactionThread(giverList, blockChain, blockChain.size(), transactionList);
            transactionThread.start();
        }

        ArrayList<String> list = Main.generateHashCode(String.valueOf(id), timeStamp, String.valueOf(numberOfZero));
        currentHashCode = list.get(1);
        magicNumber = list.get(0);

        synchronized (blockChain){

            timeStamp = String.valueOf(new Date().getTime());

            String blockData = dataOfBlock.toString();
            if(!blockData.isEmpty())
                blockData = blockData.substring(0, blockData.length()-1);
            message.setDataOfBlock(new StringBuilder(blockData));
            GenerateKeys.generate(message);

            if(blockChain.size() < 15){

                try {
                    if(VerifyMessage.verifySign(message)){

                        synchronized (giverList){
                            User user = new User("miner" + minerNum, 100);
                            giverList.add(user);
                        }

                        addBlock(startTime, message);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private void addBlock(Long startTime, Message message){

        Long endTime = System.currentTimeMillis()/1000;
        timeToGenerate = String.valueOf(endTime-startTime);

        List<Transaction> dummyTransactionList;

        synchronized (transactionList){
            dummyTransactionList = new ArrayList<>(transactionList);
            transactionList.clear();
        }

        if(blockChain.isEmpty()){

            numberOfZero = 1;
            previousHashCode = "0";

            blockChain.add(new Block("1", timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, message, dummyTransactionList));

        }else {

            Block lastBlock1 = blockChain.get(blockChain.size()-1);
            id = Long.parseLong(lastBlock1.getId());
            id++;
            numberOfZero = Integer.parseInt(lastBlock1.getNumberOfZeros());

            if(Long.parseLong(timeToGenerate) <= 1) numberOfZero++;
            else if(Long.parseLong(timeToGenerate) > 6) numberOfZero--;

            previousHashCode = lastBlock1.getCurrentHashCode();
            blockChain.add(new Block(String.valueOf(id), timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, message, dummyTransactionList));
        }
    }
}

