package com.example.itemfinder;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/found-items")
public class FoundItemController {

    private final FoundItemService foundItemService;

    @Autowired
    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<FoundItem>> getAllItems() {
        List<FoundItem> items = foundItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Find items by name
    @GetMapping("/search")
    public ResponseEntity<List<FoundItem>> findByName(@RequestParam String name) {
        List<FoundItem> items = foundItemService.findByName(name);
        return ResponseEntity.ok(items);
    }

    // Add a new item
    @PostMapping
    public ResponseEntity<FoundItem> addItem(@RequestBody FoundItem foundItem) {
        FoundItem newItem = foundItemService.addItem(foundItem);
        return ResponseEntity.ok(newItem);
    }

    // Get item by ID (optional)
    @GetMapping("/{id}")
    public ResponseEntity<FoundItem> getItemById(@PathVariable Integer id) {
        Optional<FoundItem> foundItem = foundItemService.getItemById(id);
        return foundItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
