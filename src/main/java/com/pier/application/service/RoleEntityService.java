package com.pier.application.service;

import com.pier.application.model.RoleEntity;

public interface RoleEntityService {

    public RoleEntity getRoleByName(String RoleName);

    public RoleEntity getRoleByEmail(String email);

    public RoleEntity getRoleByPhone(String phone);

    public RoleEntity getRoleById(Long id);

    public Long saveRole(final RoleEntity Role);

    public void deleteRole(RoleEntity Role);

    public void deleteRoleByName(String RoleName);

    public void deleteRoleByEmail(String email);

    public void deleteRoleByPhone(String phone);

    public void deleteRoleById(Long id);

    public RoleEntity newRoleInstance();
}
