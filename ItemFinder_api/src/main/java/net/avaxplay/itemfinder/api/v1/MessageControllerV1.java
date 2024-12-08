package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
public class MessageControllerV1 {

    public final MessageServiceV1 messageService;


    public MessageControllerV1(MessageServiceV1 messageService) {
        this.messageService = messageService;
    }
@GetMapping("/byItem/{id}")
    public ResponseEntity<List<Message>> findByItemId(@PathVariable Integer id) {
        return ResponseEntity.ok(messageService.findByItemId(id));
    }
@PostMapping("")
    public HttpStatus add(@RequestBody Message message) {
        return messageService.add(message) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
    }


}
