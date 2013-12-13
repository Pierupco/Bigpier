package com.pier.application.service.security;

import com.pier.application.dao.UserDAO;
import com.pier.application.dao.security.UserAuthDAO;
import com.pier.application.model.RoleEntity;
import com.pier.application.model.User;
import com.pier.application.model.security.AuthEntity;
import com.pier.application.model.security.UserAuthEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;


@Service
public class AuthUserService implements AuthRoleEntityService {

    private static final Log log = LogFactory.getLog(AuthUserService.class);

    private UserDAO userDAO;
    private UserAuthDAO userAuthDAO;

    public AuthUserService() {

    }

    @Autowired
    public AuthUserService(UserDAO userDAO, UserAuthDAO userAuthDAO) {
        this.userDAO = userDAO;
        this.userAuthDAO = userAuthDAO;
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByName(String userName) {
        return userDAO.findUserByName(userName);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleByPhone(String phone) {
        return userDAO.findUserByPhone(phone);
    }

    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public RoleEntity getRoleById(Long id) {
        return userDAO.findUserById(id);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    @Nullable
    public Long saveRole(final RoleEntity role) {
        if (role instanceof User) {
            return userDAO.save((User) role);
        }
        return null;
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRole(final RoleEntity role) {
        if (role instanceof User) {
            userDAO.delete((User) role);
        }
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByName(String userName) {
        User user = userDAO.findUserByName(userName);
        userDAO.delete(user);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByEmail(String email) {
        User user = userDAO.findUserByEmail(email);
        userDAO.delete(user);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleByPhone(String phone) {
        User user = userDAO.findUserByPhone(phone);
        userDAO.delete(user);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteRoleById(Long id) {
        User user = userDAO.findUserById(id);
        userDAO.delete(user);
    }

    @Override
    @Transactional(
            propagation = Propagation.NEVER,
            isolation = Isolation.READ_COMMITTED,
            readOnly = true)
    public AuthEntity getAuthEntityById(final Long id) {
        return userAuthDAO.findUserAuthEntityById(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.READ_COMMITTED,
            readOnly = false)
    public void deleteAuthEntityById(final Long id) {
        final UserAuthEntity userAuthEntity = userAuthDAO.findUserAuthEntityById(id);
        userAuthDAO.delete(userAuthEntity);
    }

    @Override
    public void updateAuthEntity(final AuthEntity authEntity) {
        if (authEntity instanceof UserAuthEntity) {
            userAuthDAO.save((UserAuthEntity) authEntity);
        }
    }

    public RoleEntity newRoleInstance() {
        return new User();
    }

    @Override
    public AuthEntity newAuthEntityInstance(final Long id) {
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(id);
        return userAuthEntity;
    }
}
