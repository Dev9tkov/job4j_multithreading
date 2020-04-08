package ru.job4j.prodCons;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ilya Devyatkov
 * @since 06.04.2020
 */
public class SimpleBlockingQueueTest {

    @Test
    public void whenAddInEmptyQueueThenPollAndTerminated() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread prod = new Thread(producer);
        Thread cons = new Thread(consumer);

        prod.start();
        prod.join();

        queue.offer(1);

        cons.start();
        cons.join();

        assertSame(prod.getState(), Thread.State.TERMINATED);
        assertSame(cons.getState(), Thread.State.TERMINATED);
    }
}