package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/items/lost")
public class ItemsLostControllerV1 {
    private final ItemsLostRepositoryV1 itemsLostRepository;

    public ItemsLostControllerV1(ItemsLostRepositoryV1 itemsLostRepository) {
        this.itemsLostRepository = itemsLostRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemsLostRepository.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Item> findById(@PathVariable Integer id) {
        Optional<Item> item = itemsLostRepository.findById(id);
        if (item.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(item.get());
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<Item>> findByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(itemsLostRepository.findByCreatorId(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Item>> findByCreatorUsername(@PathVariable String username) {
        return ResponseEntity.ok(itemsLostRepository.findByCreatorUsername(username));
    }

    @GetMapping("/completed/{completed}")
    public ResponseEntity<List<Item>> findByCompleted(@PathVariable Boolean completed) {
        return ResponseEntity.ok(itemsLostRepository.findByCompleted(completed));
    }

    @PostMapping("")
    public HttpStatus create(@RequestBody Item item) {
        return itemsLostRepository.create(item) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
    }

    @PutMapping("")
    public HttpStatus update(@RequestBody Item item) {
        return itemsLostRepository.update(item) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
