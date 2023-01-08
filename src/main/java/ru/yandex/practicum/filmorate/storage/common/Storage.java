package ru.yandex.practicum.filmorate.storage.common;

import ru.yandex.practicum.filmorate.model.StorageObject;

import java.util.List;


public interface Storage <T> {
    public T add (T obj);
    public T update (T obj);
    public T delete (Integer id);
    public List<T> getAll();
    public T getById(Integer id);
}
