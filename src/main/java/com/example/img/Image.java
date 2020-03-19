package com.example.img;

import com.example.user.User;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String path;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Image() {
    }

    public Image(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
