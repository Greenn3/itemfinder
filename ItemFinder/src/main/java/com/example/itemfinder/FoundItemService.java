package com.example.itemfinder;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundItemService {

    private final FoundItemRepository foundItemRepository;

    @Autowired
    public FoundItemService(FoundItemRepository foundItemRepository) {
        this.foundItemRepository = foundItemRepository;
    }

    // Get all found items
    public List<FoundItem> getAllItems() {
        return foundItemRepository.findAll();
    }

    // Find items by name
    public List<FoundItem> findByName(String name) {
        return foundItemRepository.findByName(name);
    }

    // Add a new item
    public FoundItem addItem(FoundItem foundItem) {
        return foundItemRepository.save(foundItem);
    }

    // Get item by ID (optional, just in case you need it)
    public Optional<FoundItem> getItemById(Integer id) {
        return foundItemRepository.findById(id);
    }
}
