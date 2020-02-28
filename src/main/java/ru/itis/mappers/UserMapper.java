package ru.itis.mappers;

import org.mapstruct.Mapper;
import ru.itis.dto.ResponseUserDto;
import ru.itis.models.User;

@Mapper
public interface UserMapper {

    ResponseUserDto userToResponseUserDto(User source);
}
