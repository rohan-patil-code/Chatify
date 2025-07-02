package com.Chatify.Service;

import com.Chatify.DTO.SearchUserDTO;
import com.Chatify.DTO.UpdateProfile;
import com.Chatify.DTO.UserDTO;
import com.Chatify.DTO.UserSummaryDTO;
import com.Chatify.Model.Status;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Users> getInfo(String username) {
        return userRepository.findUserByEmail(username);
    }

    // service logic for creating user or signing user
    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    // service logic for search user
//     public List<UserSummaryDTO> SearchUsers(String name, String currentEmail) {
//     Users currentUser = userRepository.findUserByEmail(currentEmail)
//         .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
//
//     return userRepository.searchUserSummaries(name, currentUser.getUserId());
// }


    // service logic for update user status ex: ONLINE,OFLINE,AWAY
    public void updateUserStatus(String email, Status status) {
        Users user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        user.setStatus(status);
        userRepository.save(user);
    }

    public void updateProfile(String email, UpdateProfile req) {
        Users user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (req.getUsername() != null && !req.getUsername().isBlank()) {
            user.setUsername(req.getUsername());
            user.setAvatarUrl(req.getAvatarUrl());
        }

        userRepository.save(user);
    }
    
    public List<UserDTO> searchUsers(String username) {
        List<Users> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setAvatarurl(user.getAvatarUrl()); // ensure this field exists in `User` model
            return dto;
        }).collect(Collectors.toList());
    }

}
