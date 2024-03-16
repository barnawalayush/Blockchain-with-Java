package blockchain.Threads;

import blockchain.Model.Block;
import blockchain.Model.Transaction;
import blockchain.Model.User;

import java.util.List;
import java.util.Random;

public class TransactionThread extends Thread {

    final List<User> giverList;
    int size;
    List<Block> blockChain;
    final List<Transaction> transactionList;

    public TransactionThread(List<User> giverlist, List<Block> blockChain, int size, List<Transaction> transactionList) {
        this.giverList = giverlist;
        this.size = size;
        this.blockChain = blockChain;
        this.transactionList = transactionList;
    }

    @Override
    public void run() {

        size++;

        int MaxIndex = giverList.size();
        Random random = new Random();

        while (blockChain.size() != size && blockChain.size() < 15) {

            int randomIndex1 = random.nextInt(MaxIndex);
            User randomGiver = giverList.get(randomIndex1);

            int randomIndex2 = random.nextInt(MaxIndex);
            User randomBorrower = giverList.get(randomIndex2);
            synchronized (giverList) {
                if (!randomBorrower.getName().equals(randomGiver.getName())) {

                    Random random1 = new Random();
                    int amount = random1.nextInt(100);

                    if (amount > 0 && randomGiver.getVirtualCoin() >= amount) {
                        int leftAmount = randomGiver.getVirtualCoin() - amount;
                        randomGiver.setVirtualCoin(leftAmount);
                        int newAmount = randomBorrower.getVirtualCoin() + amount;
                        randomBorrower.setVirtualCoin(newAmount);

                        synchronized (transactionList) {
                            transactionList.add(new Transaction(randomGiver.getName(), randomBorrower.getName(), amount));
                        }

                    }
                }
            }
            try {
                Thread.sleep(100000000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
