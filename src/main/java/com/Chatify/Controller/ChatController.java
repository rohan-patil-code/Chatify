package com.Chatify.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Chatify.DTO.ChatDTO;
import com.Chatify.DTO.CreateChatRequest;
import com.Chatify.DTO.CreateGroupChats;
import com.Chatify.Model.UserPrincipal;
import com.Chatify.Service.ChatService;
@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getUserChats(@AuthenticationPrincipal UserPrincipal user) {
        Long userid = user.getId();
        System.out.println("-----------------------------Debugging Controller Of userchats" + userid);
        List<ChatDTO> chats = chatService.getUserChats(userid);
        return ResponseEntity.ok(chats);
    }

    @PostMapping  // ✅ FIXED: No need to repeat "/api/chats" here
    public ResponseEntity<ChatDTO> createChat(@RequestBody CreateChatRequest request, Principal principal) {
        String senderUsername = principal.getName();
        ChatDTO chat = chatService.createIndividualChat(senderUsername, request.getReceiverId());
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/group")
    public ResponseEntity<ChatDTO> createGroupChat(@RequestBody CreateGroupChats request) {
        ChatDTO chat = chatService.createGroupChat(request.getName(), request.getParticipantIDs());
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/chatDetais/{chatId}")
    public ResponseEntity<ChatDTO> getChatDetails(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getChatDetails(chatId));
    }
}
