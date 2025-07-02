package com.Chatify.Controller;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Chatify.DTO.SearchUserDTO;
import com.Chatify.DTO.UpdateProfile;
import com.Chatify.DTO.UpdateStatusRequestt;
import com.Chatify.DTO.UserDTO;
import com.Chatify.DTO.UserSummaryDTO;
import com.Chatify.Model.Users;
import com.Chatify.Service.UserService;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
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


//         api to search the user with excluding self one
    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam("username") String username) {
        return service.searchUsers(username);
    }



    @PatchMapping("/status")
    public ResponseEntity<String> updateStatus (@RequestBody UpdateStatusRequestt req,Principal principal)
    {
        String email=principal.getName();

        service.updateUserStatus(email,req.getStatus());

        return ResponseEntity.ok("Status Updated to"+req.getStatus());
    }

    
    @PatchMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfile updateProfile,Principal principal)
    {
        String email=principal.getName();

        service.updateProfile(email,updateProfile);

        return ResponseEntity.ok("Profile Update Successfully");
    }



    
}
