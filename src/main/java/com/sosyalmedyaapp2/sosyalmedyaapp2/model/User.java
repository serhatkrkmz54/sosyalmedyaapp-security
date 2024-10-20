package com.sosyalmedyaapp2.sosyalmedyaapp2.model;

import com.sosyalmedyaapp2.sosyalmedyaapp2.enums.AuthProvider;
import com.sosyalmedyaapp2.sosyalmedyaapp2.enums.Gender;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surName")
    private String surname;
    @Column(unique = true, name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "birthDate")
    private LocalDate birthdate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "bio")
    private String bio;
    @Column(name = "phoneNumber")
    private String phonenumber;
    private String profilePicture;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdat;
    @Enumerated(EnumType.STRING)
    private AuthProvider authprovide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public AuthProvider getAuthprovide() {
        return authprovide;
    }

    public void setAuthprovide(AuthProvider authprovide) {
        this.authprovide = authprovide;
    }

    @PrePersist
    protected void onCreate() {
        this.createdat = LocalDateTime.now();
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public User(Long id, String name, String surname, String email, String password, LocalDate birthdate, Gender gender, String bio, String phonenumber, String profilePicture, LocalDateTime createdat, AuthProvider authprovide) {
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.password = password;
//        this.birthdate = birthdate;
//        this.gender = gender;
//        this.bio = bio;
//        this.phonenumber = phonenumber;
//        this.profilePicture = profilePicture;
//        this.createdat = createdat;
//        this.authprovide = authprovide;
//    }

    public User() {
    }
}
