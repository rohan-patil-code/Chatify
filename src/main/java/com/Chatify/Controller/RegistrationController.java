// package com.Chatify.Controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.Chatify.Model.Myappuser;
// import com.Chatify.Repository.MyAppRepository;

// @RestController
// public class RegistrationController {
//     @Autowired
//     private MyAppRepository repository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;
//     @PostMapping("/req/signup")
//     public Myappuser createUser(@RequestBody Myappuser user){

//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return repository.save(user);
//     }

//     // @GetMapping("/home")
//     // public String welcomeM(){
//     //     return "Hello User!";
//     // }
// }
