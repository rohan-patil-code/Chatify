package com.Chatify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Chatify.Model.Chat;

public interface ChatRepository extends JpaRepository<Chat,Long> {

}
