package net.avaxplay.itemfinder.web;

import jakarta.validation.Valid;
import net.avaxplay.itemfinder.api.v1.ItemsFoundServiceV1;
import net.avaxplay.itemfinder.api.v1.ItemsLostServiceV1;
import net.avaxplay.itemfinder.api.v1.UsersServiceV1;
import net.avaxplay.itemfinder.schema.Item;
import net.avaxplay.itemfinder.schema.ItemForm;
import net.avaxplay.itemfinder.schema.UserNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {
    private final UsersServiceV1 usersService;
    private final ItemsLostServiceV1 itemsLostService;
    private final ItemsFoundServiceV1 itemsFoundService;

    public WebController(UsersServiceV1 usersService, ItemsLostServiceV1 itemsLostService, ItemsFoundServiceV1 itemsFoundService) {
        this.usersService = usersService;
        this.itemsLostService = itemsLostService;
        this.itemsFoundService = itemsFoundService;
    }

    @RequestMapping("/")
    public String index() {
        return "web/index";
    }

    @RequestMapping("/template")
    public String template() {
        return "web/template";
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute UserNew userNew) {
        usersService.create(userNew);
        return "redirect:/dynamic";
    }

    //@RequestMapping("/dynamic")
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
        Optional<Item> item = itemsFoundService.findById(id);
        if (item.isEmpty()) return "web/item-not-found";
        model.addAttribute("item", item.get());
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
            return "create-lost";
        }
        Item item = new Item(
                null,
                itemForm.getCreatorId(),
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
        return "redirect:/lost-items";
    }

    @PostMapping("/create-found")
    public String createFoundItem(@ModelAttribute @Valid ItemForm itemForm, BindingResult result) {
        if (result.hasErrors()) {
            return "create-found";
        }
        Item item = new Item(
                null,
                itemForm.getCreatorId(),
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
        return "redirect:/found-items";
    }






    @RequestMapping("/test-site2")
    public String test() {
        return "web/testing-site";
    }


    @RequestMapping("/test-site3")
    public String testSiteV2() {
        return "web/indexV2";
    }


    @RequestMapping("/found-itemsV2")
    public String foundItemsV2(Model model) {
        model.addAttribute("items", itemsFoundService.findAll());
        return "web/found-itemsV2";
    }

    @RequestMapping("/lost-itemsV2")
    public String lostItemsV2(Model model) {
        model.addAttribute("items", itemsLostService.findAll());
        return "web/lost-itemsV2";
    }


    @RequestMapping("/lost-itemsV2/{id}")
    public String lostItemIdV2(Model model, @PathVariable Integer id) {
       Optional<Item> item = itemsLostService.findById(id);
        if (item.isEmpty()) return "web/item-not-found";
        model.addAttribute("item", item.get());
        return "web/lost-item-singularV2";
    }

    @RequestMapping("/found-itemsV2/{id}")
    public String foundItemIdV2(Model model, @PathVariable Integer id) {
        Optional<Item> item = itemsFoundService.findById(id);
        if (item.isEmpty()) return "web/item-not-found";
        model.addAttribute("item", item.get());
        return "web/found-item-singularV2";
    }

    // Method to handle search requests
    @GetMapping("/search-found-items")
    public String searchFoundItemsByName(@RequestParam("name") String name, Model model) {
        List<Item> items = itemsFoundService.findByNameContaining(name);
        model.addAttribute("items", items);
        return "web/found-itemsV2"; // The template displaying the items, adjust if necessary
    }

    @GetMapping("/search-lost-items")
    public String searchLostItemsByName(@RequestParam("name") String name, Model model) {
        List<Item> items = itemsLostService.findByNameContaining(name);
        model.addAttribute("items", items);
        return "web/lost-itemsV2"; // The template displaying the items, adjust if necessary
    }


    @RequestMapping("/add-lostV2")
    public String addLostItemV2(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "web/add-lostV2";
    }

    @RequestMapping("/add-foundV2")
    public String addFoundItemV2(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "web/add-foundV2";
    }

}

