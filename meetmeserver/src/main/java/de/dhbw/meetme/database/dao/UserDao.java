package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;


@ApplicationScoped
public class UserDao extends JpaDao<UuidId, User> {
    public UserDao() {
        super(User.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<User> findByName(String name) {
        Query query = entityManager.createQuery("select u from User u where u.name = :name");
        query.setParameter("name", name);
        return (Collection<User>) query.getResultList();
    }
    public Collection<User> findByEmail(String email) {
        Query query = entityManager.createQuery("select u from User u where u.email = :email");
        query.setParameter("email", email);
        return (Collection<User>) query.getResultList();
    }

    public User findByUserName(String name) {
        Query query = entityManager.createQuery("SELECT u from User u where u.name = :name"); //prev.: from User u where u.username = :username"
        query.setParameter("name", name);
        return (User) query.getResultList().get(0);
    }
}
