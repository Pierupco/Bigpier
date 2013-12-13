package com.pier.application.dao.security;

import com.pier.application.model.security.MerchantAuthEntity;
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
public class MerchantAuthDAO {

    private static final Log log = LogFactory.getLog(MerchantAuthDAO.class);

    private SessionFactory sessionFactory;

    @Resource(name = "sessionFactory")
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Long save(MerchantAuthEntity instance) {
        if (instance.getId() == null || instance.getId() < 1) {
            return this.saveIntoDB(instance);
        } else {
            this.attachDirty(instance);
            return instance.getId();
        }
    }

    @Transactional
    public Long saveIntoDB(MerchantAuthEntity transientInstance) {
        log.debug("persisting MerchantAuthEntity instance");
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
    public void attachDirty(MerchantAuthEntity instance) {
        log.debug("attaching dirty MerchantAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void attachClean(MerchantAuthEntity instance) {
        log.debug("attaching clean MerchantAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void delete(MerchantAuthEntity persistentInstance) {
        log.debug("deleting MerchantAuthEntity instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Transactional
    public MerchantAuthEntity merge(MerchantAuthEntity detachedInstance) {
        log.debug("merging MerchantAuthEntity instance");
        try {
            MerchantAuthEntity result = (MerchantAuthEntity) sessionFactory.getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Transactional
    public MerchantAuthEntity findMerchantAuthEntityById(Long id) {
        log.debug("getting MerchantAuthEntity instance with id: " + id);
        try {
            MerchantAuthEntity instance = (MerchantAuthEntity) sessionFactory.getCurrentSession().get(MerchantAuthEntity.class, id);
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
    public List<MerchantAuthEntity> findByExample(MerchantAuthEntity instance) {
        log.debug("finding MerchantAuthEntity instance by example");
        try {
            List<MerchantAuthEntity> results = (List<MerchantAuthEntity>) sessionFactory
                    .getCurrentSession().createCriteria(MerchantAuthEntity.class)
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
    public MerchantAuthEntity findMerchantAuthEntityByName(String merchantAuthEntityName) {
        MerchantAuthEntity MerchantAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from MerchantAuthEntity as merchantAuthEntity where merchantAuthEntity.loginName =:merchantAuthEntityName");
        query.setString("merchantAuthEntityName", merchantAuthEntityName);
        List<MerchantAuthEntity> merchantAuthEntities = query.list();
        if (merchantAuthEntities != null && !merchantAuthEntities.isEmpty()) {
            MerchantAuthEntity = merchantAuthEntities.get(0);
        }

        return MerchantAuthEntity;
    }

    @Transactional
    public MerchantAuthEntity findMerchantAuthEntityByEmail(String email) {
        MerchantAuthEntity MerchantAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from MerchantAuthEntity as merchantAuthEntity where merchantAuthEntity.email =:email");
        query.setString("email", email);
        List<MerchantAuthEntity> merchantAuthEntities = query.list();
        if (merchantAuthEntities != null && !merchantAuthEntities.isEmpty()) {
            MerchantAuthEntity = merchantAuthEntities.get(0);
        }

        return MerchantAuthEntity;
    }

    @Transactional
    public MerchantAuthEntity findMerchantAuthEntityByPhone(String phone) {
        MerchantAuthEntity MerchantAuthEntity = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from MerchantAuthEntity as merchantAuthEntity where merchantAuthEntity.phone =:phone");
        query.setString("phone", phone);
        List<MerchantAuthEntity> merchantAuthEntities = query.list();
        if (merchantAuthEntities != null && !merchantAuthEntities.isEmpty()) {
            MerchantAuthEntity = merchantAuthEntities.get(0);
        }

        return MerchantAuthEntity;
    }

    public List<MerchantAuthEntity> findMerchantAuthEntitys(String merchantAuthEntityName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from MerchantAuthEntity as merchantAuthEntity where merchantAuthEntity.loginName LIKE '" + merchantAuthEntityName + "%");
        List<MerchantAuthEntity> merchantAuthEntities = query.list();

        return merchantAuthEntities;
    }

}
