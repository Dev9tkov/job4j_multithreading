package ru.job4j.atomic;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ilya Devyatkov
 * @since 09.04.2020
 */
@ThreadSafe
public class CASQueue<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();
    private final AtomicReference<Node<T>> tail = new AtomicReference<>();

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> ref;
        Node<T> currentNode = null;

        do {
            ref = head.get();
            if (ref == null) {
                head.set(newNode);
                tail.set(newNode);
                break;
            } else {
                currentNode = tail.get();
                currentNode.prev = newNode;
            }
        } while (!tail.compareAndSet(currentNode, newNode));
    }


    public T poll() {
        Node<T> temp;
        Node<T> ref;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalStateException("Queue is empty");
            }
            temp = ref.prev;
        } while (!head.compareAndSet(ref, temp));
        ref.prev = null;
        return ref.value;
    }

    private static final class Node<T> {
        final T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }
    }
}
