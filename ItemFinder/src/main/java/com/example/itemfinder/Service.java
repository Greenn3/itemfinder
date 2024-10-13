package com.example.itemfinder;




import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final ItemRepository itemRepository;

    public Service(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }
}
