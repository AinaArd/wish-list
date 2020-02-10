package ru.itis.services;

import ru.itis.forms.ItemForm;

public interface ItemService {
    void addNewItem(ItemForm itemForm, Long listId);

    void removeByName(String itemName);
}
