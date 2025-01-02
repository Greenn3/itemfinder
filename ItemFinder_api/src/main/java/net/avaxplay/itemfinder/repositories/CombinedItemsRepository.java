package net.avaxplay.itemfinder.repositories;

import net.avaxplay.itemfinder.schema.CombinedItem;
import net.avaxplay.itemfinder.schema.Item;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CombinedItemsRepository {
    private final JdbcClient jdbcClient;

    public CombinedItemsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<CombinedItem> findAll() {
        return jdbcClient.sql("SELECT * FROM CombinedItems")
                .query(CombinedItem.class)
                .list();
    }

    public List<CombinedItem> findAllFiltered() {
        return jdbcClient.sql("SELECT * FROM CombinedItemsFiltered")
                .query(CombinedItem.class)
                .list();
    }

    public List<CombinedItem> findAllDateDesc() {
        return jdbcClient.sql("SELECT * FROM CombinedItems ORDER BY EventDate DESC")
                .query(CombinedItem.class)
                .list();
    }

    public List<CombinedItem> findAllFilteredDateDesc() {
        return jdbcClient.sql("SELECT * FROM CombinedItemsFiltered ORDER BY EventDate DESC")
                .query(CombinedItem.class)
                .list();
    }
    public List<String> queryColumns() {
        return jdbcClient.sql("SELECT column_name FROM information_schema.columns WHERE table_name = 'combineditems'")
                .query(String.class)
                .list();
    }
    public List<CombinedItem> searchAndSort(String searchPhrase, String orderByColumn, boolean descending, boolean filtered) {
        //System.out.printf("searchPhrase = [%s], sortByColumn = [%s], descending = [%b]%n", searchPhrase, orderByColumn, descending);
        String column = "eventdate";
        if (orderByColumn != null && !orderByColumn.isBlank()) {
            orderByColumn = orderByColumn.toLowerCase();
            if (queryColumns().contains(orderByColumn)) {
                column = orderByColumn;
            }
        }
        //System.out.printf("verified column = [%s]%n", column);
        //noinspection SqlSourceToSinkFlow // String 'column' is protected from SQL-injection by checking in database is it the valid column name
        return jdbcClient.sql("SELECT * FROM " + (filtered ? "CombinedItemsFiltered" : "CombinedItems") + ((searchPhrase == null || searchPhrase.isBlank()) ? " " : " WHERE ItemName ILIKE :Search ") + "ORDER BY " + column + " " + (descending ? "DESC" : "ASC"))
                .param("Search", "%" + searchPhrase + "%")
                .query(CombinedItem.class)
                .list();
    }
}
