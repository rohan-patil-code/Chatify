package com.Chatify.DTO;

import java.util.List;

public class ChatDTO {

    private Long id;
    private String name;
    private Boolean isGroup;
    private String avatarUrl;
    private List<UserDTO> participants;
    
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

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<UserDTO> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<UserDTO> participants) {
        this.participants = participants;
    }
  
}
