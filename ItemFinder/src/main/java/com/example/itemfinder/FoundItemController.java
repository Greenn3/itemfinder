package com.example.itemfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/found-items")
@CrossOrigin(origins = "*")
public class FoundItemController {
private final FoundItemService foundItemService;


@Autowired
public FoundItemController(FoundItemService foundItemService) {

        this.foundItemService = foundItemService;
    }

    @GetMapping
    public List<FoundItem> getFoundItems() {
        return foundItemService.getAllFoundItems();
    }

    @PostMapping
    public FoundItem addFoundItem(@RequestBody FoundItem foundItem) {
        return foundItemService.addFoundItem(foundItem);
    }

    @GetMapping("/search")
    public List<FoundItem> searchFoundItems(@RequestParam String name, @RequestParam(required = false) LocalDate date) {
        return foundItemService.searchFoundItems(name, date);
    }
}
