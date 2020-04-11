package ru.job4j.pool;

import ru.job4j.prodCons.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ilya Devyatkov
 * @since 11.04.2020
 */
public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    /**
     * ThreadPool initialization
     */
    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new JobWorker(tasks));
        }
        threads.forEach(Thread::start);
    }

    /**
     * wait runnable task
     * @param job
     * @throws InterruptedException
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private final class JobWorker extends Thread {
        private final SimpleBlockingQueue<Runnable> tasks;

        public JobWorker(SimpleBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable job;
                try {
                    job = tasks.poll();
                    job.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
