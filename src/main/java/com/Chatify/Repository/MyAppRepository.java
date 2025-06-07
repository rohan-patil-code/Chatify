package com.Chatify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Chatify.Model.Myappuser;

@Repository
public interface MyAppRepository extends JpaRepository<Myappuser,Integer>{

    Optional<Myappuser> findByUsername(String username);
}
