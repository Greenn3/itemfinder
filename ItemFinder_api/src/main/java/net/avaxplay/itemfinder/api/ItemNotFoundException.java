package net.avaxplay.itemfinder.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Item Not Found!");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
