package backend.jwtbackend.service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.jwtbackend.dto.CredentialsDto;
import backend.jwtbackend.dto.SignUpDto;
import backend.jwtbackend.dto.UserDto;
import backend.jwtbackend.exception.AppException;
import backend.jwtbackend.mapper.UserMapper;
import backend.jwtbackend.model.User;
import backend.jwtbackend.repo.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
    
    @Autowired
    public UsersRepository userRepo;
    
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        System.out.printf("LOGIN -- CREDENTIALS DTO: %s\n\n", credentialsDto);
        User user = userRepo.findByUsername(credentialsDto.username())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepo.findByUsername(userDto.getUsername());
        System.out.printf("LOGIN -- USER SIGN UP DTO: %s\n\n", userDto);

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("\n\n----- USERDTO INPUT USERNAME -----\n %s\n\n", userDto);

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepo.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}
