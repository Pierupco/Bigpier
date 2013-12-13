package com.pier.support.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public final class LocaleTool {
    public static final ImmutableMap<String, String> supportedLanguages;
    public static final ImmutableSet<String> supportedLocaleSet;

    static {
        supportedLanguages = ImmutableMap.<String, String>builder()
                .put("en", "English")
                .put("en_US", "English")
                .put("en_CA", "English")
                .put("zh_CN", "\u7B80\u4F53\u4E2D\u6587")
                .build();
        supportedLocaleSet = supportedLanguages.keySet();
    }
}
