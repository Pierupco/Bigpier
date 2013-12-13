package com.pier.application.model.security;

import com.pier.application.model.enums.AccessPlatform;

public class AccessInfo {

    private final Long ip;
    private final AccessPlatform platform;
    private final String deviceToken;

    public AccessInfo(Long ip, String platformStr, String deviceToken) {
        this.ip = ip;
        this.platform = AccessPlatform.fromString(platformStr);
        this.deviceToken = deviceToken;
    }

    public Long getIp() {
        return ip;
    }

    public AccessPlatform getPlatform() {
        return platform;
    }

    public String getDeviceToken() {
        return deviceToken;
    }
}
