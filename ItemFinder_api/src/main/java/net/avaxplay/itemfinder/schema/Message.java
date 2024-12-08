package net.avaxplay.itemfinder.schema;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Message(
      @Id Integer messageId,
        Integer senderId,
        Integer itemId,
        String messageText,
        LocalDateTime time)
{

}
