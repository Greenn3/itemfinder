package net.avaxplay.itemfinder.web;

import jakarta.validation.Valid;
import net.avaxplay.itemfinder.api.v1.*;
import net.avaxplay.itemfinder.model.UserPrincipal;
import net.avaxplay.itemfinder.schema.*;
import net.avaxplay.itemfinder.services.CombinedItemsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private final UsersServiceV1 usersService;
    private final ItemsLostServiceV1 itemsLostService;
    private final ItemsFoundServiceV1 itemsFoundService;
    private final ImageUploadService imageUploadService;

    private final MessageServiceV1 messageService;
    private final CombinedItemsService combinedItemsService;

    public WebController(UsersServiceV1 usersService, ItemsLostServiceV1 itemsLostService, ItemsFoundServiceV1 itemsFoundService, ImageUploadService imageUploadService, MessageServiceV1 messageService, CombinedItemsService combinedItemsService) {
        this.usersService = usersService;
        this.itemsLostService = itemsLostService;
        this.itemsFoundService = itemsFoundService;
        this.imageUploadService = imageUploadService;
        this.messageService = messageService;
        this.combinedItemsService = combinedItemsService;
    }

//    @RequestMapping("/")
//    public String index() {
//        return "web/index";
//    }
private Map<Integer, String> getUsersMap() {
    return usersService.findAll().stream().collect(Collectors.toMap(User::UserId, User::Username));
}
    @RequestMapping("/template")
    public String template() {
        return "web/template";
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
            System.out.println(result);
            return "redirect:/add-lostV2";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal(); // Assuming your UserDetails implementation includes an ID
        Integer creatorId = userDetails.getId(); // Extract the user ID

        String imagePath = null;
        if (itemForm.getImageFile() != null) {
            try {
                imagePath = imageUploadService.uploadFoundImage(itemForm.getImageFile());
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }

        Item item = new Item(
                null,
                creatorId,
                itemForm.getItemName(),
                itemForm.getItemDescription(),
                LocalDateTime.now(), // creationDate set to current time
                itemForm.getEventDate(),
                (imagePath == null ? itemForm.getImageUrl() : imagePath),
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
            return "web/V2/found-itemsV2";
        }
        String imagePath = null;
        if (itemForm.getImageFile() != null) {
            try {
                imagePath = imageUploadService.uploadFoundImage(itemForm.getImageFile());
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
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
                (imagePath == null ? itemForm.getImageUrl() : imagePath),
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



    @RequestMapping("/found-itemsV2")
    public String foundItemsV2(
            Model model,
            @ModelAttribute("searchForm") SearchSortForm searchSortForm,
            BindingResult result,
            @RequestParam(required = false, defaultValue = "") String searchPhrase,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(required = false, defaultValue = "false") String filter
    ) {
        boolean filtered = (!Objects.equals(filter, "false"));
      //  model.addAttribute("itemUrlBase", "/found-itemsV2/");
        model.addAttribute("items", itemsFoundService.searchAndSort(searchPhrase, sortBy, (sortOrder.equals("desc")), filtered));
        model.addAttribute("formAction", "/found-itemsV2");
        model.addAttribute("filtered", filtered);
      //  model.addAttribute("whatHappened", "found");
        model.addAttribute("userMap", getUsersMap());
        if (result.hasErrors()) {
            System.out.println(result);
            return "web/V2/found-itemsV2";
        }
        return "web/V2/found-itemsV2";
    }



    @RequestMapping("/lost-itemsV2")
    public String lostItemsV2(
            Model model,
            @ModelAttribute("searchForm") SearchSortForm searchSortForm,
            BindingResult result,
            @RequestParam(required = false, defaultValue = "") String searchPhrase,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(required = false, defaultValue = "false") String filter
    ) {
        boolean filtered = (!Objects.equals(filter, "false"));
        //  model.addAttribute("itemUrlBase", "/found-itemsV2/");
        model.addAttribute("items", itemsLostService.searchAndSort(searchPhrase, sortBy, (sortOrder.equals("desc")), filtered));
        model.addAttribute("formAction", "/lost-itemsV2");
        model.addAttribute("filtered", filtered);
        //  model.addAttribute("whatHappened", "found");
        model.addAttribute("userMap", getUsersMap());
        if (result.hasErrors()) {
            System.out.println(result);
            return "web/V2/lost-itemsV2";
        }
        return "web/V2/lost-itemsV2";
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( "auth: " +authentication);
        System.out.println("Principal: " + authentication.getPrincipal());
        if(!authentication.getPrincipal().equals("anonymousUser")){
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            //System.out.println("id: " +((UserPrincipal) authentication.getPrincipal()).getId());// Assuming your UserDetails implementation includes an ID
            Integer userId = userDetails.getId();
            List<Message> messages = messageService.findByItemId(id);
            model.addAttribute("userId", userId);
            model.addAttribute("messages", messages);
        }


        //System.out.println("id: " +((UserPrincipal) authentication.getPrincipal()).getId());// Assuming your UserDetails implementation includes an ID



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
        System.out.println( "auth: " +authentication);
        System.out.println("Principal: " + authentication.getPrincipal());
        if(!authentication.getPrincipal().equals("anonymousUser")){
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            //System.out.println("id: " +((UserPrincipal) authentication.getPrincipal()).getId());// Assuming your UserDetails implementation includes an ID
            Integer userId = userDetails.getId();
            List<Message> messages = messageService.findByItemId(id);
            model.addAttribute("userId", userId);
            model.addAttribute("messages", messages);
        }

        model.addAttribute("item", item);
        model.addAttribute("creatorName", creatorName);

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
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("addUser", new UserNew(null, null));
        return "web/V2/registerV2";
    }
    @PostMapping("/create-userV2")
    public String createUserV2(@ModelAttribute("addUser") UserNew userNew) {
        usersService.create(userNew);
        return "redirect:web/V2/loginV2";
    }



    @RequestMapping({ "/"})
    public String allItemsV2(
            Model model,
           @ModelAttribute("searchForm") SearchSortForm searchSortForm,
            BindingResult result,
            @RequestParam(required = false, defaultValue = "") String searchPhrase,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(required = false, defaultValue = "true") String filter
    ) {
        //System.out.printf("searchPhrase = [%s], sortBy = [%s], sortOrder = [%s], filter = [%s]%n", searchPhrase, sortBy, sortOrder, filter);
        boolean filtered = (!Objects.equals(filter, "false"));
        model.addAttribute("items", combinedItemsService.searchAndSort(searchPhrase, sortBy, (sortOrder.equals("desc")), filtered));
        model.addAttribute("formAction", "/");
        model.addAttribute("filtered", filtered);
        model.addAttribute("usersMap", getUsersMap());
        if (result.hasErrors()) {
            System.out.println(result);
            return "web/V2/indexV2";
        }
        return "web/V2/indexV2";
    }

   @RequestMapping("/terms")
    public String terms(){
        return "web/V2/terms";
    }

    @RequestMapping("/delete-lost-item/{id}")
    public String deleteListItem(@PathVariable Integer id){
        itemsLostService.delete(id);
        return "redirect:/lost-itemsV2";
    }

    @RequestMapping("/delete-found-item/{id}")
    public String deleteFoundItem(@PathVariable Integer id){
        itemsFoundService.delete(id);
        return "redirect:/found-itemsV2";
    }


}

