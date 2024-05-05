package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserRepository.class.getName());

    private final HibernateCrudRepository hibernateCrudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            hibernateCrudRepository.run(session -> {
                        session.persist(user);
                        session.flush();
                        return user.getId() > 0;
                    }
            );
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error("User with the same email already exists", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return hibernateCrudRepository.optional(
                "FROM User WHERE email = :fEmail AND password = :fPassword", User.class,
                Map.of("fEmail", email, "fPassword", password)
        );
    }
}
