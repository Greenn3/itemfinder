package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.Message;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryV1 {

    private final JdbcClient jdbcClient;

    public MessageRepositoryV1(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    //add
    public boolean add(Message message) {
        var updated = jdbcClient.sql("insert into message (  senderId, itemId , messageText, time) values ( ?, ?, ?, ?)")
                .params( message.senderId(), message.itemId(), message.messageText(), message.time())
                .update();
        return updated == 1;
    }



    //get by item id
    public List<Message> findByItemId(Integer id){
        return jdbcClient.sql("SELECT * FROM message WHERE itemid = :itemid")
                .param("itemid", id)
                .query(Message.class)
                .list();
    }



}
