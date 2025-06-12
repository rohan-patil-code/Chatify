package com.Chatify.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Chatify.DTO.MessageRequest;
import com.Chatify.DTO.MessageResponse;
import com.Chatify.DTO.MessageStatusUpdateRequest;
import com.Chatify.DTO.UserDTO;
import com.Chatify.DTO.UserJoinRequest;
import com.Chatify.Service.MessagesService;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


  @MessageMapping("/sendMessage")
public void sendMessageViaWebSocket(MessageRequest req) {
    // Reuse the same logic
    MessageResponse savedMessage = messagesService.saveMessage(req);

    // Broadcast message to chat subscribers
    messagingTemplate.convertAndSend("/topic/chat/" + savedMessage.getChatId(), savedMessage);
}


    
    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageResponse>> getChatMessages(@PathVariable Long chatId)
    {
        return ResponseEntity.ok(messagesService.getMessages(chatId));
    }
@MessageMapping("/chat.addUser")
    public void addUserToChat(@Payload UserDTO user, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", user.getUsername());
        // Optionally notify others
    }
  
    @MessageMapping("/message.status.update")
    public void updateStatus(@Payload MessageStatusUpdateRequest dto) {
        messagesService.updateMessageStatus(dto);
        messagingTemplate.convertAndSend("/topic/status/" + dto.getMessageId(), dto);
    }
}
