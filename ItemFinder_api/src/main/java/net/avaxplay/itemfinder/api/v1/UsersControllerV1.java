package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UsersControllerV1 {
    private final UsersRepositoryV1 usersRepository;

    public UsersControllerV1(UsersRepositoryV1 usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("")
    public List<User> findAll() {
        return usersRepository.findAll();
    }
}
