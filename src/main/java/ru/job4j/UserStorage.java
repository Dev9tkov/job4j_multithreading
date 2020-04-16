package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ilya Devyatkov
 * @since 02.04.2020
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> userMap = new HashMap<>();

    public synchronized User add (User user) {
        return userMap.put(user.getId(), user);
    }

    public synchronized User update (User user) {
        return userMap.put(user.getId(), user);

    }

    public synchronized User delete(User user) {
        return userMap.remove(user.getId());
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = userMap.get(fromId);
        User to = userMap.get(toId);
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
    }
}
