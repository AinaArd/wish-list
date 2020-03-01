package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.itis.dto.ResponseUserDto;
import ru.itis.models.User;

@Mapper
public interface UserMapper {
    UserMapper UserMapper = Mappers.getMapper(UserMapper.class);
    ResponseUserDto userToResponseUserDto(User source);
}
