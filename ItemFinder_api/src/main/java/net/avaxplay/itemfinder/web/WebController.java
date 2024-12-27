package net.avaxplay.itemfinder.web;

import jakarta.validation.Valid;
import net.avaxplay.itemfinder.api.v1.ItemsFoundServiceV1;
import net.avaxplay.itemfinder.api.v1.ItemsLostServiceV1;
import net.avaxplay.itemfinder.api.v1.MessageServiceV1;
import net.avaxplay.itemfinder.api.v1.UsersServiceV1;
import net.avaxplay.itemfinder.model.UserPrincipal;
import net.avaxplay.itemfinder.schema.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private final UsersServiceV1 usersService;
    private final ItemsLostServiceV1 itemsLostService;
    private final ItemsFoundServiceV1 itemsFoundService;

    private final MessageServiceV1 messageService;

    public WebController(UsersServiceV1 usersService, ItemsLostServiceV1 itemsLostService, ItemsFoundServiceV1 itemsFoundService, MessageServiceV1 messageService) {
        this.usersService = usersService;
        this.itemsLostService = itemsLostService;
        this.itemsFoundService = itemsFoundService;
        this.messageService = messageService;
    }

    @RequestMapping("/")
    public String index() {
        return "web/index";
    }

    @RequestMapping("/template")
    public String template() {
        return "web/template";
    }

//    @PostMapping("/create-user")
//    public String createUser(@ModelAttribute UserNew userNew) {
//        usersService.create(userNew);
//        return "redirect:/dynamic";
//    }

    @RequestMapping("/dynamic")
    public String dynamic(Model model) {
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("addUser", new UserNew(null, null));
        return "web/dynamic";
    }

    @RequestMapping("/lost-items")
    public String lostItems(Model model) {
        model.addAttribute("items", itemsLostService.findAll());
        return "web/lost-items";
    }

    @RequestMapping("/found-items")
    public String foundItems(Model model) {
        model.addAttribute("items", itemsFoundService.findAll());
        return "web/found-items";
    }

    @RequestMapping("/lost-items/{id}")
    public String lostItemId(Model model, @PathVariable Integer id) {
        Optional<Item> item = itemsLostService.findById(id);
        if (item.isEmpty()) return "web/item-not-found";
        model.addAttribute("item", item.get());
        return "web/lost-item-singular";
    }

    @RequestMapping("/found-items/{id}")
    public String foundItemId(Model model, @PathVariable Integer id) {
        System.out.println("here");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal(); // Assuming your UserDetails implementation includes an ID
        Integer userId = userDetails.getId();
        Optional<Item> item = itemsFoundService.findById(id);
        if (item.isEmpty()) return "web/item-not-found";

        List<Message> messages = messageService.findByItemId(id);

        System.out.println("Messages for itemId " + id + ": " + messages);

        model.addAttribute("item", item.get());
model.addAttribute("userId", userId);
        model.addAttribute("messages", messages);
        return "web/found-item-singular";
    }

    @RequestMapping("/map")
    public String showMap() {
        return "web/map";
    }

    @RequestMapping("/map/{latitude}/{longitude}")
    public String showMap(@PathVariable Double latitude, @PathVariable Double longitude, Model model) {
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        return "web/map";
    }

    @RequestMapping("/add-lost")
    public String addLostItem(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "web/add-lost";
    }

    @RequestMapping("/add-found")
    public String addFoundItem(Model model) {
//model.addAttribute("items", itemsFoundService.findAll() );
        model.addAttribute("itemForm", new ItemForm());
        return "web/add-found";
    }


    @PostMapping("/create-lost")
    public String createLostItem(@ModelAttribute @Valid ItemForm itemForm, BindingResult result) {
        if (result.hasErrors()) {
            return "/lost-itemsV2";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal(); // Assuming your UserDetails implementation includes an ID
        Integer creatorId = userDetails.getId(); // Extract the user ID
        Item item = new Item(
                null,
                creatorId,
                itemForm.getItemName(),
                itemForm.getItemDescription(),
                LocalDateTime.now(), // creationDate set to current time
                itemForm.getEventDate(),
                itemForm.getImageUrl(),
                itemForm.getCompleted(),
                itemForm.getHelperId(),
                itemForm.getLatitude(),
                itemForm.getLongitude(),
                itemForm.getLocationText()
        );
        itemsLostService.create(item);
        return "redirect:/lost-itemsV2";
    }

//    @PostMapping("/send-message")
//    public String sendMessage(){
//
//    }
@PostMapping("/send-message")
public String sendMessage(@ModelAttribute Message message) {
messageService.add(message);
    return "redirect:/lost-itemsV2";
}


    @PostMapping("/create-found")
    public String createFoundItem(@ModelAttribute @Valid ItemForm itemForm, BindingResult result) {
        if (result.hasErrors()) {
            return "/found-itemsV2";
        }

        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal(); // Assuming your UserDetails implementation includes an ID
        Integer creatorId = userDetails.getId(); // Extract the user ID

        // Create the Item object with the creatorId from the logged-in user
        Item item = new Item(
                null,
                creatorId, // Set creatorId from logged-in user
                itemForm.getItemName(),
                itemForm.getItemDescription(),
                LocalDateTime.now(), // creationDate set to current time
                itemForm.getEventDate(),
                itemForm.getImageUrl(),
                itemForm.getCompleted(),
                itemForm.getHelperId(),
                itemForm.getLatitude(),
                itemForm.getLongitude(),
                itemForm.getLocationText()
        );

        itemsFoundService.create(item);
        return "redirect:/found-itemsV2";
    }





    @RequestMapping("/test-site2")
    public String test() {
        return "web/testing-site";
    }


//    @RequestMapping("/test-site3")
//    public String testSiteV2() {
//        return "web/V2/indexV2";
//    }


    @RequestMapping("/found-itemsV2")
    public String foundItemsV2(Model model) {
        List<Item> items = itemsFoundService.findAll();
        Map<Integer, String> userIdToUsernameMap = usersService.findAll() // Add a method to fetch all users
                .stream()
                .collect(Collectors.toMap(User::UserId, User::Username));
        model.addAttribute("items", items);
        model.addAttribute("userMap", userIdToUsernameMap);
        return "web/V2/found-itemsV2";
    }

    @RequestMapping("/lost-itemsV2")
    public String lostItemsV2(Model model) {
        List<Item> items = itemsLostService.findAll();
        Map<Integer, String> itemsLostTUsernameMap = usersService.findAll()
                .stream()
                .collect(Collectors.toMap(User::UserId, User::Username));

        model.addAttribute("items", items);
        model.addAttribute("userMap", itemsLostTUsernameMap);
        return "web/V2/lost-itemsV2";
    }
    @RequestMapping("/found-itemsSortedV2")
    public String foundItemsSortedV2(
            @RequestParam(required = false, defaultValue = "CreationDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            Model model) {

        // Fetch sorted items from the service layer
        List<Item> sortedItems = itemsFoundService.getSortedItems(sortBy, order);

        // Add sorted items to the model
        model.addAttribute("items", sortedItems);

        // Return the Thymeleaf view
        return "web/V2/found-itemsV2";
    }

    @RequestMapping("/lost-itemsSortedV2")
    public String lostItemsSortedV2(
            @RequestParam(required = false, defaultValue = "CreationDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            Model model) {

        // Fetch sorted items from the service layer
        List<Item> sortedItems = itemsLostService.getSortedItems(sortBy, order);

        // Add sorted items to the model
        model.addAttribute("items", sortedItems);

        // Return the Thymeleaf view
        return "web/V2/found-itemsV2";
    }




    @RequestMapping("/lost-itemsV2/{id}")
    public String lostItemIdV2(Model model, @PathVariable Integer id) {
       Optional<Item> itemOptional = itemsLostService.findById(id);
        if (itemOptional.isEmpty()) {
            return "web/item-not-found";
        }
        Item item = itemOptional.get();

        Optional<User> usersOptional = usersService.findById(item.CreatorId());
        String creatorName = usersOptional.map(User::Username).orElse("Unknown Creator");

        model.addAttribute("item", item);
        model.addAttribute("creatorName", creatorName);
        return "web/V2/lost-item-singularV2";
    }

    @RequestMapping("/found-itemsV2/{id}")
    public String foundItemIdV2(Model model, @PathVariable Integer id, Principal principal) {
        Optional<Item> itemOptional = itemsFoundService.findById(id);
        if (itemOptional.isEmpty()) {
            return "web/item-not-found";
        }

        Item item = itemOptional.get();

        // Fetch the creator's name using the creatorId from the item
        Optional<User> userOptional = usersService.findById(item.CreatorId());
        String creatorName = userOptional.map(User::Username).orElse("Unknown Creator");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        System.out.println("id: " +((UserPrincipal) authentication.getPrincipal()).getId());// Assuming your UserDetails implementation includes an ID
        Integer userId = userDetails.getId();
        List<Message> messages = messageService.findByItemId(id);
        System.out.println("Messages for itemId " + id + ": " + messages);
        // Add the item and the creator's name to the model
        model.addAttribute("item", item);
        model.addAttribute("creatorName", creatorName);
        model.addAttribute("userId", userId);
        model.addAttribute("messages", messages);
//        model.addAttribute("sender", usersService.findById(messag));


        return "web/V2/found-item-singularV2";
    }


    // Method to handle search requests
    @GetMapping("/search-found-items")
    public String searchFoundItemsByName(@RequestParam("name") String name, Model model) {
        List<Item> items = itemsFoundService.findByNameContaining(name);
        model.addAttribute("items", items);
        return "web/V2/found-itemsV2"; // The template displaying the items, adjust if necessary
    }

    @GetMapping("/search-lost-items")
    public String searchLostItemsByName(@RequestParam("name") String name, Model model) {
        List<Item> items = itemsLostService.findByNameContaining(name);
        model.addAttribute("items", items);
        return "web/V2/lost-itemsV2"; // The template displaying the items, adjust if necessary
    }


    @RequestMapping("/add-lostV2")
    public String addLostItemV2(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "web/V2/add-lostV2";
    }

    @RequestMapping("/add-foundV2")
    public String addFoundItemV2(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "web/V2/add-foundV2";
    }


    @RequestMapping("/loginV2")
    public String loginV2(){
        return "web/V2/loginV2";
    }
    @RequestMapping("/registerV2")
    public String registerV2(Model model) {
        System.out.println("here2");
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("addUser", new UserNew(null, null));
        return "web/V2/registerV2";
    }
    @PostMapping("/create-userV2")
    public String createUserV2(@ModelAttribute("addUser") UserNew userNew) {
        System.out.println("here");
        usersService.create(userNew);
        System.out.println(userNew);
        return "redirect:web/V2/loginV2";
    }

    @RequestMapping("/test-site3")
    public String allItemsV2(Model model) {


        List<Item> foundItems = itemsFoundService.findAll();
        List<Item> lostItems = itemsLostService.findAll();
        Map<Item, String> combinedItems = new HashMap<>();

        for (Item item : foundItems) {
            combinedItems.put(item, "found");
        }

        for (Item item : lostItems) {
            combinedItems.put(item, "lost");
        }
        model.addAttribute("allItems", combinedItems);

        Map<Integer, String> userIdToUsernameMap = usersService.findAll() // Add a method to fetch all users
                .stream()
                .collect(Collectors.toMap(User::UserId, User::Username));

        model.addAttribute("userMap", userIdToUsernameMap);
        return "web/V2/indexV2";
    }

}

