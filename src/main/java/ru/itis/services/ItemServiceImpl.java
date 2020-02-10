package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.forms.ItemForm;
import ru.itis.models.Item;
import ru.itis.models.WishList;
import ru.itis.repositories.ItemsRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    public ItemsRepository itemsRepository;

    @Autowired
    public WishListService wishListService;

    @Autowired
    public UserService userService;

    @Override
    public Item addNewItem(ItemForm itemForm, Long listId) {
        WishList wishList = getWishList(listId);
        Item newItem = Item.builder()
                .name(itemForm.getName())
                .price(itemForm.getPrice())
                .link(itemForm.getLink())
                .wishList(wishList)
                .build();
        wishList.getItems().add(newItem);
        return itemsRepository.save(newItem);
    }

    @Override
    public void removeByName(String itemName, Long listId) {
        WishList wishList = getWishList(listId);
        Optional<Item> itemCandidate = itemsRepository.findByName(itemName);
        if (itemCandidate.isPresent()) {
            itemsRepository.deleteById(itemCandidate.get().getId());
            wishList.getItems().remove(itemCandidate.get());
        } else {
            throw new IllegalArgumentException("Can not find such item");
        }
    }

    private WishList getWishList(Long listId) {
        Optional<WishList> wishListCandidate = wishListService.findWishListById(listId);
        if (wishListCandidate.isPresent()) {
            return wishListCandidate.get();
        } else {
            throw new NoSuchElementException("Can not find such wish list");
        }
    }
}
