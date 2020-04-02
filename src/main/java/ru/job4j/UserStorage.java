package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Devyatkov
 * @since 02.04.2020
 */
@ThreadSafe
public class UserStorage {

    private List<User> userList = new ArrayList<>();

    public synchronized boolean add (User user) {
        return userList.add(user);
    }

    public synchronized boolean update (int id, User user) {
        boolean result = false;
        for(User val : userList) {
            if (val.getId() == id) {
                val.setId(user.getId());
                val.setAmount(user.getAmount());
                result = true;
            }
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return userList.removeIf(val -> val.equals(user));
    }

    public void transfer(int fromId, int toId, int amount) {
        User from = userList.stream()
                .filter(val -> val.getId() == fromId)
                .findFirst()
                .get();
        User to = userList.stream()
                .filter(val -> val.getId() == toId)
                .findFirst()
                .get();
        synchronized (this) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
        }
    }
}
