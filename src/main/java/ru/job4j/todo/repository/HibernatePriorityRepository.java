package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Optional<Priority> findById(int id) {
        return hibernateCrudRepository.optional(
                "FROM Priority WHERE id = :fId", Priority.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Priority> findAll() {
        return hibernateCrudRepository.query("FROM Priority", Priority.class);
    }
}
