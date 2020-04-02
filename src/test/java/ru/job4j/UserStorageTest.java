package ru.job4j;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Ilya Devyatkov
 * @since 02.04.2020
 */
public class UserStorageTest {
    @Test
    public void whenTransferAmountThenSecondUserHaveMoreAmount() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 50);
        assertThat(user1.getAmount(), is (50));
    }
}