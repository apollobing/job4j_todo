package ru.job4j.todo.dto;

import lombok.Data;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private int id;

    private String title;

    private String description;

    private LocalDateTime created = LocalDateTime.now().withNano(0);

    private boolean done;

    private User user;

    private int priorityId;
}
