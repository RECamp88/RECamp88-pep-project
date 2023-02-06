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
    public Message addMessage(Message message){
        if(message.message_text.length() < 256 && message.message_text !=""){
           return messageDAO.addMessage(message);
        }
        return null;        
    }
    // handles 5th requiment to get a message by its id
    //retrieving message by id
    public Message getMessageById(int id){
       
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        messageDAO.deleteMessageById(messageId);
        
        if(message == null){
            return null;
        }
        return message;
    
    }

    public Message updateMessageById(Message message, int messageId) {
        
        if(getMessageById(messageId)==null ||message.message_text == "" || message.message_text.length()>255){
            return null ;
        }
        return messageDAO.updateMessageById(message, messageId);
    }

    public List<Message> getAllMessagesByAcctId(int accountId) {
        return messageDAO.getMessagesByAcctId(accountId);
    }
   
}
