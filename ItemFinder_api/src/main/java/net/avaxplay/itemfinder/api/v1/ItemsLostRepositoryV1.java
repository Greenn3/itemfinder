package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemsLostRepositoryV1 {
    private static final Logger log = LoggerFactory.getLogger(ItemsLostRepositoryV1.class);
    private final JdbcClient jdbcClient;

    public ItemsLostRepositoryV1(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Item> findAll() {
        return jdbcClient.sql("SELECT * FROM LostItems")
                .query(Item.class)
                .list();
    }

    public Optional<Item> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM LostItems WHERE ItemId = :ItemId")
                .param("ItemId", id)
                .query(Item.class)
                .optional();
    }




    public List<Item> findByCreatorId(Integer id) {
        return jdbcClient.sql("SELECT * FROM LostItems WHERE CreatorId = :CreatorId")
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
        return jdbcClient.sql("SELECT * FROM LostItems WHERE CreatorId = :CreatorId")
                .param("CreatorId", id.get())
                .query(Item.class)
                .list();
    }

    public List<Item> findByCompleted(Boolean completed) {
        return jdbcClient.sql("SELECT * FROM LostItems WHERE Completed = :Completed")
                .param("Completed", completed)
                .query(Item.class)
                .list();
    }

    public boolean create(Item item) {
        var updated = jdbcClient.sql("insert into LostItems (CreatorId, ItemName, ItemDescription, EventDate, ImageUrl, Latitude, Longitude, LocationText) values (?, ?, ?, ?, ?, ?, ?, ?)")
                .params(item.CreatorId(), item.ItemName(), item.ItemDescription(), item.EventDate(), item.ImageUrl(), item.Latitude(), item.Longitude(), item.LocationText())
                .update();
        return updated == 1;
    }

    public boolean update(Item item) {
        var updated = jdbcClient.sql("UPDATE LostItems SET CreatorId = ?, ItemName = ?, ItemDescription = ?, EventDate = ?, ImageUrl = ?, Completed = ?, HelperId = ?, Latitude = ?, Longitude = ?, LocationText = ? WHERE ItemId = ?")
                .params(item.CreatorId(), item.ItemName(), item.ItemDescription(), item.EventDate(), item.ImageUrl(), item.Completed(), item.HelperId(), item.Latitude(), item.Longitude(), item.LocationText(), item.ItemId())
                .update();
        return updated == 1;
    }
    public boolean delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM LostItems WHERE ItemId = ?")
                .params(id)
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
                        "SELECT * FROM LostItems WHERE LOWER(ItemName) LIKE :searchQuery OR LOWER(ItemDescription) LIKE :searchQuery")
                .param("searchQuery", searchQuery)
                .query(Item.class)
                .list();
        return items.isEmpty() ? null : items;
    }
    public List<Item> findByCreationDate(LocalDateTime creationDate) {
       List<Item> items =  jdbcClient.sql("SELECT * FROM LostItems WHERE CreationDate = :CreationDate")
                .param("CreationDate", creationDate)
                .query(Item.class)
                .list();
       return items.isEmpty() ? null : items;
    }

    public List<Item> findAllSorted(String sortBy, String order) {
        // Default values if parameters are null or invalid
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "CreationDate"; // Default column to sort by
        }

        if (order == null || (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc"))) {
            order = "asc"; // Default order
        }

        // Build the query with sorting
        String sql = "SELECT * FROM LostItems ORDER BY " + sortBy + " " + order;

        // Execute the query
        return jdbcClient.sql(sql)
                .query(Item.class)
                .list();
    }

}
