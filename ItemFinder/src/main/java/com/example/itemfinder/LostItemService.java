package com.example.itemfinder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemService {

    private final LostItemRepository lostItemRepository;

    @Autowired
    public LostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    // Get all lost items
    public List<LostItem> getAllItems() {
        return lostItemRepository.findAll();
    }

    // Find items by name
    public List<LostItem> findByName(String name) {
        return lostItemRepository.findByName(name);
    }

    // Add a new lost item
    public LostItem addItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }

    // Get a lost item by ID
    public Optional<LostItem> getItemById(Integer id) {
        return lostItemRepository.findById(id);
    }
}
