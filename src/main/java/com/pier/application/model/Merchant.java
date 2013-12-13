package com.pier.application.model;

import com.pier.application.model.enums.BusinessType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_merchant_info")
public class Merchant implements RoleEntity {

    private Long id = 0l;
    private String email = "";
    private String phone = "";

    private String businessName = "";
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;
    private String customBusinessType;

    private String address = "";
    private String city = "";
    private String stateCode = "";
    private String countryCode = "";
    private String postalCode = "";
    private int addressMonths;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EMAIL", length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONE", length = 128)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "BUSINESS_NAME", length = 128)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Column(name = "BUSINESS_TYPE", nullable = true, length = 128)
    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    @Column(name = "CUSTOM_BUSINESS_TYPE", nullable = true, length = 128)
    public String getCustomBusinessType() {
        return customBusinessType;
    }

    public void setCustomBusinessType(String customBusinessType) {
        this.customBusinessType = customBusinessType;
    }

    @Column(name = "ADDRESS", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ADDRESS_CITY", length = 128)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "ADDRESS_STATE_CODE", length = 128)
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Column(name = "ADDRESS_COUNTRY_CODE", length = 128)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "ADDRESS_POSTAL_CODE", length = 128)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "ADDRESS_STAY_MONTHS")
    public int getAddressMonths() {
        return addressMonths;
    }

    public void setAddressMonths(int addressMonths) {
        this.addressMonths = addressMonths;
    }

}
