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
    private final ItemsLostServiceV1 lostService;

    public ItemsLostControllerV1(ItemsLostServiceV1 lostService) {
        this.lostService = lostService;
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(lostService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Item> findById(@PathVariable Integer id) {
        Optional<Item> item = lostService.findById(id);
        if (item.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(item.get());
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<Item>> findByCreatorId(@PathVariable Integer id) {
        return ResponseEntity.ok(lostService.findByCreatorId(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Item>> findByCreatorUsername(@PathVariable String username) {
        return ResponseEntity.ok(lostService.findByCreatorUsername(username));
    }

    @GetMapping("/completed/{completed}")
    public ResponseEntity<List<Item>> findByCompleted(@PathVariable Boolean completed) {
        return ResponseEntity.ok(lostService.findByCompleted(completed));
    }

    @PostMapping("")
    public HttpStatus create(@RequestBody Item item) {
        return lostService.create(item) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
    }

    @PutMapping("")
    public HttpStatus update(@RequestBody Item item) {
        return lostService.update(item) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Integer id) {
        return lostService.delete(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

}
