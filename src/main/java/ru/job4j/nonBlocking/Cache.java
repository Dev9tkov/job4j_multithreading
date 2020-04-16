package ru.job4j.nonBlocking;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ilya Devyatkov
 * @since 10.04.2020
 */
public class Cache {

    private ConcurrentHashMap<Integer, Base> storage = new ConcurrentHashMap<>();

    public void add(Base model) {
        storage.put(model.getId(), model);
    }

    public void delete(Base model) {
        storage.remove(model.getId());
    }

    public void update(Base model) throws OptimisticException {
        storage.computeIfPresent(model.getId(),
                (id, value) -> {
                int version = model.getVersion();
                if (value.getVersion() != version) {
                    throw new OptimisticException("version changed");
                }
                model.setVersion(version + 1);
                return model;
            });
    }

}
