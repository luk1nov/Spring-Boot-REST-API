package com.epam.esm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.Permission.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
    ADMIN(EnumSet.allOf(Permission.class)),
    USER(Set.of(TAGS_READ, CERTIFICATES_READ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toSet());
    }
}
