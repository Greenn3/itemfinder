package com.example.itemfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;

   @Autowired
    public FoundItemService(FoundItemRepository foundItemRepository) {

       this.foundItemRepository = foundItemRepository;
   }

    public List<FoundItem> getAllFoundItems() {
        return foundItemRepository.findAll();
    }

    public FoundItem addFoundItem(FoundItem foundItem) {
        return foundItemRepository.save(foundItem);
    }

    public List<FoundItem> searchFoundItems(String name, LocalDate date) {
        return foundItemRepository.findByNameContainingOrDate(name, date);
    }
}
