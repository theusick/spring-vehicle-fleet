package com.theusick.security.repository.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "managers")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("true")
    @Column(columnDefinition = "BIT", nullable = false)
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private boolean enabled;

}
