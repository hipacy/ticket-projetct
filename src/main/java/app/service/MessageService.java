package app.service;

import app.model.Message;
import app.model.Ticket;
import app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public void saveMessage(Message message){
        messageRepository.save(message);
    }
//    public List<Message> findAllMessages() {
//        return messageRepository.findAllItems();
//    }
//
//    public List<Message> findItemByType(String type) {
//        return messageRepository.findItemByType(type);
//    }

}