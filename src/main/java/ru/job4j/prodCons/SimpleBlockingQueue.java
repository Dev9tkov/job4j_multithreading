package ru.job4j.prodCons;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Bounded blocking queue
 * @author Ilya Devyatkov
 * @since 06.04.2020
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    int capacity = 8;

    int size = 0;

    public synchronized void offer(T value) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        queue.offer(value);
        size++;
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        T rsl = queue.poll();
        size--;
        notifyAll();
        return rsl;
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
