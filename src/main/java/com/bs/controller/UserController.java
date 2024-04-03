package com.bs.controller;

import com.bs.model.User;
import com.bs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
