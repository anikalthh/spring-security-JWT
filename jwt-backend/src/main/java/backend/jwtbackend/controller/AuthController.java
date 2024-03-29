package backend.jwtbackend.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import backend.jwtbackend.config.UserAuthenticationProvider;
import backend.jwtbackend.dto.CredentialsDto;
import backend.jwtbackend.dto.SignUpDto;
import backend.jwtbackend.dto.UserDto;
import backend.jwtbackend.service.UsersService;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UsersService userSvc;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider ;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userSvc.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        System.out.printf("in controller: %s\n\n", user);
        UserDto createdUser = userSvc.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUserid())).body(createdUser);
    }
}
