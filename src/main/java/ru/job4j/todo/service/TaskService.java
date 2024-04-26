package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task add(Task task);

    boolean edit(Task task);

    void deleteById(int id);

    Optional<Task> findById(int id);

    Collection<Task> findNew();

    Collection<Task> findCompleted();

    Collection<Task> findAll();
}
