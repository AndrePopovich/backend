package com.bs.auth;

import com.bs.model.User;
import com.bs.payload.request.AuthenticationRequest;
import com.bs.payload.request.RegisterRequest;
import com.bs.payload.response.AuthenticationResponse;
import com.bs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://frontend-9ald.onrender.com")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/is-exist/{email}")
    public ResponseEntity<Boolean> isExistUsername(@PathVariable String email){
        return ResponseEntity.ok(userService.isExistEmail(email));
    }
}
