package com.sosyalmedyaapp2.sosyalmedyaapp2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @Column(name = "contentPost")
    private String content;
    private String postPicture;

    private LocalDateTime createdAtPost;

    @PrePersist
    protected void onCreatePost() {
        this.createdAtPost = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostPicture() {
        return postPicture;
    }

    public void setPostPicture(String postPicture) {
        this.postPicture = postPicture;
    }

    public LocalDateTime getCreatedAtPost() {
        return createdAtPost;
    }

    public void setCreatedAtPost(LocalDateTime createdAtPost) {
        this.createdAtPost = createdAtPost;
    }

    public Post(String content, String postPicture){
        this.content = content;
        this.postPicture = postPicture;
    }

    public Post() {
    }
}
