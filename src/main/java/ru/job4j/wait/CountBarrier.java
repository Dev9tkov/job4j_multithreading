package ru.job4j.wait;

/**
 * @author Ilya Devyatkov
 * @since 06.04.2020
 */
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            while (count!=total) {
                count++;
                monitor.notifyAll();
            }
        }
    }

    public void await() {
        synchronized (monitor) {
             while (count >= total) {
                 try {
                     monitor.wait();
                 } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                 }
             }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(4);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                }, "first"
        );
        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                }, "second"
        );
        first.start();
        second.start();
    }
}
