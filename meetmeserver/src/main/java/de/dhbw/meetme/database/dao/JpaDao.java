package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.PersistentObject;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public abstract class JpaDao<ID extends UuidId, TYPE extends PersistentObject> implements Dao<ID, TYPE> {
  protected Class<TYPE> entityClass;
  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  @PersistenceContext
  protected EntityManager entityManager;

  public JpaDao(Class<TYPE> entityClass) {
    this.entityClass = entityClass;
  }

  public void persist(TYPE entity) {
    entityManager.persist(entity);
  }

  public void delete(ID id) {
    try {
      TYPE entity = get(id);
      entityManager.remove(entity);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public TYPE get(ID id) {
    return entityManager.find(entityClass, id);
  }

  @SuppressWarnings("unchecked")
  public Collection<TYPE> list() {
    Query q = entityManager.createQuery("select e from " + entityClass.getName() + " e");
    return (Collection<TYPE>)q.getResultList();
  }

}
