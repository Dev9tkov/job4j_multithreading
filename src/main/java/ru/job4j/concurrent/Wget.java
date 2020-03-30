package ru.job4j.concurrent;

/**
 * @author Ilya Devyatkov
 * @since 30.03.2020
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 1; i <= 100; i ++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("\rLoading : " + i  + "%");
            }
        });
        thread.start();
    }
}
