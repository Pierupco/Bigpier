package com.pier.application.model.search;

import com.pier.application.model.enums.BusinessType;

public class MerchantSearchResult {
    final private String merchantName;
    final private BusinessType merchantBusinessType;

    public MerchantSearchResult(String merchantName, BusinessType merchantBusinessType) {
        this.merchantName = merchantName;
        this.merchantBusinessType = merchantBusinessType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public BusinessType getMerchantBusinessType() {
        return merchantBusinessType;
    }
}
