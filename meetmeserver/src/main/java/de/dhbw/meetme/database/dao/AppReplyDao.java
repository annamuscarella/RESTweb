package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.AppReply;
import de.dhbw.meetme.domain.UrgentAppointment;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;


@ApplicationScoped
public class AppReplyDao extends JpaDao<UuidId, AppReply> {
    public AppReplyDao() {
        super(AppReply.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);




//where t.processed=:false

    public AppReply getOpenAppReply(String lecturerName){
        Query query = entityManager.createQuery("select t from AppReply t where t.processed=FALSE AND t.lecturerName=:lecturerName");
        query.setParameter("lecturerName", lecturerName);
        if (query.getResultList().isEmpty()){return null;}

        return (AppReply) query.getResultList().get(0);
    }



/*
    public Collection<UrgentAppointment> getLecturerAppointment(String lecturerName){

        Query query = entityManager.createQuery("select t from UrgentAppointment t where t.lecturerName=:lecturerName order by t.date desc");
        query.setParameter("lecturerName", lecturerName);

        return (Collection<UrgentAppointment>) query.getResultList();
    }
*/

}
