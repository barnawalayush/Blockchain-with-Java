package blockchain.Threads;

import blockchain.*;
import blockchain.Model.Block;
import blockchain.Model.Message;
import blockchain.Signature.VerifyMessage;
import blockchain.keyPair.GenerateKeys;

import java.util.*;

import static java.lang.Math.round;

public class MinerThread extends Thread{

    private final List<Block> blockChain;
    private Long id;
    private String timeStamp;
    private String previousHashCode;
    private String currentHashCode;
    private String magicNumber;
    private String timeToGenerate;
    private int numberOfZero;
    private int minerNum;
    private StringBuilder dataOfBlock;

    public MinerThread(List<Block> blockChain, int minerNum){
        this.blockChain = blockChain;
        this.minerNum = minerNum;
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

            if(blockChain.size() < 5){

                try {
                    if(VerifyMessage.verifySign(message)){
                        addBlock(startTime, message);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private void addBlock(Long startTime, Message message){
        String blockData = message.getDataOfBlock().toString();
        if(!blockData.isEmpty())blockData = blockData.substring(0, blockData.length()-1);

        Long endTime = System.currentTimeMillis()/1000;
        timeToGenerate = String.valueOf(endTime-startTime);

        if(blockChain.isEmpty()){
            numberOfZero = 1;
            previousHashCode = "0";
            blockChain.add(new Block("1", timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, message));

        }else {

            Block lastBlock1 = blockChain.get(blockChain.size()-1);
            id = Long.parseLong(lastBlock1.getId());
            id++;
            numberOfZero = Integer.parseInt(lastBlock1.getNumberOfZeros());

            if(Long.parseLong(timeToGenerate) <= 1) numberOfZero++;
            else if(Long.parseLong(timeToGenerate) > 6) numberOfZero--;

            previousHashCode = lastBlock1.getCurrentHashCode();
            blockChain.add(new Block(String.valueOf(id), timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, message));
        }
    }
}

