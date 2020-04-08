package ru.job4j.prodCons;

/**
 * @author Ilya Devyatkov
 * @since 08.04.2020
 */
public class Producer implements Runnable {
    private SimpleBlockingQueue<Integer> simpleQueue;

    public Producer(SimpleBlockingQueue<Integer> simpleQueue) {
        this.simpleQueue = simpleQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i < simpleQueue.getSize(); i++) {
            simpleQueue.offer(1);
        }
    }
}
