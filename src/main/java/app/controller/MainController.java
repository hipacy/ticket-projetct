package app.controller;

import app.model.Filter;
import app.model.Ticket;
import app.model.Users;
import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.UserService;
import app.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @PostMapping(value = "/filter")
    public ModelAndView filter(ModelAndView mav, Filter filter, RedirectAttributes rr){

        rr.addFlashAttribute(ModelNames.FILTERING_FLAG_MODEL_NAME,  true);
        rr.addFlashAttribute(ModelNames.FILTER_MODEL_NAME, filter);

        mav.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.MAIN_TEMPLATE_NAME + buildFilterURL(filter));

        return mav;
    }

    @GetMapping(value = {"/","/main"})
    public ModelAndView main(ModelAndView modelAndView,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam(value = "size", required = false) Optional<Integer> size,
                             @RequestParam(value = "ticketId", required = false) String ticketId,
                             @RequestParam(value = "category", required = false) String category,
                             @RequestParam(value = "prio", required = false) String prio,
                             @RequestParam(value = "body", required = false) String body) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Filter filter = Filter.getNonEmptyFilter(ticketId, category, prio, body);

        Users user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<Ticket> ticketPage = ticketService.findPaginated(PageRequest.of(currentPage-1,pageSize), user, filter);

        modelAndView.addObject(ModelNames.TICKET_PAGE_MODEL_NAME, ticketPage);
        modelAndView.addObject(ModelNames.USER_MODEL_NAME, user);
        modelAndView.addObject(ModelNames.FILTER_MODEL_NAME, filter);

        int totalPages = ticketPage.getTotalPages();
        if(totalPages > 0)
        {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject(ModelNames.PAGE_NUMBERS_MODEL_NAME, pageNumbers);
        }
        modelAndView.setViewName(TemplateNames.MAIN_TEMPLATE_NAME);
        return modelAndView;
    }

    private String buildFilterURL(Filter filter){
        if(filter == null){
            return "";
        }
        return new StringBuilder().append("?")
                .append("ticketId=").append(filter.getId()).append("&")
                .append("category=").append(filter.getCategory()).append("&")
                .append("prio=").append(filter.getPriority()).append("&")
                .append("body").append(filter.getBody()).toString();
    }


}
