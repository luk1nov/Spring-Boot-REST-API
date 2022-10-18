package com.epam.esm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Permission {
    TAGS_READ("tags:read"),
    TAGS_CREATE("tags:create"),
    TAGS_MODIFY("tags:modify"),

    CERTIFICATES_READ("certificates:read"),
    CERTIFICATES_CREATE("certificates:create"),
    CERTIFICATES_MODIFY("certificates:modify");

    private final String permissionName;
}
