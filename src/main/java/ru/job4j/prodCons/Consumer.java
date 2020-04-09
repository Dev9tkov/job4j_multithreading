package ru.job4j.prodCons;

/**
 * @author Ilya Devyatkov
 * @since 08.04.2020
 */
public class Consumer implements Runnable{
    private SimpleBlockingQueue<Integer> simpleQueue;

    public Consumer(SimpleBlockingQueue<Integer> simpleQueue) {
        this.simpleQueue = simpleQueue;
    }

    @Override
    public void run() {
        try {
            simpleQueue.poll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
