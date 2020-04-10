package ru.job4j.nonBlocking;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Ilya Devyatkov
 * @since 10.04.2020
 */
public class CacheTest {

    @Test
    public void whenThrowException() throws InterruptedException {
        Cache cache = new Cache();
        Base model = new Base(1);
        cache.add(model);
        AtomicReference<Exception> exception = new AtomicReference<>();
        Thread thread1 = new Thread(
                () -> {
                    try {
                        cache.update(model);
                    } catch (Exception e) {
                        exception.set(e);
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        cache.update(new Base(1));
                    } catch (Exception e) {
                        exception.set(e);
                    }
                }
        );
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        Assert.assertThat(exception.get().getMessage(), is("version changed"));
    }
}