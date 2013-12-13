package com.pier.application.model.enums;

import com.google.common.collect.ImmutableMap;

public enum BusinessType {
    ELECTRONICS,
    PHARMACY,
    COSMETICS,
    APPAREL,
    UNKNOWN;

    private static ImmutableMap<String, BusinessType> map =
            new ImmutableMap.Builder<String, BusinessType>()
                    .put(ELECTRONICS.name(), ELECTRONICS)
                    .put(PHARMACY.name(), PHARMACY)
                    .put(COSMETICS.name(), COSMETICS)
                    .put(APPAREL.name(), APPAREL)
                    .build();

    public static BusinessType fromString(final String value) {
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
