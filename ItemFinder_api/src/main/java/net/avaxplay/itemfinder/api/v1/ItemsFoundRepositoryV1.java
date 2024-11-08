package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemsFoundRepositoryV1 {
    private static final Logger log = LoggerFactory.getLogger(ItemsFoundRepositoryV1.class);
    private final JdbcClient jdbcClient;

    public ItemsFoundRepositoryV1(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Item> findAll() {
        return jdbcClient.sql("SELECT * FROM FoundItems")
                .query(Item.class)
                .list();
    }

    public Optional<Item> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM FoundItems WHERE ItemId = :ItemId")
                .param("ItemId", id)
                .query(Item.class)
                .optional();
    }

    public List<Item> findByCreatorId(Integer id) {
        return jdbcClient.sql("SELECT * FROM FoundItems WHERE CreatorId = :CreatorId")
                .param("CreatorId", id)
                .query(Item.class)
                .list();
    }

    public List<Item> findByCreatorUsername(String username) {
        Optional<Integer> id = jdbcClient.sql("SELECT UserId FROM Users WHERE Username = :Username")
                .param("Username", username)
                .query(Integer.class)
                .optional();
        if (id.isEmpty()) return List.of();
        return jdbcClient.sql("SELECT * FROM FoundItems WHERE CreatorId = :CreatorId")
                .param("CreatorId", id.get())
                .query(Item.class)
                .list();
    }

    public List<Item> findByCompleted(Boolean completed) {
        return jdbcClient.sql("SELECT * FROM FoundItems WHERE Completed = :Completed")
                .param("Completed", completed)
                .query(Item.class)
                .list();
    }

    public boolean create(Item item) {
        var updated = jdbcClient.sql("insert into FoundItems (CreatorId, ItemName, ItemDescription, EventDate, ImageUrl, Latitude, Longitude) values (?, ?, ?, ?, ?, ?, ?)")
                .params(item.CreatorId(), item.ItemName(), item.ItemDescription(), item.EventDate(), item.ImageUrl(), item.Latitude(), item.Longitude())
                .update();
        return updated == 1;
    }

    public boolean update(Item item) {
        var updated = jdbcClient.sql("UPDATE FoundItems SET CreatorId = ?, ItemName = ?, ItemDescription = ?, EventDate = ?, ImageUrl = ?, Completed = ?, HelperId = ?, Latitude = ?, Longitude = ? WHERE ItemId = ?")
                .params(item.CreatorId(), item.ItemName(), item.ItemDescription(), item.EventDate(), item.ImageUrl(), item.Completed(), item.HelperId(), item.Latitude(), item.Longitude(), item.ItemId())
                .update();
        return updated == 1;
    }


    public boolean delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM FoundItems WHERE ItemId = ?")
                .param(id)
                .update();
        return updated == 1;
    }



    public List<Item> findByNameContaining(String name) {
        String searchQuery = "%" + name.toLowerCase() + "%";
        List<Item> items = jdbcClient.sql("SELECT * FROM LostItems WHERE LOWER(ItemName) LIKE :name")
                .param("name", searchQuery)
                .query(Item.class)
                .list();
        return items.isEmpty() ? null : items;
    }

    public List<Item> findByNameOrDescriptionContaining(String name) {
        String searchQuery = "%" + name.toLowerCase() + "%";
        List<Item> items = jdbcClient.sql(
                        "SELECT * FROM FoundItems WHERE LOWER(ItemName) LIKE :searchQuery OR LOWER(ItemDescription) LIKE :searchQuery")
                .param("searchQuery", searchQuery)
                .query(Item.class)
                .list();
        return items.isEmpty() ? null : items;
    }



    public List<Item> findByCreationDate(LocalDateTime creationDate) {
        List<Item> items =  jdbcClient.sql("SELECT * FROM FoundItems WHERE CreationDate = :CreationDate")
                .param("CreationDate", creationDate)
                .query(Item.class)
                .list();
        return items.isEmpty() ? null : items;
    }


}
