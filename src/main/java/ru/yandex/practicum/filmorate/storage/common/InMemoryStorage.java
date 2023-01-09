package ru.yandex.practicum.filmorate.storage.common;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.StorageObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorage<T extends StorageObject> implements Storage<T> {
    private final Map<Integer, T> objects = new HashMap<>();
    private Integer nextId = 0;

    @Override
    public T add(T obj) {
        obj.setId(++nextId);
        objects.put(nextId, obj);
        return obj;
    }

    @Override
    public T update(T obj) {
        if (!objects.containsKey(obj.getId())) {
            throw new ResourceNotFoundException(String.format("Object with id %d for update is not found", obj.getId()));
        }
        objects.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public T delete(Integer id) {
        T obj = objects.remove(id);
        if (obj == null) {
            throw new ResourceNotFoundException(String.format("Object with id %d for removal is not found", id));
        }
        return obj;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<> (objects.values());
    }

    @Override
    public T getById(Integer id) {
        T obj = objects.get(id);
        if (obj == null) {
            throw new ResourceNotFoundException(String.format("Object with id %d is not found", id));
        }
        return obj;
    }
}