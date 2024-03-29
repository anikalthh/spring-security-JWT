package backend.jwtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import backend.jwtbackend.dto.SignUpDto;
import backend.jwtbackend.dto.UserDto;
import backend.jwtbackend.model.User;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserDto toUserDto(User user);
    
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

} 
