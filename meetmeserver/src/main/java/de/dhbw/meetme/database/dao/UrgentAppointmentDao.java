package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.UrgentAppointment;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;


@ApplicationScoped
public class UrgentAppointmentDao extends JpaDao<UuidId, UrgentAppointment> {
    public UrgentAppointmentDao() {
        super(UrgentAppointment.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);






    public Collection<UrgentAppointment> getAllAppointment(){
        Query query = entityManager.createQuery("select t from UrgentAppointment t group by t.lecturerName order by t.date desc");

        return (Collection<UrgentAppointment>) query.getResultList();
    }




    public Collection<UrgentAppointment> getLecturerAppointment(String lecturerName){

        Query query = entityManager.createQuery("select t from UrgentAppointment t where t.lecturerName=:lecturerName order by t.date desc");
        query.setParameter("lecturerName", lecturerName);

        return (Collection<UrgentAppointment>) query.getResultList();
    }


}