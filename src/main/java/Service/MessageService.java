package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    public MessageDAO messageDAO;

    //constructor with no args
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //constructor with MessageDAO provided
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    //handles 4th requirement to retrieve all messages.
    //getting all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    //handles 3rd requirement to create a new message. 
    //adding a message
    // need to verify that the message is not larger than 255
    // and the posted by is valid (this constraint is handled in the DAO)
    public Message addMessage(Message message){
        if(message.message_text.length() < 256){
            return messageDAO.addMessage(message);
        }
        return null;        
    }
    // handles 5th requiment to get a message by its id
    //retrieving message by id
    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }
   
}
