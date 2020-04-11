package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ilya Devyatkov
 * @since 11.04.2020
 */
public class ThreadPoolTest {

    class Job implements Runnable {
        private int jobId;

        public Job(int jobId) {
            this.jobId = jobId;
        }

        @Override
        public void run() {
            try {
                System.out.println(this + Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Job{"
                    + "jobId=" + jobId
                    + '}';
        }
    }

    @Test
    public void whenJobsAddedThenExecuteByThreadPool() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        List<Runnable> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            jobs.add(new Job(i));
        }
        try {
            for (Runnable val : jobs) {
                pool.work(val);
            }
            Thread.sleep(2000);
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}