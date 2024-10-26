package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryV1 {
    private static final Logger log = LoggerFactory.getLogger(UsersRepositoryV1.class);
    private final JdbcClient jdbcClient;

    public UsersRepositoryV1(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM Users")
                .query(User.class)
                .list();
    }

    public Optional<User> findById(Integer id) {
        return jdbcClient.sql("SELECT UserId, Username, PasswordHash FROM Users WHERE UserId = :Id")
                .param("Id", id)
                .query(User.class)
                .optional();
    }

    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("SELECT UserId, Username, PasswordHash FROM Users WHERE Username = :Username")
                .param("Username", username)
                .query(User.class)
                .optional();
    }

    public boolean create(User user) {
        var updated = jdbcClient.sql("INSERT INTO Users (UserId, Username, PasswordHash) VALUES (?, ?, ?)")
                .params(user.UserId(), user.Username(), user.PasswordHash())
                .update();
        return updated == 1;
    }

    public boolean createAutoId(User user) {
        var updated = jdbcClient.sql("INSERT INTO Users (Username, PasswordHash) VALUES (?, ?)")
                .params(user.Username(), user.PasswordHash())
                .update();
        return updated == 1;
    }

    public boolean update(User user) {
       var updated = jdbcClient.sql("UPDATE Users SET Username = ?, PasswordHash = ? WHERE UserId = ?")
                .params(user.Username(), user.PasswordHash(), user.UserId())
                .update();
        return updated == 1;

    }
public boolean delete(Integer id) {
    // First, delete from LostItem where creatorId is the user ID
    jdbcClient.sql("DELETE FROM LostItem WHERE creatorId = ?")
            .params(id)
            .update();

    // Next, delete from FoundItem where creatorId is the user ID
    jdbcClient.sql("DELETE FROM FoundItem WHERE creatorId = ?")
            .params(id)
            .update();
        var updated = jdbcClient.sql("DELETE FROM Users WHERE UserId = ?")
                .params(id)
                .update();
        return updated == 1;
    }
    public List<User> findByNameContaining(String name) {
        String searchQuery = "%" + name + "%";
        List<User> users = jdbcClient.sql("SELECT * FROM Users WHERE UserName LIKE :name")
                .param("name", searchQuery)
                .query(User.class)
                .list();
        return users.isEmpty() ? null : users;
    }


}
