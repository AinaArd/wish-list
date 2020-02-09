package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.forms.ItemForm;
import ru.itis.models.Item;
import ru.itis.models.WishList;
import ru.itis.repositories.ItemsRepository;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserService userService;

    @Override
    public Item addNewItem(ItemForm itemForm) {
        if (wishListService.findWishListById(itemForm.getWishListId()).isPresent()) {
            WishList wishList = wishListService.findWishListById(itemForm.getWishListId()).get();
            Item newItem = Item.builder()
                    .name(itemForm.getName())
                    .price(itemForm.getPrice())
                    .link(itemForm.getLink())
                    .wishList(wishList)
                    .build();
            wishList.getItems().add(newItem);
            return itemsRepository.save(newItem);
        } else throw new NullPointerException("No wishlists found");
    }

    @Override
    public void remove(String itemName) {
        Optional<Item> itemCandidate = itemsRepository.findByName(itemName);
        if (itemCandidate.isPresent()) {
            itemsRepository.deleteById(itemCandidate.get().getId());
        } else throw new IllegalArgumentException("Can not find such item");
    }
}
