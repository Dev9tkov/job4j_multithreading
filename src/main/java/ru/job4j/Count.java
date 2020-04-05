package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Ilya Devyatkov
 * @since 02.04.2020
 */
@ThreadSafe
public class Count {

    @GuardedBy("this")

    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }
}
