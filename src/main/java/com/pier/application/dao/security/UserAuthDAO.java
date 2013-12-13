package com.pier.application.dao.security;

import com.pier.application.model.security.UserAuthEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class UserAuthDAO {

    private static final Log log = LogFactory.getLog(UserAuthDAO.class);

    private SessionFactory sessionFactory;

    @Resource(name = "sessionFactory")
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Long save(UserAuthEntity instance) {
        if (instance.getId() == null || instance.getId() < 1) {
            return this.saveIntoDB(instance);
        } else {
            this.attachDirty(instance);
            return instance.getId();
        }
    }

    @Transactional
    public Long saveIntoDB(UserAuthEntity transientInstance) {
        log.debug("persisting UserAuthEntity instance");
        try {
            final Long id = (Long) sessionFactory.getCurrentSession().save(transientInstance);
            log.debug("persist successful");
            return id;
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    @Transactional
    public void attachDirty(UserAuthEntity instance) {
        log.debug("attaching dirty UserAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void attachClean(UserAuthEntity instance) {
        log.debug("attaching clean UserAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void delete(UserAuthEntity persistentInstance) {
        log.debug("deleting UserAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Transactional
    public UserAuthEntity merge(UserAuthEntity detachedInstance) {
        log.debug("merging UserAuthEntity instance");
        try {
            UserAuthEntity result = (UserAuthEntity) sessionFactory.getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Transactional
    public UserAuthEntity findUserAuthEntityById(Long id) {
        log.debug("getting UserAuthEntity instance with id: " + id);
        try {
            UserAuthEntity instance = (UserAuthEntity) sessionFactory.getCurrentSession().get(UserAuthEntity.class, id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<UserAuthEntity> findByExample(UserAuthEntity instance) {
        log.debug("finding UserAuthEntity instance by example");
        try {
            List<UserAuthEntity> results = (List<UserAuthEntity>) sessionFactory
                    .getCurrentSession().createCriteria(UserAuthEntity.class)
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    @Transactional
    public UserAuthEntity findUserAuthEntityByName(String userAuthEntityName) {
        UserAuthEntity UserAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAuthEntity as userAuthEntity where userAuthEntity.loginName =:userAuthEntityName");
        query.setString("userAuthEntityName", userAuthEntityName);
        List<UserAuthEntity> userAuthEntities = query.list();
        if (userAuthEntities != null && !userAuthEntities.isEmpty()) {
            UserAuthEntity = userAuthEntities.get(0);
        }

        return UserAuthEntity;
    }

    @Transactional
    public UserAuthEntity findUserAuthEntityByEmail(String email) {
        UserAuthEntity UserAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAuthEntity as userAuthEntity where userAuthEntity.email =:email");
        query.setString("email", email);
        List<UserAuthEntity> userAuthEntities = query.list();
        if (userAuthEntities != null && !userAuthEntities.isEmpty()) {
            UserAuthEntity = userAuthEntities.get(0);
        }

        return UserAuthEntity;
    }

    @Transactional
    public UserAuthEntity findUserAuthEntityByPhone(String phone) {
        UserAuthEntity UserAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAuthEntity as userAuthEntity where userAuthEntity.phone =:phone");
        query.setString("phone", phone);
        List<UserAuthEntity> userAuthEntities = query.list();
        if (userAuthEntities != null && !userAuthEntities.isEmpty()) {
            UserAuthEntity = userAuthEntities.get(0);
        }

        return UserAuthEntity;
    }

    public List<UserAuthEntity> findUserAuthEntitys(String userAuthEntityName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAuthEntity as userAuthEntity where userAuthEntity.loginName LIKE '" + userAuthEntityName + "%");
        List<UserAuthEntity> userAuthEntities = query.list();

        return userAuthEntities;
    }

}
