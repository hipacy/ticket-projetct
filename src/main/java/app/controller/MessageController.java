package app.controller;


import app.model.Message;
import app.model.Ticket;
import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.MessageService;
import app.service.TicketService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MessageController {


    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MessageService messageService;


    @PostMapping(value = "/sendMessage-{id}")
    public ModelAndView addMessage(ModelAndView modelAndView,
                                   @PathVariable Integer id,
                                   Message message,
                                   RedirectAttributes rr) {
        if (ticketService.checkId(modelAndView, id)) return modelAndView;
        Ticket currentTicket = ticketService.findTicketById(id);

        messageService.createNewMessage(message, userService.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()), currentTicket);

        rr.addFlashAttribute(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "Message sent!");
        modelAndView.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.TICKET_VIEW_TEMPLATE_NAME + "-" + id);

        return modelAndView;
    }
}