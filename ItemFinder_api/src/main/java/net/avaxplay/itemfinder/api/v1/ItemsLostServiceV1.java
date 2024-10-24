package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsLostServiceV1 {
    private final ItemsLostRepositoryV1 itemsLostRepository;

    public ItemsLostServiceV1(ItemsLostRepositoryV1 itemsLostRepository) {
        this.itemsLostRepository = itemsLostRepository;
    }

    public List<Item> findAll() {
        return itemsLostRepository.findAll();
    }

    public Optional<Item> findById(Integer id) {
        return itemsLostRepository.findById(id);
    }

    public List<Item> findByCreatorId(Integer id) {
        return itemsLostRepository.findByCreatorId(id);
    }

    public List<Item> findByCreatorUsername(String username) {
        return itemsLostRepository.findByCreatorUsername(username);
    }

    public List<Item> findByCompleted(Boolean completed) {
        return itemsLostRepository.findByCompleted(completed);
    }

    public boolean create(Item item) {
        return itemsLostRepository.create(item);
    }

    public boolean update(Item item) {
        return itemsLostRepository.update(item);
    }

    public boolean delete(Integer id) {
        return itemsLostRepository.delete(id);
    }
}
