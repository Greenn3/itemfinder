package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.User;
import net.avaxplay.itemfinder.schema.UserNew;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceV1 {
    private final UsersRepositoryV1 usersRepository;
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(); // default strength is 10

    public UsersServiceV1(UsersRepositoryV1 usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return usersRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public boolean create(UserNew userNew) {
        User user = new User(null, userNew.Username(), bCrypt.encode(userNew.Password()));
        return usersRepository.createAutoId(user);
    }

    public boolean update(User user) {
        //return usersRepository.update(user) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return false;
    }
    public boolean delete(Integer id) {
        return usersRepository.delete(id);
    }

    public List<User> findByNameContaining(String name) {
        return usersRepository.findByNameContaining(name);
    }
}
