package com.Chatify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Chatify.Model.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken>  findByToken(String token);
    void deleteByToken(String token);
}
