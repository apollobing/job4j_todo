package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Collection<Category> findAll() {
        return hibernateCrudRepository.query("FROM Category", Category.class);
    }
}
