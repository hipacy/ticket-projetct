package app.model.mapper;

import app.model.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TicketMapper {

    public static void mapTicket(Ticket current, Ticket edited, Users user)
    {
        if(current != null && edited != null)
        {
         mapTicket(current, edited);
         current.setRequestorId(user);
        }
    }

    public static Ticket mapNewTicket(Ticket current, Users user)
    {
        if(current != null)
        {
         current.setRequestorId(user);
         current.setCreationDate(LocalDateTime.now());
         current.setStateId(new StateOfTicket(1, "New"));

         return current;
        } else {
            return null;
        }
    }

    public static void mapTicket(Ticket current, Ticket edited)
    {
        if (edited.getPrioId() != null) {
            current.setPrioId(edited.getPrioId());
        }

        List<Item> list = edited.getItems();
        if (list != null) {
            current.setItems(new ArrayList(list));
        }

        List<Team> list1 = edited.getAssignedTeams();
        if (list1 != null) {
            current.setAssignedTeams(new ArrayList(list1));
        }

        if (edited.getCategory() != null) {
            current.setCategory(edited.getCategory());
        }

        if (edited.getAssignedTechnicianId() != null) {
            current.setAssignedTechnicianId(edited.getAssignedTechnicianId());
        }

        if (edited.getTitle() != null) {
            current.setTitle(edited.getTitle());
        }

        if (edited.getTicketBody() != null) {
            current.setTicketBody(edited.getTicketBody());
        }

        if (edited.getStateId() != null) {
            current.setStateId(edited.getStateId());
        }

        if (edited.getApprovalNeeded() != null) {
            current.setApprovalNeeded(edited.getApprovalNeeded());
        }

        if (edited.getRequestorId() != null) {
            current.setRequestorId(edited.getRequestorId());
        }

        if (edited.getCreationDate() != null) {
            current.setCreationDate(edited.getCreationDate());
        }

        current.setLastModifiedDate(LocalDateTime.now());
    }
}
