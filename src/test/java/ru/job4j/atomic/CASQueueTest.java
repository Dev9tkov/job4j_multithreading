package ru.job4j.atomic;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Ilya Devyatkov
 * @since 09.04.2020
 */
public class CASQueueTest {
    @Test
    public void when3PushThen3Poll() {
        CASQueue<Integer> queue = new CASQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
    }
}