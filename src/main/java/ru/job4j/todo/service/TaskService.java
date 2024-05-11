package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task add(TaskDto taskDto);

    boolean edit(TaskDto taskDto);

    boolean changeStatus(int id);

    boolean deleteById(int id);

    Optional<Task> findById(int id);

    Collection<Task> findNew();

    Collection<Task> findCompleted();

    Collection<Task> findAll();
}
