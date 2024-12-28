package com.theusick.security.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    USER(1),
    MANAGER(2);

    private final int code;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    public static Role fromCode(int code) {
        for (Role role : Role.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }

}
