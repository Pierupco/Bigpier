package com.pier.application.model.enums;

import com.google.common.collect.ImmutableMap;

public enum OwnerType {
    USER,
    MERCHANT,
    UNKNOWN;

    private static ImmutableMap<String, OwnerType> map =
            new ImmutableMap.Builder<String, OwnerType>()
                    .put(MERCHANT.name(), MERCHANT)
                    .put(USER.name(), USER)
                    .build();

    public static OwnerType fromString(final String value) {
        if (map.containsKey(value)) {
            return map.get(value);
        } else {
            return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}
