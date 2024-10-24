package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UsersControllerV1 {
    private final UsersRepositoryV1 usersRepository;

    private final UsersServiceV1 usersService;

    public UsersControllerV1(UsersRepositoryV1 usersRepository, UsersServiceV1 usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        Optional<User> user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("")
    public HttpStatus create(@RequestBody User user) {
        return usersRepository.create(user) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
    }

    @PutMapping("")
    public HttpStatus update(@RequestBody User user) {
        //return usersRepository.update(user) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return HttpStatus.NOT_IMPLEMENTED;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Integer id) {
        return usersService.delete(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/name_contains/{name}")
    public ResponseEntity findByNameContaining(@PathVariable String name) {
        Optional<List<User>> users = usersService.findByNameContaining(name);
        if (users.isEmpty()) throw new ItemNotFoundException();
        return ResponseEntity.ok(users.get());
    }


}
