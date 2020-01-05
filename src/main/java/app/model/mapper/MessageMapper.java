package app.model.mapper;



import app.model.Message;
import app.model.Ticket;
import app.model.UserType;
import app.model.Users;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class MessageMapper {


    public static Message mapNewMessage(Message message, Users user, Ticket ticket) {
        if (message == null) {
            return null;
        }
        message.setIsTechnical(checkTechnicalUser(ticket));
        message.setTicketId(ticket);
        message.setDateTimeCreated(LocalDateTime.now());
        message.setCreator(user);

        return message;
    }

    private static boolean checkTechnicalUser(Ticket ticket) {
        if (ticket == null) {
            return false;
        }

        return !ticket.getRequestorId().getRoles().contains(new UserType(2, "USER"));
    }
}

