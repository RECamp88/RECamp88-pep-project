package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("localhost:8080/register", this::postRegisterHandler);
        app.post("localhost:8080/login", this::postAccountLoginHandler);
        app.post("localhost:8080/messages", this::postMessageHandler);
        app.get("localhost:8080/messages/{message_id}", this::getAllMessagesHandler);
        return app;
    }
    

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    // handles the first requirement of creating a new account
    private void postRegisterHandler(Context context) throws JsonProcessingException {
        
		ObjectMapper mapper = new ObjectMapper();
		Account account = mapper.readValue(context.body(), Account.class);
		Account addedAccount = AccountService.addAccount(account);
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
		Message addedMessage = MessageService.addMessage(message);
		if(addedMessage!=null){
			context.json(mapper.writeValueAsString(addedMessage));
		}else {
			context.status(400);
        }
    }
}