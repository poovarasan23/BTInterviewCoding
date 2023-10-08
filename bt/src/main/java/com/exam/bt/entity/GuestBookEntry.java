package com.exam.bt.entity;

import javax.persistence.*;

@Entity
@Table(name = "booking_entry")
public class GuestBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String image;
    private String status;
    private String users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return users;
    }

    public void setUser(String user) {
        this.users = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
