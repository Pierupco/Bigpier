package com.pier.application.dao;

import com.pier.application.model.User;
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
public class UserDAO {

    private static final Log log = LogFactory.getLog(UserDAO.class);

    private SessionFactory sessionFactory;

    @Resource(name = "sessionFactory")
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Long save(User user) {
        if (user.getId() == null || user.getId() < 1) {
            return this.saveIntoDB(user);
        } else {
            this.attachDirty(user);
            return user.getId();
        }
    }

    @Transactional
    public Long saveIntoDB(User transientInstance) {
        log.debug("persisting User instance");
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
    public void attachDirty(User instance) {
        log.debug("attaching dirty User instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void attachClean(User instance) {
        log.debug("attaching clean User instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void delete(User persistentInstance) {
        log.debug("deleting User instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Transactional
    public User merge(User detachedInstance) {
        log.debug("merging User instance");
        try {
            User result = (User) sessionFactory.getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Transactional
    public User findUserById(Long id) {
        log.debug("getting User instance with id: " + id);
        try {
            User instance = (User) sessionFactory.getCurrentSession().get(User.class, id);
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
    public List<User> findByExample(User instance) {
        log.debug("finding User instance by example");
        try {
            List<User> results = (List<User>) sessionFactory
                    .getCurrentSession().createCriteria(User.class)
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
    public User findUserByName(String userName) {
        User user = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.loginName =:userName");
        query.setString("userName", userName);
        List<User> users = query.list();
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }

    @Transactional
    public User findUserByEmail(String email) {
        User user = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.email =:email");
        query.setString("email", email);
        List<User> users = query.list();
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }

    @Transactional
    public User findUserByPhone(String phone) {
        User user = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.phone =:phone");
        query.setString("phone", phone);
        List<User> users = query.list();
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }

    public List<User> findUsers(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.loginName LIKE '" + userName + "%");
        List<User> users = query.list();

        return users;
    }

}
