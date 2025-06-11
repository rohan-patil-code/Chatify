package com.Chatify.Model;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isGroup;
    private String avatarurl;

    @ManyToOne
@JoinColumn(name = "user_id") 
private Users user;


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
    public boolean isGroup() {
        return isGroup;
    }
    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }
    public String getAvatarurl() {
        return avatarurl;
    }
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
    public Users getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    @ManyToOne
    @JoinColumn(name="created_by")
    private Users createdBy;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
