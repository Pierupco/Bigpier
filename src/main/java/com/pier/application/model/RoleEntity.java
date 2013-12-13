package com.pier.application.model;

public interface RoleEntity {
    public Long getId();

    public void setPhone(String phone);

    public String getPhone();

    public void setCountryCode(String countryCode);

    public String getCountryCode();

    public String getAddress();

    public String getCity();

    public String getStateCode();

    public String getPostalCode();

    public int getAddressMonths();

}
