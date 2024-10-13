package com.example.itemfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


    @Service
    public class LostItemService {
        private final LostItemRepository lostItemRepository;

       @Autowired
        public LostItemService(LostItemRepository lostItemRepository) {
            this.lostItemRepository = lostItemRepository;
        }

        public List<LostItem> getAllLostItems() {
            return lostItemRepository.findAll();
        }

        public LostItem addLostItem(LostItem lostItem) {
            return lostItemRepository.save(lostItem);
        }

        public List<LostItem> searchLostItems(String name, LocalDate date) {
            return lostItemRepository.findByNameContainingOrDate(name, date);
        }
    }


