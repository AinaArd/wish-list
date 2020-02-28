package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.itis.dto.WishListDto;
import ru.itis.models.WishList;

@Mapper
public interface WishListMapper {
    WishListMapper WISH_LIST_MAPPER = Mappers.getMapper(WishListMapper.class);

    @Mappings({
            @Mapping(target = "author", source = "author.login"),
    })
    WishListDto wishListToWishListDto(WishList source);
}
