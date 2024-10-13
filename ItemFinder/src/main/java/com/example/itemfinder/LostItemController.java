package com.example.itemfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lost-items")
@CrossOrigin(origins = "*")
public class LostItemController {

    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping
    public List<LostItem> getLostItems() {
        return lostItemService.getAllLostItems();
    }

    @PostMapping
    public LostItem addLostItem(@RequestBody LostItem lostItem) {
        return lostItemService.addLostItem(lostItem);
    }

    @GetMapping("/search")
    public List<LostItem> searchLostItems(@RequestParam String name, @RequestParam(required = false) LocalDate date) {
        return lostItemService.searchLostItems(name, date);
    }
}
