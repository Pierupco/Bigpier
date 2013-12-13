package com.pier.application.model.security;

import com.pier.application.model.enums.AccessPlatform;
import com.pier.application.security.SHA256Encoder;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "tbl_user_auth_info")
public class UserAuthEntity implements AuthEntity {
    private Long id = 0L;
    private String email = "";
    private String password = "";
    private String payPin = "";

    @Enumerated(EnumType.STRING)
    private AccessPlatform createdBy;
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private AccessPlatform modifiedBy;
    private Date modifiedOn;
    @Enumerated(EnumType.STRING)
    private AccessPlatform accessedBy;
    private Date accessedOn;

    private Date accountLockDate;
    private boolean isAccountLocked;

    private String deviceToken;
    private String singleOfflineToken;
    private String verificationCode;
    private Long accessIp;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "EMAIL", nullable = true, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PAY_PIN", nullable = true, length = 128)
    public String getPayPin() {
        return payPin;
    }

    /**
     * Setting the payPin, in this method, the pin will be encrypted
     *
     * @param payPin
     */
    public void setPayPin(String payPin) {
        this.payPin = payPin;
    }

    @Column(name = "PASSWORD", nullable = false, length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Setting the password, but in this method, the password will be encrypted.
     *
     * @param password
     */
    public void updatePassword(String password) throws Exception {
        if (!StringUtils.isEmpty(password)) {
            this.password = SHA256Encoder.encode(password);
        }
    }

    public boolean isValidPassword(String password) {
        try {
            return this.password.equals(SHA256Encoder.encode(password)) || this.password.equals(password);
        } catch (Exception e) {
            return false;
        }
    }

    @Column(name = "IS_ACCOUNT_LOCKED", nullable = true)
    public boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACCOUNT_LOCK_DATE", length = 19)
    public Date getAccountLockDate() {
        return this.accountLockDate;
    }

    public void setAccountLockDate(Date accountLockDate) {
        this.accountLockDate = accountLockDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "CREATED_BY", nullable = false)
    public AccessPlatform getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AccessPlatform createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", nullable = false, length = 19)
    @Nonnull
    public Date getCreatedOn() {
        return (this.createdOn = (this.createdOn == null ? new Date() : this.createdOn));
    }

    public void setCreatedOn(@Nonnull final Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "MODIFIED_BY", nullable = false)
    public AccessPlatform getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(AccessPlatform modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_ON", nullable = false, length = 19)
    public Date getModifiedOn() {
        return (this.modifiedOn = (this.modifiedOn == null ? new Date() : this.modifiedOn));
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Column(name = "ACCESSED_BY", length = 20)
    public AccessPlatform getAccessedBy() {
        return this.accessedBy;
    }

    public void setAccessedBy(AccessPlatform accessPlatform) {
        this.accessedBy = accessPlatform;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACCESSED_ON", length = 19)
    public Date getAccessedOn() {
        return this.accessedOn;
    }

    public void setAccessedOn(Date accessedOn) {
        this.accessedOn = accessedOn;
    }

    @Column(name = "DEVICE_TOKEN", length = 128, nullable = false)
    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Column(name = "SINGLE_OFFLINE_TOKEN", length = 256, nullable = true)
    public String getSingleOfflineToken() {
        return singleOfflineToken;
    }

    public void setSingleOfflineToken(String singleOfflineToken) {
        this.singleOfflineToken = singleOfflineToken;
    }

    public void revokeToken() {
        singleOfflineToken = "";
    }

    @Column(name = "ACCESS_IP", nullable = true)
    public Long getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(Long accessIp) {
        this.accessIp = accessIp;
    }

    @Column(name = "VERF_CODE", nullable = true)
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
