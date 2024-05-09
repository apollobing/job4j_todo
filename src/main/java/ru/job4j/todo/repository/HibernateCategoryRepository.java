package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Optional<Category> findById(int id) {
        return hibernateCrudRepository.optional(
                "FROM Category WHERE id = :fId", Category.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Category> findAll() {
        return hibernateCrudRepository.query("FROM Category", Category.class);
    }
}
