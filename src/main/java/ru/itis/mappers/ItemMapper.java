package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.itis.dto.ItemDto;
import ru.itis.models.Item;

@Mapper
public interface ItemMapper {
    ItemMapper ItemMapper = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "id", ignore = true)
    Item itemDtoToItem(ItemDto source);
}
