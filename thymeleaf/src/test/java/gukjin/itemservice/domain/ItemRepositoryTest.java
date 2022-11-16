package gukjin.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    public void clear(){
        itemRepository.clearStore();
    }

    @Test
    public void save(){
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem.getId()).isEqualTo(findItem.getId());
    }

    @Test
    public void findAll(){
        Item item = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 30);

        itemRepository.save(item);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item, item2);

    }

    @Test
    public void update(){
        Item item = new Item("itemA", 10000, 10);
        Item updateParam = new Item("itemB", 20000, 30);

        itemRepository.save(item);

        itemRepository.update(item.getId(), updateParam);

        assertThat(item.getQuantity()).isEqualTo(30);
        assertThat(item.getPrice()).isEqualTo(20000);

    }

}