package ru.itis.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.ItemDto;
import ru.itis.models.Item;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {
    private static ItemDto itemDto = new ItemDto("pencil", 10);

    @Autowired
    private ItemService itemService;

    @Test
    public void dbItemEqualsToNewItem() {
        itemService.addNewItem(itemDto, 1L);
        Optional<Item> found = itemService.findItemByName(itemDto.getName());
        found.ifPresent(item -> assertEquals(itemDto.getName(), item.getName()));
        found.orElseThrow(IllegalArgumentException::new);
    }
}
