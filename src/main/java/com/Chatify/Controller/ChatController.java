package com.Chatify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Chatify.DTO.ChatDTO;
import com.Chatify.DTO.CreateChatRequest;
import com.Chatify.Model.UserPrincipal;
import com.Chatify.Security.JwtUtill;
import com.Chatify.Service.ChatService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getUserChats(@AuthenticationPrincipal UserPrincipal user)
    {  
        Long userid=user.getId();
         System.out.println("-----------------------------TOp CHA VISHY"+userid);
        List<ChatDTO> chats = chatService.getUserChats(userid);
        return ResponseEntity.ok(chats);

    }

    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@RequestBody CreateChatRequest request)
    {
            ChatDTO chat = chatService.createIndividualChat(request.getSenderId(),request.getReceiverId());
            return ResponseEntity.ok(chat);
    }
}
