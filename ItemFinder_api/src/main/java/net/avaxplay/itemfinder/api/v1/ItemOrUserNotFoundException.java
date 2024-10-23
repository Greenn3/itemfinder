package net.avaxplay.itemfinder.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemOrUserNotFoundException extends RuntimeException {
    public ItemOrUserNotFoundException() {
        super("Item Or User Not Found!");
    }

    public ItemOrUserNotFoundException(String message) {
        super(message);
    }
}
