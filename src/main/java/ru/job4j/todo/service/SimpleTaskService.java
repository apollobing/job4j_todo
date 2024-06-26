package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTaskService implements TaskService {

    private final TaskRepository taskRepository;

    private final PriorityRepository priorityRepository;

    private final CategoryRepository categoryRepository;

    public SimpleTaskService(TaskRepository taskRepository, PriorityRepository priorityRepository,
                             CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Task add(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCreated(taskDto.getCreated());
        task.setDone(taskDto.isDone());
        task.setUser(taskDto.getUser());
        task.setPriority(priorityRepository.findById(taskDto.getPriorityId()).orElseThrow());
        task.setCategories(new ArrayList<>(categoryRepository.findSelected(taskDto)));
        return taskRepository.add(task);
    }

    @Override
    public boolean edit(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId()).orElseThrow();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(priorityRepository.findById(taskDto.getPriorityId()).orElseThrow());
        task.setCategories(new ArrayList<>(categoryRepository.findSelected(taskDto)));
        return taskRepository.edit(task);
    }

    @Override
    public boolean changeStatus(int id) {
        return taskRepository.changeStatus(id);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.of(taskRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<Task> findNew() {
        return taskRepository.findNew();
    }

    @Override
    public Collection<Task> findCompleted() {
        return taskRepository.findCompleted();
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void setUserTimezoneToTask(Task task, User user) {
        task.setCreated(ZonedDateTime.of(task.getCreated(), ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(user.getTimezone()))
                .toLocalDateTime());
    }

    @Override
    public Collection<Task> setUserTimezoneToTasks(Collection<Task> tasks, User user) {
        for (Task task : tasks) {
            setUserTimezoneToTask(task, user);
        }
        return tasks;
    }
}
