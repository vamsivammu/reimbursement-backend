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
    private Integer role;
    private String email;
    private String password;

    @OneToMany(targetEntity = Bill.class,mappedBy = "user")
    private List<Bill> bills;

    public User() {
    }

    public User(SignUpDto signUpDto) {
        this.name = signUpDto.getName();
        this.role = signUpDto.getRole();
        this.email = signUpDto.getEmail();
        this.password = signUpDto.getPassword();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
