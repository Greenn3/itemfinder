package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemFinderUsersRepositoryV1 {
    private static final Logger log = LoggerFactory.getLogger(ItemFinderUsersRepositoryV1.class);
    private final JdbcClient jdbcClient;

    public ItemFinderUsersRepositoryV1(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM Users")
                .query(User.class)
                .list();
    }

    public Optional<User> findById(Integer Id) {
        return jdbcClient.sql("SELECT Id, Username FROM Users WHERE Id = :Id")
                .param("Id", Id)
                .query(User.class)
                .optional();
    }

    public void create(User user) {
        var updated = jdbcClient.sql("INSERT INTO Users (UserId, Username, PasswordHash) VALUES (?, ?, ?)")
                .params(user.UserId(), user.Username(), user.PasswordHash())
                .update();
        Assert.state(updated == 1, "Failed to create user '" + user.Username() + "'");
    }
}
