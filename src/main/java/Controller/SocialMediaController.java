package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();       

    }
   
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}", this::getMessagesByAcctIDHandler);
        return app;
    }
    
    // handles the first requirement of creating a new account
    private void postRegisterHandler(Context context) throws JsonProcessingException {
        
		ObjectMapper mapper = new ObjectMapper();
		Account account = mapper.readValue(context.body(), Account.class);
		Account addedAccount = accountService.addAccount(account);
		if(addedAccount!=null){
			context.json(mapper.writeValueAsString(addedAccount));
		}else {
			context.status(400);
		
        }
    }
    // handles the 2nd requirement of validating a login
    private void postAccountLoginHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount!=null){
            context.json(mapper.writeValueAsString(loginAccount));

        }else{
            context.status(401);
        }

    }
    // handles the 3rd requirement of adding a message
    private void postMessageHandler(Context context) throws JsonProcessingException {
        
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(context.body(), Message.class);
		Message addedMessage = messageService.addMessage(message);
		if(addedMessage!=null){
			context.json(mapper.writeValueAsString(addedMessage));
		}else {
			context.status(400);
        }
    }
    // handles the 4th requirement of retrieving all messages
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);

    }
    // handles the 5th requirement of gettinga message by its id
    private void getMessageByIdHandler(Context context) {
       int messageId = Integer.parseInt(context.pathParam("message_id"));        
       context.json(messageService.getMessageById(messageId));     
       
    }
    // handles the 6th requirement of deleting a message by its id
    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));        
        context.json(messageService.deleteMessageById(messageId));     
        
     }
    // handles the 7th request to update a message by its id
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
              
        Message message = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updateMessage = messageService.updateMessageById(message, messageId);
        if(updateMessage != null){
            context.json(messageService.updateMessageById(updateMessage,messageId));
        }else{
            context.status(400);
        } 
    }   
     private void getMessagesByAcctIDHandler(Context context){
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAcctId(accountId);
        context.json(messages);
    }


      
}