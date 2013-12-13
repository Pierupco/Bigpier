package com.pier.application.model.security;

import com.pier.application.model.enums.AccessPlatform;

import java.util.Date;

public interface AuthEntity {

    public Long getId();

    public String getEmail();

    public String getPayPin();

    public void setPayPin(String payPin);

    public String getDeviceToken();

    public String getSingleOfflineToken();

    public void setPassword(String password) throws Exception;

    public void updatePassword(String password) throws Exception;

    public boolean isValidPassword(final String password);

    public boolean isAccountLocked();

    public void setAccountLocked(boolean accountLocked);

    public Date getAccountLockDate();

    public void setAccountLockDate(Date accountLockDate);

    public void setDeviceToken(String deviceToken);

    public void setCreatedBy(AccessPlatform createdBy);

    public AccessPlatform getCreatedBy();

    public void setCreatedOn(Date createdOn);

    public Date getCreatedOn();

    public void setModifiedBy(AccessPlatform modifiedBy);

    public AccessPlatform getModifiedBy();

    public void setModifiedOn(Date modifiedOn);

    public Date getModifiedOn();

    public void setAccessedBy(AccessPlatform accessPlatform);

    public AccessPlatform getAccessedBy();

    public void setAccessedOn(Date accessedOn);

    public Date getAccessedOn();

    public void setAccessIp(Long ip);

    public Long getAccessIp();

    public void revokeToken();

    public String getVerificationCode();
}
