package ru.itis.services;

import ru.itis.forms.ItemForm;
import ru.itis.models.Item;

public interface ItemService {
    Item addNewItem(ItemForm itemForm, Long listId);

    void removeByName(String itemName, Long listId);
}
