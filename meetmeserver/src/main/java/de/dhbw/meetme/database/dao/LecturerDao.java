package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.Lecturers;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;


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
    public Lecturers findLecturerMail(String mail) {
        Query query = entityManager.createQuery("SELECT u from Lecturers u where u.lecturerMail = :mail");
        query.setParameter("mail", mail);
        return (Lecturers) query.getResultList().get(0);
    }

}
