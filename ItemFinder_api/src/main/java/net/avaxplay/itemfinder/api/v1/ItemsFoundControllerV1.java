package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/items/found")
public class ItemsFoundControllerV1 {
    private final ItemsFoundServiceV1 itemsFoundService;

    public ItemsFoundControllerV1(ItemsFoundServiceV1 itemsFoundService) {
        this.itemsFoundService = itemsFoundService;
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemsFoundService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Item> findById(@PathVariable Integer id) {
        Optional<Item> item = itemsFoundService.findById(id);
        if (item.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(item.get());
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<Item>> findByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(itemsFoundService.findByCreatorId(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Item>> findByCreatorUsername(@PathVariable String username) {
        return ResponseEntity.ok(itemsFoundService.findByCreatorUsername(username));
    }

    @GetMapping("/completed/{completed}")
    public ResponseEntity<List<Item>> findByCompleted(@PathVariable Boolean completed) {
        return ResponseEntity.ok(itemsFoundService.findByCompleted(completed));
    }

    @PostMapping("")
    public HttpStatus create(@RequestBody Item item) {
        return itemsFoundService.create(item) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
    }

    @PutMapping("")
    public HttpStatus update(@RequestBody Item item) {
        return itemsFoundService.update(item) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Integer id) {
        return itemsFoundService.delete(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/name_contains/{name}")
    public ResponseEntity findByNameContaining(@PathVariable String name) {
        List<Item> items = itemsFoundService.findByNameContaining(name);
        if (items.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/creation_date/{creationDate}")
    public ResponseEntity findByCreationDate(@PathVariable LocalDateTime creationDate) {
        List<Item> items = itemsFoundService.findByCreationDate(creationDate);
        if (items.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(items);
    }
}
