package net.avaxplay.itemfinder.auth;

import net.avaxplay.itemfinder.api.v1.UsersRepositoryV1;
import net.avaxplay.itemfinder.model.UserPrincipal;
import net.avaxplay.itemfinder.schema.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataBaseUserDetailService implements UserDetailsService {
    private final UsersRepositoryV1 usersRepository;

    public DataBaseUserDetailService(UsersRepositoryV1 usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found!");
        }
        return new UserPrincipal(user.get());
    }
}
