package io.mavq.reimbursementbackend.model;

import io.mavq.reimbursementbackend.dto.SignUpDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
    private String role;
    private String email;
    private String password;

    @OneToMany(targetEntity = Bill.class,mappedBy = "user",fetch = FetchType.LAZY)
    private List<Bill> bills;

    public User() {
    }

    public User(SignUpDto signUpDto) {
        this.name = signUpDto.getName();
        this.role = signUpDto.getRole();
        this.email = signUpDto.getEmail();
        this.password = signUpDto.getPassword();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
