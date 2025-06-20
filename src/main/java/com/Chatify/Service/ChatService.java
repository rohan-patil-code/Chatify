package com.Chatify.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Chatify.DTO.ChatDTO;
import com.Chatify.DTO.ChatMapper;
import com.Chatify.DTO.MessageStatusUpdateRequest;
import com.Chatify.DTO.UserDTO;
import com.Chatify.Model.Chat;
import com.Chatify.Model.ChatParticipant;
import com.Chatify.Model.MessageStatus;
import com.Chatify.Model.MessageStatusType;
import com.Chatify.Model.ROLE;
import com.Chatify.Model.Users;
import com.Chatify.Repository.ChatParticipantRepository;
import com.Chatify.Repository.ChatRepository;
import com.Chatify.Repository.MessageRepository;
import com.Chatify.Repository.MessageStatusRepository;
import com.Chatify.Repository.UserRepository;

@Service
public class ChatService {
    @Autowired
    private ChatParticipantRepository chatParticipantRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

   
    
   

    public List<ChatDTO> getUserChats(Long userid) {
        System.out.println("-----------debuging service layer of chat" + userid);
        List<ChatParticipant> participantList = chatParticipantRepository.findByUser_UserId(userid);

        Map<Long, List<UserDTO>> chatUserMap = new HashMap<>();
        Map<Long, Chat> chatMap = new HashMap<>();

        for (ChatParticipant cp : participantList) {
            Long chatId = cp.getChat().getId();
            System.out.println("ChatID -> " + chatId);

            chatMap.putIfAbsent(chatId, cp.getChat());
            chatUserMap.putIfAbsent(chatId, new ArrayList<>());

            UserDTO userDTO = ChatMapper.toDTO(cp.getUser());

            chatUserMap.get(chatId).add(userDTO);

        }

        return chatMap.values().stream()
                .map(chat -> ChatMapper.toDTO(chat, chatUserMap.get(chat.getId())))
                .collect(Collectors.toList());
    }

    public ChatDTO createIndividualChat(Long senderId, Long receiverId) {
        List<ChatParticipant> senderChats = chatParticipantRepository.findByUser_UserId(senderId);
        for (ChatParticipant cp : senderChats) {
            Chat chat = cp.getChat();

            if (!chat.isGroup()) {
                List<ChatParticipant> participants = chatParticipantRepository.findByChat_Id(chat.getId());

                boolean receiver = participants.stream()
                        .anyMatch(p -> p.getUser().getUserId().equals(receiverId));

                if (receiver && participants.size() == 2) {
                    return ChatMapper.toDTO(chat, participants.stream()
                            .map(p -> ChatMapper.toDTO(p.getUser()))
                            .collect(Collectors.toList()));
                }
            }
        }

        // for creating new chat
        Chat chat = new Chat();
        chat.setGroup(false);
        chat.setName("Chat with" + receiverId);
        chatRepository.save(chat);

        // Adding both users as chat participants
        Users sender = userRepository.findById(senderId).orElseThrow();
        Users receiver = userRepository.findById(receiverId).orElseThrow();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        ChatParticipant cp1 = new ChatParticipant();
        cp1.setChat(chat);
        cp1.setUser(sender);
        cp1.setRole(ROLE.MEMBER);
        cp1.setJoinedAt(now);

        ChatParticipant cp2 = new ChatParticipant();
        cp2.setChat(chat);
        cp2.setUser(receiver);
        cp2.setRole(ROLE.MEMBER);
        cp2.setJoinedAt(now);

        chatParticipantRepository.save(cp1);
        chatParticipantRepository.save(cp2);

        List<UserDTO> users = List.of(ChatMapper.toDTO(sender), ChatMapper.toDTO(receiver));

        return ChatMapper.toDTO(chat, users);
    }

    public ChatDTO createGroupChat(String name, List<Long> participantIDs) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setGroup(true);
        chatRepository.save(chat);

        List<UserDTO> participants = new ArrayList<>();

        for (Long userId : participantIDs) {
            Users user = userRepository.findById(userId).orElseThrow();
            ChatParticipant cp = new ChatParticipant();
            cp.setChat(chat);
            cp.setUser(user);
            cp.setRole(ROLE.MEMBER);
            chatParticipantRepository.save(cp);

            participants.add(ChatMapper.toDTO(user));
        }
        return ChatMapper.toDTO(chat, participants);
    }

    public ChatDTO getChatDetails(Long chatId)
    {
       Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() ->  new RuntimeException("Chat not found"));

                List<ChatParticipant> participants = chatParticipantRepository.findByChat_Id(chatId);

                List<UserDTO> participantsDTOs = participants.stream()
                                    .map(cp -> ChatMapper.toDTO(cp.getUser()))
                                    .collect(Collectors.toList());

            return ChatMapper.toDTO(chat, participantsDTOs);
    }

   


}
