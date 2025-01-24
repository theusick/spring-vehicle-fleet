package com.theusick.core.security.repository.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserEntity implements UserDetails {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 256)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "manager_entity_roles",
        joinColumns = @JoinColumn(name = "manager_entity_id"))
    @Column(name = "roles")
    @Convert(converter = RoleConverter.class)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Converter(autoApply = true)
    public static class RoleConverter implements AttributeConverter<Role, Integer> {

        @Override
        public Integer convertToDatabaseColumn(Role role) {
            return role != null ? role.getCode() : null;
        }

        @Override
        public Role convertToEntityAttribute(Integer value) {
            return value != null ? Role.fromCode(value) : null;
        }
    }

}
