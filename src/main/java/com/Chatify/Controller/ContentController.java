package com.Chatify.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Chatify.Repository.MyAppRepository;
import com.Chatify.Repository.UserRepository;
@RestController
@RequestMapping("/api")
public class ContentController {

    @Autowired
    private MyAppRepository repo;

    @Autowired
    private UserRepository userRepository;

 @GetMapping("/first")
    public String First(){
        return "Hello Ronniee Welcome to Page!";
    }

    @GetMapping("/second")
    public String Second(){
        return  "Hello Welcome Guest";
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo()
    {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userRepository.findUserByEmail(username));
    }
}
