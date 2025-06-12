package com.Chatify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Chatify.Model.Messages;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Long> {

    List<Messages> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
