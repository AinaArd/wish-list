package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.ItemDto;
import ru.itis.models.Item;
import ru.itis.models.WishList;
import ru.itis.repositories.ItemsRepository;

import java.util.Optional;

@Service
public class ItemService {

    private ItemsRepository itemsRepository;
    private WishListService wishListService;

    @Autowired
    public ItemService(ItemsRepository itemsRepository, WishListService wishListService) {
        this.itemsRepository = itemsRepository;
        this.wishListService = wishListService;
    }

    public void addNewItem(ItemDto itemDto, Long listId) {
        WishList wishList = getWishList(listId);
        Item newItem = itemDtoToItem(itemDto, wishList);
        itemsRepository.save(newItem);
    }

    public void removeByName(String itemName) {
        Optional<Item> itemCandidate = itemsRepository.findByName(itemName);
        if (itemCandidate.isPresent()) {
            itemsRepository.deleteById(itemCandidate.get().getId());
        } else {
            throw new IllegalArgumentException("Can not find such item");
        }
    }

    private WishList getWishList(Long listId) {
        return wishListService
                .findWishListById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Can not find such wish list"));
    }

    private Item itemDtoToItem(ItemDto itemDto, WishList wishList) {
        return new Item(itemDto.getName(),
                itemDto.getPrice(),
                itemDto.getLink(),
                itemDto.getDescription(),
                wishList);
    }

    public Optional<Item> findItemByName(String name) {
        return itemsRepository.findByName(name);
    }

}
