package blockchain.Threads;

import blockchain.Block;
import blockchain.Main;
import blockchain.SignMessage;
import blockchain.VerifyMessage;

import java.util.*;

public class MyThread extends Thread{

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

    public MyThread(List<Block> blockChain, int minerNum){
        this.blockChain = blockChain;
        this.minerNum = minerNum;
    }

    @Override
    public void run() {

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

        Thread chatThread = new DataReaderThread(blockChain, dataOfBlock, blockChain.size());
        chatThread.start();

        ArrayList<String> list = Main.generateHashCode(String.valueOf(id), timeStamp, String.valueOf(numberOfZero));
        currentHashCode = list.get(1);
        magicNumber = list.get(0);

        synchronized (blockChain){

            timeStamp = String.valueOf(new Date().getTime());

            if(blockChain.size() < 5){

                String blockData = dataOfBlock.toString();
                if(!blockData.isEmpty())blockData = blockData.substring(0, blockData.length()-1);

                String[] args = new String[]{blockData};
                try {
                    SignMessage.execute(args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Long endTime = System.currentTimeMillis()/1000;
                timeToGenerate = String.valueOf(endTime-startTime);

                if(blockChain.isEmpty()){
                    numberOfZero = 1;
                    previousHashCode = "0";
                    try {
                        VerifyMessage.verify(new String[]{});
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    blockChain.add(new Block("1", timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, blockData));

                }else {

                    Block lastBlock1 = blockChain.get(blockChain.size()-1);
                    id = Long.parseLong(lastBlock1.getId());
                    id++;
                    numberOfZero = Integer.parseInt(lastBlock1.getNumberOfZeros());

                    try {
                        VerifyMessage.verify(new String[]{});
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    if(Long.parseLong(timeToGenerate) <= 1) numberOfZero++;
                    else if(Long.parseLong(timeToGenerate) > 6) numberOfZero--;

                    previousHashCode = lastBlock1.getCurrentHashCode();
                    blockChain.add(new Block(String.valueOf(id), timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum, blockData));
                }
            }

        }

    }
}

