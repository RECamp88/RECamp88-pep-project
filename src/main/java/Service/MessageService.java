package Service;

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

    //getting all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    //adding a message
    public Message addMessage(Message message){
        if(message.getMessageById(message.getMessage_id())==null){
            return messageDAO.addMessage(message);
        }
        return null;        
    }
}
