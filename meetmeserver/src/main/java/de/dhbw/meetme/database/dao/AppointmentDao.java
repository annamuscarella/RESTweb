package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;


@ApplicationScoped
public class AppointmentDao extends JpaDao<UuidId, Appointment> {
    public AppointmentDao() {
        super(Appointment.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);






    public Collection<Appointment> getAllAppointment(){
        Query query = entityManager.createQuery("select t from Appointment t group by t.lecturerName order by t.date desc");

        return (Collection<Appointment>) query.getResultList();
    }




    public Collection<Appointment> getLecturerAppointment(String lecturerName){

        Query query = entityManager.createQuery("select t from Appointment t where t.lecturerName=:lecturerName order by t.date desc");
        query.setParameter("lecturerName", lecturerName);

        return (Collection<Appointment>) query.getResultList();
    }


}
