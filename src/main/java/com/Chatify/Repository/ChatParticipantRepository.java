package com.Chatify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Chatify.Model.ChatParticipant;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant,Long> {

   List<ChatParticipant> findByUser_UserId(Long userId);
   List<ChatParticipant> findByChat_Id(Long chatId);


}
