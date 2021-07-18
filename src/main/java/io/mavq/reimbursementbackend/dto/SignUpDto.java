package io.mavq.reimbursementbackend.dto;

public class SignUpDto {
    private String email;
    private String name;
    private String role;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String hashed){
        this.password = hashed;
    }
}
