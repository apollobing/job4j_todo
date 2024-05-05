package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Task add(Task task) {
        hibernateCrudRepository.run(session -> {
                    session.persist(task);
                    session.flush();
                    return task.getId() > 0;
                }
        );
        return task;
    }

    @Override
    public boolean edit(Task task) {
        return hibernateCrudRepository.run(
                "UPDATE Task SET title = :fTitle,"
                        + " description = :fDescription,"
                        + " done = :fDone WHERE id = :fId",
                Map.of("fTitle", task.getTitle(), "fDescription", task.getDescription(),
                        "fDone", task.isDone(), "fId", task.getId())
        );
    }

    @Override
    public boolean changeStatus(int id) {
        return hibernateCrudRepository.run(
                "UPDATE Task SET done = (done = false) WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public boolean deleteById(int id) {
        return hibernateCrudRepository.run(
                "DELETE Task WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return hibernateCrudRepository.optional(
                "FROM Task WHERE id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findNew() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        return hibernateCrudRepository.query(
                "FROM Task WHERE created >= :fToday ORDER BY id DESC", Task.class,
                Map.of("fToday", today)
        );
    }

    @Override
    public Collection<Task> findCompleted() {
        return hibernateCrudRepository.query("FROM Task WHERE done = true ORDER BY id DESC", Task.class);
    }

    @Override
    public Collection<Task> findAll() {
        return hibernateCrudRepository.query("FROM Task ORDER BY id DESC", Task.class);
    }
}
