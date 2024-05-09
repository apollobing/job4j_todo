package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(int id);

    Collection<Category> findAll();
}