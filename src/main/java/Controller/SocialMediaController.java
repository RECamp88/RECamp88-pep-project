package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
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
        app.post("localhost:8080/register", this::postRegisterHandler);
        app.post("localhost:8080/login", this::postAccountLoginHandler);
        app.post("localhost:8080/messages", this::postMessageHandler);
        app.get("localhost:8080/messages/{message_id}", this::getAllMessagesHandler);
        app.get("localhost:8080/messages/{message_id}", this::getMessageByIdHandler);
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
    private void getMessageByIdHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message messageId = messageService.getMessageById(message.getMessage_id());

        context.json(messageId);
    }
}