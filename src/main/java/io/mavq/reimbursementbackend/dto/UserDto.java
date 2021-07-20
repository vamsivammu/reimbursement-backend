package io.mavq.reimbursementbackend.dto;

import io.mavq.reimbursementbackend.model.User;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String name;
    private Integer role;
    private String email;
    private String token;

    public UserDto(User user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.token = token;
    }

    public UserDto(User user){
        this.name = user.getName();
        this.role = user.getRole();
        this.email = user.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
