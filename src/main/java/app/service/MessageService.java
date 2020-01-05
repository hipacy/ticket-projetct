package app.service;

import app.model.Message;
import app.model.Ticket;
import app.model.Users;
import app.model.mapper.MessageMapper;
import app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//import app.model.Users;
//import app.model.mapper.MessageMapper;

@Service("messageService")
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;

    }

    public List<Message> findMessagesByTicketId(Ticket ticket) {
        return messageRepository.findMessageByTicketId(ticket);
    }


    private void saveMessage(Message message) {
        messageRepository.save(message);
    }


    public void createNewMessage(Message message, Users user, Ticket ticket) {
        saveMessage(MessageMapper.mapNewMessage(message, user, ticket));
    }


}