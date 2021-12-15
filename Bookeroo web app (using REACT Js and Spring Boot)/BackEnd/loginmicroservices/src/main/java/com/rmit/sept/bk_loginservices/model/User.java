package com.rmit.sept.bk_loginservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Collection;


@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Please enter your full name")
    private String fullName;
    private String address;
    @Column(unique = true)
    private String phoneNumber;
    @NotBlank(message = "Password field is required")
    private String password;

    @Column(unique = true)
    private String abnNumber;
    private String shopName;
    @Transient
    private String confirmPassword;

    @NotBlank(message = "Pick a user type")
    private String userType;
    private boolean approved;
    private boolean banned;

    private Date create_At;
    private Date update_At;

    //OneToMany with Project

    public User() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAbnNumber() {
        return abnNumber;
    }
    public void setAbn_number(String abn_number) {
        this.abnNumber = abn_number;
    }

    public String getAddress(){ return address;}
    public void setAddress(String address){ this.address = address;}

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean getApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved;}

    public boolean getBanned() { return banned; }
    public void setBanned(boolean banned) { this.banned = banned;}

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName;}

    public Date getCreate_At() {
        return create_At;
    }
    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }
    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

    /*
    UserDetails interface methods
     */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}