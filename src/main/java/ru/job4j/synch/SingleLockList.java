package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * @author Ilya Devyatkov
 * @since 02.04.2020
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private DynamicContainer<T> container = new DynamicContainer<>();

    public synchronized void add(T value) {
        container.add(value);
    }

    public synchronized T get(int index) {
        return container.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        DynamicContainer<T> copyContainer = new DynamicContainer<>();
        container.forEach(copyContainer::add);
        return copyContainer.iterator();
    }
}
