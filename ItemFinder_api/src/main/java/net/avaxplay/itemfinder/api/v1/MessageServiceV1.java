package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceV1 {

    private final MessageRepositoryV1 messageRepository;

    public MessageServiceV1(MessageRepositoryV1 messageRepository) {
        this.messageRepository = messageRepository;
    }
public List<Message> findByItemId(Integer id){
        List<Message> messages = messageRepository.findByItemId(id);
    System.out.println("Messages for itemId " + id + ": " + messages);
        return messages;
    }

    public boolean add(Message message) {
        return messageRepository.add(message);
    }

}
