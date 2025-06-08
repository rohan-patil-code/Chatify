package com.Chatify.Controller;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Chatify.Model.Users;
import com.Chatify.Service.UserService;
@RestController
@RequestMapping("/api/users")
public class ContentController {

    @Autowired
    private UserService service;

    //api to get personal information
    @GetMapping("/me")
    public ResponseEntity<?> getInfo()
    {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.getInfo(username));
    }

        // api to search the user with excluding self one
    @GetMapping("/search")
    public List<Users> SearchUser(@RequestParam String username,Principal principal) {

        return service.SearchUsers(username,principal.getName());
    }


    
}
