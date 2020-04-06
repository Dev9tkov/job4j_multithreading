package ru.job4j.synch;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Необходимо создать динамический контейнер
 *
 * @author Ilya Devyatkov
 * @version 01
 * @since 10.12.2019
 */
public class DynamicContainer<E> implements Iterable<E> {
    private E[] container;
    private int index;
    private int size;
    private int capacity = 4;
    private int modCount;

    public DynamicContainer() {
        container = (E[]) new Object[capacity];
    }

    /**
     * Добавление значений в массив
     * @param value
     */
    public void add(E value) {
        if (index == container.length) {
            grow();
            container[index++] = value;
        } else {
            container[index++] = value;
            size++;
        }
    }

    /**
     * Получение значения в массиве по индексу
     * @param index
     * @return
     */
    public E get(int index) {
        return (E) container[index];
    }

    /**
     * Увеличение массива в 2 раза, при нехватке свободных ячеек
     */
    public void grow() {
        container = Arrays.copyOf(container, capacity * 2);
        modCount++;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int expectedModCount = modCount;
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                checkModification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                   return (E) container[index++];
                }
            }

            private void checkModification() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
