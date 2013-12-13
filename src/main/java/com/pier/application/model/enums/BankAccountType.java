package com.pier.application.model.enums;

import com.google.common.collect.ImmutableMap;

public enum BankAccountType {
    SAVING,
    CHECKING,
    UNKNOWN;

    private static ImmutableMap<String, BankAccountType> map =
            new ImmutableMap.Builder<String, BankAccountType>()
                    .put(SAVING.name(), SAVING)
                    .put(CHECKING.name(), CHECKING)
                    .build();

    public static BankAccountType fromString(final String value) {
        if (map.containsKey(value)) {
            return map.get(value);
        } else {
            return UNKNOWN;
        }
    }
}
