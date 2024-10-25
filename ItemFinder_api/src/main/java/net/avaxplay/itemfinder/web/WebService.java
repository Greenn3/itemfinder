package net.avaxplay.itemfinder.web;

import net.avaxplay.itemfinder.api.v1.ItemsFoundServiceV1;
import net.avaxplay.itemfinder.api.v1.ItemsLostServiceV1;
import net.avaxplay.itemfinder.api.v1.UsersServiceV1;
import org.springframework.stereotype.Service;

@Service
public class WebService {
    private final UsersServiceV1 usersService;
    private final ItemsLostServiceV1 itemsLostService;
    private final ItemsFoundServiceV1 itemsFoundService;

    public WebService(UsersServiceV1 usersService, ItemsLostServiceV1 itemsLostService, ItemsFoundServiceV1 itemsFoundService) {
        this.usersService = usersService;
        this.itemsLostService = itemsLostService;
        this.itemsFoundService = itemsFoundService;
    }
}
