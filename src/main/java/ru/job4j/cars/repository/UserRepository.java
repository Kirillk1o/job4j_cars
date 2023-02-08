package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE User SET login = :fLogin WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователей отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var rsl = session.createQuery(
                    "FROM User ORDER BY id", User.class).list();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return List.of();
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var queryUser = session.createQuery(
                    "FROM User as u WHERE u.id = :fId", User.class);
            queryUser.setParameter("fId", id);
            var rsl = queryUser.uniqueResultOptional();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var query = session.createQuery(
                    "FROM User as u WHERE u.login LIKE :fKey", User.class);
            query.setParameter("fKey", "%" + key + "%");
            var rsl = query.getResultList();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return List.of();
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var queryUser = session.createQuery(
                    "FROM User as u WHERE u.login = :fLogin", User.class);
            queryUser.setParameter("fLogin", login);
            var rsl = queryUser.uniqueResultOptional();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.empty();
    }
}
