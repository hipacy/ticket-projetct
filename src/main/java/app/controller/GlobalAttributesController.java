package app.controller;


import app.model.Item;
import app.service.ItemService;
import app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Set;

@SessionAttributes({"allItems", "allCategories", "allPrios", "states"})
@ControllerAdvice
public class GlobalAttributesController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketService ticketService;


    @ModelAttribute("allItems")
    public List<Item> getItems() {
        return itemService.findAllItems();
    }

    @ModelAttribute("allCategories")
    public Set<String> getCategories() {
        return ticketService.findAllCategories();
    }

}
