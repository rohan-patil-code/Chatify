package com.Chatify.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Chatify.Model.UserPrincipal;
import com.Chatify.Model.Users;
import com.Chatify.Repository.UserRepository;

@Service
public class Myappservicee implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> user = userRepository.findUserByEmail(email);

        if (user.isPresent()) {
            var userObj = user.get();
            return new UserPrincipal(
                    userObj.getUserId(),
                    userObj.getEmail(),
                    userObj.getPassword()

            );
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

}
