package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsFoundServiceV1 {
    private final ItemsFoundRepositoryV1 itemsFoundRepository;

    public ItemsFoundServiceV1(ItemsFoundRepositoryV1 itemsFoundRepository) {
        this.itemsFoundRepository = itemsFoundRepository;
    }

    public List<Item> findAll() {
        return itemsFoundRepository.findAll();
    }

    public Optional<Item> findById(Integer id) {
        return itemsFoundRepository.findById(id);
    }

    public List<Item> findByCreatorId(Integer id) {
        return itemsFoundRepository.findByCreatorId(id);
    }

    public List<Item> findByCreatorUsername(String username) {
        return itemsFoundRepository.findByCreatorUsername(username);
    }

    public List<Item> findByCompleted(Boolean completed) {
        return itemsFoundRepository.findByCompleted(completed);
    }

    public boolean create(Item item) {
        return itemsFoundRepository.create(item);
    }

    public boolean update(Item item) {
        return itemsFoundRepository.update(item);
    }

    public boolean delete(Integer id) {
        return itemsFoundRepository.delete(id);
    }
}
