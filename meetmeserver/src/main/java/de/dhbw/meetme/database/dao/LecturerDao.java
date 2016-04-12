package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.Lecturers;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Data Access Object for User
 * <p>
 * This class is responsible to do the CRUD (create, read, update, delete) operations
 * for user objects in the database.
 * <p>
 * There is an alternative implementation UserClassicDao using the traditional way.
 * <p>
 * Decide yourself which one you want to use.
 * You may even mix both approaches.
 */
@ApplicationScoped
public class LecturerDao extends JpaDao<UuidId, Lecturers> {
    public LecturerDao() {
        super(Lecturers.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<Lecturers> listLecturers() {
        Query query = entityManager.createQuery("select u from Lecturers u");
        return (Collection<Lecturers>) query.getResultList();
    }

    public Lecturers findLecturer(String name) {
        Query query = entityManager.createQuery("SELECT u from Lecturers u where u.lecturerLastname = :name");
        query.setParameter("name", name);
        return (Lecturers) query.getResultList().get(0);
    }

}
