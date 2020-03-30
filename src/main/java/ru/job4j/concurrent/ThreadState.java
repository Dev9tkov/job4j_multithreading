package ru.job4j.concurrent;

/**
 * @author Ilya Devyatkov
 * @since 30.03.2020
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        System.out.println(first.getState());
        first.start();

        Thread second = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        System.out.println(second.getState());
        second.start();

        while ((first.getState() == Thread.State.RUNNABLE || second.getState() == Thread.State.RUNNABLE)) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("Работа завершена");
        System.out.println(first.getState());
        System.out.println(second.getState());
    }
}
