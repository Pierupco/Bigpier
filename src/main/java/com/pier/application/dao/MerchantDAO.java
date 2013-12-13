package com.pier.application.dao;

import com.pier.application.model.Merchant;
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
public class MerchantDAO {

    private static final Log log = LogFactory.getLog(MerchantDAO.class);

    private SessionFactory sessionFactory;

    @Resource(name = "sessionFactory")
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Long save(Merchant merchant) {
        if (merchant.getId() == null || merchant.getId() < 1) {
            return this.saveIntoDB(merchant);
        } else {
            this.attachDirty(merchant);
            return merchant.getId();
        }
    }

    @Transactional
    public Long saveIntoDB(Merchant transientInstance) {
        log.debug("persisting Merchant instance");
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
    public void attachDirty(Merchant instance) {
        log.debug("attaching dirty Merchant instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void attachClean(Merchant instance) {
        log.debug("attaching clean Merchant instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Transactional
    public void delete(Merchant persistentInstance) {
        log.debug("deleting Merchant instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Transactional
    public Merchant merge(Merchant detachedInstance) {
        log.debug("merging Merchant instance");
        try {
            Merchant result = (Merchant) sessionFactory.getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Transactional
    public Merchant findMerchantById(Long id) {
        log.debug("getting Merchant instance with id: " + id);
        try {
            Merchant instance = (Merchant) sessionFactory.getCurrentSession().get(Merchant.class, id);
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
    public List<Merchant> findByExample(Merchant instance) {
        log.debug("finding Merchant instance by example");
        try {
            List<Merchant> results = (List<Merchant>) sessionFactory
                    .getCurrentSession().createCriteria(Merchant.class)
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
    public Merchant findMerchantByName(String merchantName) {
        Merchant Merchant = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Merchant as merchant where merchant.loginName =:merchantName");
        query.setString("merchantName", merchantName);
        List<Merchant> Merchants = query.list();
        if (Merchants != null && !Merchants.isEmpty()) {
            Merchant = Merchants.get(0);
        }

        return Merchant;
    }

    @Transactional
    public Merchant findMerchantByEmail(String email) {
        Merchant Merchant = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Merchant as merchant where merchant.email =:email");
        query.setString("email", email);
        List<Merchant> Merchants = query.list();
        if (Merchants != null && !Merchants.isEmpty()) {
            Merchant = Merchants.get(0);
        }

        return Merchant;
    }

    @Transactional
    public Merchant findMerchantByPhone(String phone) {
        Merchant Merchant = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Merchant as merchant where merchant.phone =:phone");
        query.setString("phone", phone);
        List<Merchant> Merchants = query.list();
        if (Merchants != null && !Merchants.isEmpty()) {
            Merchant = Merchants.get(0);
        }

        return Merchant;
    }

    public List<Merchant> findMerchants(String merchantName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Merchant as merchant where merchant.loginName LIKE '" + merchantName + "%");
        List<Merchant> merchants = query.list();

        return merchants;
    }

    public List<Merchant> findAllMerchants() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Merchant as merchant");
        List<Merchant> merchants = query.list();
        return merchants;
    }

}
