package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Collection<Category> findSelected(TaskDto taskDto) {
        return hibernateCrudRepository.query("FROM Category WHERE id IN (:fId)", Category.class,
                Map.of("fId", taskDto.getCategoryId()));
    }

    @Override
    public Collection<Category> findAll() {
        return hibernateCrudRepository.query("FROM Category", Category.class);
    }
}
