package ru.job4j.concurrent;

/**
 * @author Ilya Devyatkov
 * @since 30.03.2020
 */
public class ConsoleProgress implements Runnable {
    private String sphereUp = "- / | \\ -";
    private String sphereDown ="- \\ | / -";
    private String check = "ne null";

    @Override
    public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (check.equals("- / | \\ -")) {
                        System.out.print("\r load: " + sphereUp);
                        check = sphereDown;
                    } else {
                        System.out.print("\r load: " + sphereDown);
                        check = sphereUp;
                    }
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            progress.interrupt();
        }
        progress.interrupt();
    }
}
