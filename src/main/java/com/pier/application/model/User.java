package com.pier.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "tbl_user_info")
public class User implements RoleEntity {

    private Long id = 0l;
    private String email = "";
    private String phone = "";

    private String title;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";

    private String address = "";
    private String city = "";
    private String stateCode = "";
    private String countryCode = "";
    private String postalCode = "";
    private int addressMonths;

    private byte[] photo;
    private Date dob;
    private boolean student;
    private String martialStatus;
    private int dependents;
    private boolean isDependent;
    private String degree;
    private String ssn;
    private String driverLicense;
    private String driverLicenseState;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TITLE", length = 24)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "FIRST_NAME", nullable = true, length = 128)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "MIDDLE_NAME", nullable = true, length = 128)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "LAST_NAME", nullable = true, length = 128)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Column(name = "LENGTH_STAY_MONTHS")
    public int getAddressMonths() {
        return addressMonths;
    }

    public void setAddressMonths(int addressMonths) {
        this.addressMonths = addressMonths;
    }

    @Column(name = "PHOTO")
    @JsonIgnore
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Column(name = "DOB", nullable = true)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Column(name = "STUDENT", nullable = false)
    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    @Column(name = "MARITIAL_STATUS", length = 24)
    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    @Column(name = "DEPENDENTS", nullable = false)
    public int getDependents() {
        return dependents;
    }

    public void setDependents(int dependents) {
        this.dependents = dependents;
    }

    @Column(name = "DEGREE", length = 24)
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Column(name = "SSN_NUMBER", nullable = true)
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Column(name = "DRIVER_LICENSE", nullable = true)
    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    @Column(name = "DRIVER_LICENSE_STATE", nullable = true)
    public String getDriverLicenseState() {
        return driverLicenseState;
    }

    public void setDriverLicenseState(String driverLicenseState) {
        this.driverLicenseState = driverLicenseState;
    }

    @Column(name = "IS_DEPENDENT", nullable = true)
    public boolean isDependent() {
        return isDependent;
    }

    public void setDependent(boolean isDependent) {
        this.isDependent = isDependent;
    }
}
