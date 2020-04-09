package ru.job4j.prodCons;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(value -> {
                        try {
                            queue.offer(value);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    });
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}