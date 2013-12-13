package com.pier.application.service.security;

import com.pier.application.dao.MerchantDAO;
import com.pier.application.dao.security.MerchantAuthDAO;
import com.pier.application.model.Merchant;
import com.pier.application.model.RoleEntity;
import com.pier.application.model.security.AuthEntity;
import com.pier.application.model.security.MerchantAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

@Service
public class AuthMerchantService implements AuthRoleEntityService {

    private MerchantDAO merchantDAO;
    private MerchantAuthDAO merchantAuthDAO;

    public AuthMerchantService() {

    }

    @Autowired
    public AuthMerchantService(final MerchantDAO merchantDAO, final MerchantAuthDAO merchantAuthDAO) {
        this.merchantDAO = merchantDAO;
        this.merchantAuthDAO = merchantAuthDAO;
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByName(String MerchantName) {
        return merchantDAO.findMerchantByName(MerchantName);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByEmail(String email) {
        return merchantDAO.findMerchantByEmail(email);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByPhone(String phone) {
        return merchantDAO.findMerchantByPhone(phone);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleById(Long id) {
        return merchantDAO.findMerchantById(id);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    @Nullable
    public Long saveRole(final RoleEntity roleEntity) {
        if (roleEntity instanceof Merchant) {
            return merchantDAO.save((Merchant) roleEntity);
        }
        return null;
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRole(final RoleEntity roleEntity) {
        if (roleEntity instanceof Merchant) {
            merchantDAO.delete((Merchant) roleEntity);
        }
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByName(String MerchantName) {
        Merchant merchant = merchantDAO.findMerchantByName(MerchantName);
        merchantDAO.delete(merchant);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByEmail(String email) {
        Merchant merchant = merchantDAO.findMerchantByEmail(email);
        merchantDAO.delete(merchant);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByPhone(String phone) {
        Merchant merchant = merchantDAO.findMerchantByPhone(phone);
        merchantDAO.delete(merchant);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleById(Long id) {
        Merchant merchant = merchantDAO.findMerchantById(id);
        merchantDAO.delete(merchant);
    }

    @Override
    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public AuthEntity getAuthEntityById(final Long id) {
        return merchantAuthDAO.findMerchantAuthEntityById(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteAuthEntityById(final Long id) {
        final MerchantAuthEntity merchantAuthEntity = merchantAuthDAO.findMerchantAuthEntityById(id);
        merchantAuthDAO.delete(merchantAuthEntity);
    }

    @Override
    public void updateAuthEntity(final AuthEntity authEntity) {
        if (authEntity instanceof MerchantAuthEntity) {
            merchantAuthDAO.save((MerchantAuthEntity) authEntity);
        }
    }

    @Override
    public AuthEntity newAuthEntityInstance(final Long id) {
        final MerchantAuthEntity merchantAuthEntity = new MerchantAuthEntity();
        merchantAuthEntity.setId(id);
        return merchantAuthEntity;
    }

    @Override
    public RoleEntity newRoleInstance() {
        return new Merchant();
    }

}
