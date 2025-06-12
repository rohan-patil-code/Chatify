package com.Chatify.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Chatify.Model.MessageStatus;

public interface MessageStatusRepository extends JpaRepository<MessageStatus , Long> {

    List<MessageStatus> findByMessageId(Long messageId);
}
