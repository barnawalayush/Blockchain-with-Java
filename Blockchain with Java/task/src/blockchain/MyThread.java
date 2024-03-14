package blockchain;
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

    public MyThread(List<Block> blockChain, int minerNum){
        this.blockChain = blockChain;
        this.minerNum = minerNum;
    }

    @Override
    public void run() {

            Long startTime = System.currentTimeMillis()/1000;
            Block lastBlock;

            if(blockChain.isEmpty()){
                id = 1L;
            }else{
                lastBlock = blockChain.get(blockChain.size()-1);
                id = Long.parseLong(lastBlock.getId());
                id++;
            }

            ArrayList<String> list = Main.generateHashCode(null, String.valueOf(id), timeStamp, String.valueOf(numberOfZero));
            currentHashCode = list.get(1);
            magicNumber = list.get(0);

            synchronized (blockChain){

                timeStamp = String.valueOf(new Date().getTime());

                if(blockChain.size() < 5){

                    Long endTime = System.currentTimeMillis()/1000;
                    timeToGenerate = String.valueOf(endTime-startTime);

                    if(blockChain.isEmpty()){
                        numberOfZero = 1;
                        previousHashCode = "0";
                        blockChain.add(new Block("1", timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum));

                    }else {

                        Block lastBlock1 = blockChain.get(blockChain.size()-1);
                        id = Long.parseLong(lastBlock1.getId());
                        id++;
                        numberOfZero = Integer.parseInt(lastBlock1.getNumberOfZeros());

                        if(Long.parseLong(timeToGenerate) <= 1) numberOfZero++;
                        else if(Long.parseLong(timeToGenerate) > 6) numberOfZero--;

                        previousHashCode = lastBlock1.getCurrentHashCode();
                            blockChain.add(new Block(String.valueOf(id), timeStamp, previousHashCode, currentHashCode, magicNumber, Long.parseLong(timeToGenerate), String.valueOf(numberOfZero), minerNum));
                    }
                }

            }

    }
}
