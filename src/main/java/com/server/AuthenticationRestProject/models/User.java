package com.server.AuthenticationRestProject.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity implements IUser {

    @NotEmpty(message = "Please provide your first name")
    private String firstname;

    @NotEmpty(message = "Please provide your last name")
    private String lastname;

    @Transient
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = getEmail();
    }

    private String username;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    private boolean enabled;

    private String confirmationtoken;

    public String getConfirmationToken() {
        return confirmationtoken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationtoken = confirmationToken;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append(", ")
                .append("firstName: " + getFirstname())
                .append(", ")
                .append("lastName: " + getLastname())
                .append(", ")
                .append("email: " + getEmail())
                .toString();
    }
}
