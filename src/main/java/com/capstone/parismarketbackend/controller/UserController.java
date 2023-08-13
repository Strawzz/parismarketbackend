package com.capstone.parismarketbackend.controller;

import com.capstone.parismarketbackend.model.User;
import com.capstone.parismarketbackend.repository.UserRepository;
import jakarta.websocket.OnError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> existingUsers = userRepository.findAll();

        if(!existingUsers.isEmpty()){
            return ResponseEntity.ok(existingUsers);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String loginId){
        Optional<User> existingUser = userRepository.findByLoginId(loginId);
        if (existingUser.isPresent()){
            userRepository.delete(existingUser.get());
            return ResponseEntity.ok("User deleted successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestParam String loginId, @RequestParam String name){
        Optional<User> existingUserOptional = userRepository.findByLoginId(loginId);

        if (existingUserOptional.isPresent()){
            User existingUser = existingUserOptional.get();
            existingUser.setLoginId(loginId);
            existingUser.setName(name);
            return ResponseEntity.ok("User updated successfully.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestParam String loginId, @RequestParam String name){
        Optional<User> existingUser = userRepository.findByLoginId(loginId);
        if (existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        else{
            User newUser = new User();
            newUser.setLoginId(loginId);
            newUser.setName(name);
            userRepository.save(newUser);
            return  ResponseEntity.ok("Registration successful!");
        }

    }

    @PostMapping ("/login")
    public ResponseEntity<String> logInUser(@RequestParam String loginId){
        Optional<User> existingUser = userRepository.findByLoginId(loginId);

        if (!existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Id");
        }
        else{
            return ResponseEntity.ok("Login successful!");
        }

    }

}
