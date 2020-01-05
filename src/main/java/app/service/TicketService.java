package app.service;

import app.model.*;
import app.model.mapper.TicketMapper;
import app.model.names.ModelNames;
import app.repository.TicketRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.Set;

@Service("ticketService")
public class TicketService {

    private TicketRepository ticketRepository;


    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;

    }

    public Ticket findTicketById(Integer id) {
        return ticketRepository.findTicketById(id);
    }


    private Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }


    private boolean isAnyFilterNotEmpty(Filter filter) {
        return StringUtils.isNotBlank(filter.getId()) ||
                StringUtils.isNotBlank(filter.getCategory()) ||
                StringUtils.isNotBlank(filter.getPriority()) ||
                StringUtils.isNotBlank(filter.getBody());
    }

    public Page<Ticket> findPaginated(Pageable pageable, Users user, Filter filter) {
        BooleanExpression filterExpression;
        QTicket ticketFiltered = QTicket.ticket;
        UserType regularUserType = new UserType(2, "USER");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        if (isAnyFilterNotEmpty(filter)) {
            filterExpression = ticketFiltered.category.eq(filter.getCategory())
                    .or(ticketFiltered.prioId.prio.eq(filter.getPriority()))
                    .or(ticketFiltered.ticketBody.matches(filter.getBody()))
                    .or(ticketFiltered.title.matches(filter.getBody()));

            if (StringUtils.isNotBlank(filter.getId())) {
                filterExpression = filterExpression.or(ticketFiltered.ticketId.eq(Integer.valueOf(filter.getId())));
            }
            if (user != null && user.getRoles().contains(regularUserType)) {
                filterExpression = filterExpression.or(ticketFiltered.requestorId.roles.contains(regularUserType));
            }

            return ticketRepository.findAll(filterExpression, pageRequest);

        } else {
            return ticketRepository.findAll(pageRequest);
        }

    }


    public Set<String> findAllCategories() {
        return ticketRepository.findAllCategories();
    }

    public boolean checkId(ModelAndView modelAndView, @PathVariable Integer id) {
        if (id == null) {
            modelAndView.setViewName(ModelNames.ACCESS_DENIED);
            return true;
        }
        return false;
    }


    public void editTicket(Ticket editedTicketData, Users user, Integer id) {
        Optional.ofNullable(editedTicketData).ifPresent(ticket -> {
            Ticket currentTicket = findTicketById(id);
            TicketMapper.mapTicket(currentTicket, ticket, user);

            saveTicket(currentTicket);


        });
    }


    public void editTicket(Ticket editedTicketData, Integer id) {
        Optional.ofNullable(editedTicketData).ifPresent(ticket -> {
            Ticket currentTicket = findTicketById(id);
            TicketMapper.mapTicket(currentTicket, ticket);

            saveTicket(currentTicket);


        });
    }


    public void createTicket(Ticket ticketData, Users user) {
        Optional.ofNullable(ticketData).ifPresent(ticket ->
                saveTicket(TicketMapper.mapNewTicket(ticket, user))
        );
    }
}
