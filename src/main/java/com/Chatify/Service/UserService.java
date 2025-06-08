package com.Chatify.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Chatify.Model.Users;
import com.Chatify.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // service logic to getOwn Info or user Info
    public Optional<Users> getInfo(String username)
    {
        return userRepository.findUserByEmail(username);
    }

    // service logic for creating user or signing user
    public Users createUser(Users user)
    {
        return userRepository.save(user);
    }


    // service logic for search user 
    public List<Users> SearchUsers(String name,String currentEmail)
    {
        Users currentUser=userRepository.findUserByEmail(currentEmail)
        .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

        return userRepository.findByUsernameContainingIgnoreCaseAndUserIdNot(name,currentUser.getUserId());
    }
}
