package app.controller;


import app.model.Item;
import app.model.Prio;
import app.model.StateOfTicket;
import app.model.UserType;
import app.service.ItemService;
import app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @ModelAttribute("allPrios")
    public Set<Prio> getPrios() {
        return Stream.of(new Prio(1, "Low"), new Prio(2, "Medium"),
                new Prio(3, "High"))
                .collect(Collectors.toSet());
    }

    @ModelAttribute("states")
    public List<StateOfTicket> getStates() {
        return Stream.of(new StateOfTicket(1, "New"), new StateOfTicket(2, "Assigned"),
                new StateOfTicket(3, "Resolved"))
                .collect(Collectors.toList());
    }


    @ModelAttribute("userTypes")
    public List<UserType> getUserTypes() {
        return Stream.of(new UserType(1, "ADMIN "), new UserType(2, "USER"),
                new UserType(3, "TECHNICIAN"))
                .collect(Collectors.toList());
    }


}
