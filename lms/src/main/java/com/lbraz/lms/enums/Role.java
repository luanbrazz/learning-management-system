package com.lbraz.lms.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_STUDENT("ROLE_STUDENT");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}