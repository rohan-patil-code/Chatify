package com.Chatify.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.Chatify.Model.Chat;
import com.Chatify.Model.Users;

public class ChatMapper {

    public static ChatDTO toDTO(Chat chat,List<UserDTO> participants) {
        
        ChatDTO dto= new ChatDTO();

        dto.setId(chat.getId());
        dto.setName(chat.getName());
        dto.setIsGroup(chat.isGroup());
        dto.setAvatarUrl(chat.getAvatarurl());
        dto.setParticipants(participants);

        return dto;
    }

    
    public static UserDTO toDTO(Users user)
    {
        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setAvatarurl(user.getAvatarUrl());

        return dto;
    }

   

}
