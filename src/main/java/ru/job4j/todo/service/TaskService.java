package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

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

    void setUserTimezoneToTask(Task task, User user);

    Collection<Task> setUserTimezoneToTasks(Collection<Task> tasks, User user);
}
