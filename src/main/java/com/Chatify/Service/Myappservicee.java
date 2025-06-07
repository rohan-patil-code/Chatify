package com.Chatify.Service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.Chatify.Model.Myappuser;
import com.Chatify.Model.Users;
import com.Chatify.Repository.MyAppRepository;
import com.Chatify.Repository.RefreshTokenRepo;
import com.Chatify.Repository.UserRepository;

@Service
public class Myappservicee implements UserDetailsService {

    @Autowired
    private MyAppRepository repository;

    @Autowired
    private UserRepository userRepository;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //     Optional<Myappuser> user = repository.findByUsername(username);

    //     if(user.isPresent()){
    //         var userObj = user.get();
    //         return User.builder()
    //                     .username(userObj.getUsername())
    //                     .password(userObj.getPassword())
    //                     .build();
    //     }
    //     else{
    //         throw new UsernameNotFoundException(username);
    //     }
         
    // }

    // public Myappuser createUser(Myappuser user)
    // {
    //         return repository.save(user);
    // }


    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> user = userRepository.findUserByEmail(email);

        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                        .username(userObj.getEmail())
                        .password(userObj.getPassword())
                        .build();
        }
        else{
            throw new UsernameNotFoundException(email);
        }
         
    }

    public Users createUser(Users user)
    {
            return userRepository.save(user);
    }



}
