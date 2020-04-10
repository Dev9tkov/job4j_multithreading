package ru.job4j.nonBlocking;

/**
 * @author Ilya Devyatkov
 * @since 10.04.2020
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
