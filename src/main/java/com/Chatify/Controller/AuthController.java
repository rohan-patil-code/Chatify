package com.Chatify.Controller;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Chatify.Model.RefreshToken;
import com.Chatify.Model.Users;
import com.Chatify.Security.JwtUtill;
import com.Chatify.Service.Myappservicee;
import com.Chatify.Service.RefreshTokenService;
import com.Chatify.Service.UserService;

import lombok.Data;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
   
    @Autowired
    private JwtUtill jwtUtill;

    @Autowired
    private RefreshTokenService refreshTokenService;

    // @Autowired
    // private Myappservicee myappservicee;
      
       @Autowired
       private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    // mapping for signup 
    // @PostMapping("/signup")
    // public Myappuser createUser(@RequestBody Myappuser user){
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));// it encodes password
    //    return myappservicee.createUser(user);
    // }

    @PostMapping("/signup")
    public Users createUser(@RequestBody  Users user)
    {   
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // user.setCreatedAt(LocalDateTime.now());
            return userService.createUser(user);
    }


    // mapping for login
    @PostMapping("/login")
   public JwtResponse login(@RequestBody AuthRequest authRequest) {
    try{
        Authentication authentication = authenticationManager.authenticate (

            new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())

        );

         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         
        //  String token= jwtUtill.generateToken(userDetails.getUsername());

            String accessToken=jwtUtill.generateAccessToken(userDetails.getUsername());
            String refreshToken=jwtUtill.generateRefreshToken(userDetails.getUsername());

            refreshTokenService.saveRefToken(authRequest.getEmail(), refreshToken);

            // System.out.println("Here is accesstoken:- "+accessToken);
            // System.out.println("Here is refreshtoken:- "+refreshToken);

         return new JwtResponse(accessToken,refreshToken);
    }
    catch(BadCredentialsException e)
     {
        throw new RuntimeException("Invalid Credentials");
     }

   }
   

   // mappinf for refresh token 
   @PostMapping("/refresh-token")
   public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshAccessToken) {
        
    String refreshToken = refreshAccessToken.getRefreshToken();

    if(!jwtUtill.validateRefereshToken(refreshToken)) {

       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");

    }
    
    Optional<RefreshToken> optionalToken = refreshTokenService.findByToken(refreshToken);

    if(optionalToken.isEmpty()) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Not Found In Database");
    }

    RefreshToken dbtoken=optionalToken.get();

    if(refreshTokenService.isExpired(dbtoken)) {

        refreshTokenService.deleteIfExpired(dbtoken);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is expired !");
    }

    String username=jwtUtill.getUsernameFromRefreshToken(refreshToken);
    String newAccessToken=jwtUtill.generateAccessToken(username);

    return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));

   }

   //mapping for logout
   @PostMapping("/logout")
   public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest logoutrequest)  {

            String refreshToken = logoutrequest.getRefreshToken();

            if (refreshToken==null || refreshToken.isEmpty()) {

                return ResponseEntity.badRequest().body("Refresh Token is Required !");
            }

            boolean b = refreshTokenService.deleteRefreshToken(refreshToken);

            if(b) {

                return ResponseEntity.ok("Logout Successfully");
            }
            else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh Token Not Found");
            }
   }











   @Data
   static class LogoutRequest {

    private String refreshToken;
   }



   @Data
   static class RefreshTokenRequest {

    private String refreshToken;
   }


   @Data
   static class AuthRequest {

    private String email;
    private String password;
    
   }

   @Data
   static class JwtResponse {
    private final String accessToken;
    private final String refreshToken;
   }
}
