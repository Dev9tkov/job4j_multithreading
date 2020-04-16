package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ilya Devyatkov
 * @since 11.04.2020
 */
public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject(user), body(user), user.getEmail());
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }

    private String body (User user) {
        return new StringBuilder()
                .append("Add a new event to ")
                .append(user.getUserName())
                .toString();
    }

    private String subject (User user) {
        return new StringBuilder()
                .append("Notification ")
                .append(user.getUserName())
                .append(" to email ")
                .append(user.getEmail())
                .toString();
    }
}
