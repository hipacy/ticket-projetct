package app.controller;

import app.model.*;
import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.MessageService;

import app.service.TicketService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
public class TicketController {


    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

//
//

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/createTicket")
    public ModelAndView createTicket(ModelAndView modelAndView) {

        modelAndView.addObject(ModelNames.EDIT_FLAG_MODEL_NAME, false);
        modelAndView.addObject(ModelNames.TICKET_MODEL_NAME, new Ticket());
        modelAndView.addObject(ModelNames.USER_MODEL_NAME,
                userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

        modelAndView.setViewName(TemplateNames.TICKET_EDITOR_TEMPLATE_NAME);

        return modelAndView;
    }

    @PostMapping(value = "/createTicket")
    public ModelAndView createTicket(ModelAndView modelAndView,
                                     @Valid Ticket ticket,
                                     BindingResult bindingResult,
                                     RedirectAttributes rr) {
        rr.addFlashAttribute(ModelNames.EDIT_FLAG_MODEL_NAME, false);

        if (bindingResult.hasErrors()) {
            rr.addFlashAttribute(ModelNames.ERROR_MESSAGE_MODEL_NAME, "Fill in all fields!!!");
            modelAndView.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.CREATE_TICKET_TEMPLATE_NAME);

        } else {

            ticketService.createTicket(ticket, userService.findUserByEmail(SecurityContextHolder
                    .getContext().getAuthentication().getName()));
            rr.addFlashAttribute(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "Ticket has been added!");
            modelAndView.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.CREATE_TICKET_TEMPLATE_NAME);
        }


        return modelAndView;
    }


    @Secured({"ADMIN", "TECHNICIAN"})
    @PostMapping(value = "/ticketView-{id}")
    public ModelAndView ticketView(ModelAndView modelAndView,
                                   @PathVariable Integer id,
                                   Ticket ticket,
                                   RedirectAttributes rr) {
        if (ticketService.checkId(modelAndView, id)) return modelAndView;
        ticketService.editTicket(ticket, id);

        rr.addFlashAttribute(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "Ticket has been edited!");
        modelAndView.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.TICKET_VIEW_TEMPLATE_NAME + "-" + id);

        return modelAndView;
    }

    @Secured({"ADMIN", "TECHNICIAN"})
    @GetMapping(value = "/ticketView-{id}")
    public ModelAndView ticketView(ModelAndView modelAndView,
                                   @PathVariable Integer id) {
        if (id != null) {
            Ticket currentTicket = ticketService.findTicketById(id);

//

            modelAndView.addObject(ModelNames.TICKET_MODEL_NAME, currentTicket);
            modelAndView.addObject(ModelNames.MESSAGES_MODEL_NAME, messageService.findMessagesByTicketId(currentTicket));
            modelAndView.addObject(ModelNames.MESSAGE_MODEL_NAME, new Message());
            modelAndView.addObject(ModelNames.TECHNICIANS_MODEL_NAME, userService.findUserByRoles(
                    new UserType(3, "TECHNICIAN")));
            modelAndView.addObject(ModelNames.SOFTWARE_ITEMS_MODEL_NAME, currentTicket.getItems().stream().filter(
                    it -> "Software".equals(it.getType())).collect(Collectors.toList()));
            modelAndView.addObject(ModelNames.HARDWARE_ITEMS_MODEL_NAME, currentTicket.getItems().stream().filter(
                    it -> "Hardware".equals(it.getType())).collect(Collectors.toList()));
            modelAndView.addObject(ModelNames.OTHER_ITEMS_MODEL_NAME, currentTicket.getItems().stream().filter(
                    it -> "Other".equals(it.getType())).collect(Collectors.toList()));

            modelAndView.setViewName(TemplateNames.TICKET_VIEW_TEMPLATE_NAME);

        } else {
            modelAndView.setViewName(TemplateNames.ACCESS_DENIED_ERROR_TEMPLATE_NAME);
            return modelAndView;
        }


        return modelAndView;
    }

}
