package com.example.itemfinder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    // Get all lost items
    @GetMapping
    public ResponseEntity<List<LostItem>> getAllItems() {
        List<LostItem> items = lostItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Find items by name
    @GetMapping("/search")
    public ResponseEntity<List<LostItem>> findByName(@RequestParam String name) {
        List<LostItem> items = lostItemService.findByName(name);
        return ResponseEntity.ok(items);
    }

    // Add a new lost item
    @PostMapping
    public ResponseEntity<LostItem> addItem(@RequestBody LostItem lostItem) {
        LostItem newItem = lostItemService.addItem(lostItem);
        return ResponseEntity.ok(newItem);
    }

    // Get item by ID (optional)
    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getItemById(@PathVariable Integer id) {
        Optional<LostItem> lostItem = lostItemService.getItemById(id);
        return lostItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byFinder/{userId}")
    public ResponseEntity<List<LostItem>> findByFinderId(@PathVariable Integer userId) {
        List<LostItem> items = lostItemService.findByFinderId(userId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/byLoser/{userId}")
    public ResponseEntity<List<LostItem>> findByLoserId(@PathVariable Integer userId) {
        List<LostItem> items = lostItemService.findByLoserId(userId);
        return ResponseEntity.ok(items);
    }
}
