package com.Chatify.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Chatify.DTO.MessageRequest;
import com.Chatify.DTO.MessageResponse;
import com.Chatify.DTO.MessageStatusUpdateRequest;
import com.Chatify.Model.Chat;
import com.Chatify.Model.MessageStatus;
import com.Chatify.Model.MessageStatusType;
import com.Chatify.Model.Messages;
import com.Chatify.Model.Users;
import com.Chatify.Repository.ChatRepository;
import com.Chatify.Repository.MessageRepository;
import com.Chatify.Repository.MessageStatusRepository;
import com.Chatify.Repository.UserRepository;

@Service
public class MessagesService {

    @Autowired
    private MessageRepository messageRepository;
      @Autowired
    private ChatRepository chatRepository;
      @Autowired
    private UserRepository userRepository;

     @Autowired
    private MessageStatusRepository messageStatusRepository;




    public MessageResponse saveMessage(MessageRequest request) {
        Chat chat = chatRepository.findById(request.getChatId()).orElseThrow();
        Users sender = userRepository.findById(request.getSenderId()).orElseThrow();

        Messages message = new Messages();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(request.getContent());

        message = messageRepository.save(message);

        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setChatId(chat.getId());
        response.setSenderId(sender.getUserId());
        response.setContent(message.getContent());
        response.setCreatedAt(message.getCreatedAt());

        return response;
    }

    public List<MessageResponse> getMessages(Long chatId) {
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId)
            .stream().map(msg -> {
                MessageResponse res = new MessageResponse();
                res.setId(msg.getId());
                res.setChatId(chatId);
                res.setSenderId(msg.getSender().getUserId());
                res.setContent(msg.getContent());
                res.setCreatedAt(msg.getCreatedAt());
                return res;
            }).collect(Collectors.toList());
    }

     public void updateMessageStatus(MessageStatusUpdateRequest dto) {
    MessageStatus status = new MessageStatus();
    status.setMessage(messageRepository.findById(dto.getMessageId()).orElseThrow());
    status.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
    status.setStatus(MessageStatusType.valueOf(dto.getStatus()));
    status.setTimestamp(LocalDateTime.now());
    messageStatusRepository.save(status);
}

}
