package com.pier.application.model.enums;

import com.google.common.collect.ImmutableMap;

public enum AccessPlatform {
    MOBILE_APP,
    MOBILE_BROWSER,
    WEB,
    API,
    UNKNOWN;

    private static ImmutableMap<String, AccessPlatform> map =
            new ImmutableMap.Builder<String, AccessPlatform>()
                    .put(MOBILE_APP.name(), MOBILE_APP)
                    .put(MOBILE_BROWSER.name(), MOBILE_BROWSER)
                    .put(WEB.name(), WEB)
                    .put(API.name(), API)
                    .build();

    public static AccessPlatform fromString(final String value) {
        if (map.containsKey(value)) {
            return map.get(value);
        } else {
            return UNKNOWN;
        }
    }

}
