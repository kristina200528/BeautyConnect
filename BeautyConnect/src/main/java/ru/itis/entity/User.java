package ru.itis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import ru.itis.dictonary.UserRole;
import ru.itis.dictonary.UserStatus;

import java.io.Serializable;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 5718266650651034373L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String hashPassword;

    private String firstName;

    private String lastName;

    private Integer age;

    @Enumerated(STRING)
    private UserRole role;

    @Enumerated(STRING)
    private UserStatus status;

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

}
