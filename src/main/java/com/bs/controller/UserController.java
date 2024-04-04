package com.bs.controller;

import com.bs.model.User;
import com.bs.payload.response.ProfileResponse;
import com.bs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }/*
    @Deprecated
    @GetMapping("user")
    public ResponseEntity<User> getUserByEmail(@RequestBody String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
*/
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByEmail(username);
        ProfileResponse profile = new ProfileResponse(
                user.get().getId(),
                user.get().getFirstname(),
                user.get().getLastname(),
                username,
                user.get().getRole(),
                user.get().getPhone(),
                user.get().getDateOfRegistration(),
                user.get().getAds());
        return ResponseEntity.ok(profile);

    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User existingUser = userService.getUserById(id);
        if(existingUser != null){
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        User existingUser = userService.getUserById(id);
        if(existingUser != null){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
