package DAO;


import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
	// handles the 3rd requirement to add a message
    public Message addMessage(Message message){
		Connection connection = ConnectionUtil.getConnection();
		try{
			// validating that the posted_by number is found in the account_id as posted_by is a fk referencing account_id
			String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (SELECT account_id FROM account WHERE account_id= ?, ?, ?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, message.getPosted_by());
			preparedStatement.setString(2, message.getMessage_text());
			preparedStatement.setLong(3, message.getTime_posted_epoch());
           	preparedStatement.executeUpdate();

			
			   ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
			   if(pkeyResultSet.next()){
				   int generated_message_id = (int) pkeyResultSet.getInt("message_id");
				   return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),  message.getTime_posted_epoch());
			   }
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	//handles 5th requirement to get a message by message_id.
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {          
            String sql = "SELECT * FROM message WHERE message_id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message  = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
	// handles 4th requirement to retrieve all messages
    public List<Message> getAllMessages(){
		Connection connection = ConnectionUtil.getConnection();
		List<Message> messages = new ArrayList<>();
		try{
			String sql= "SELECT * FROM message;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message(rs.getInt("message_id"), 
						rs.getInt("posted_by"),
						rs.getString("message_text"),
						rs.getLong("time_posted_epoch"));
				messages.add(message);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return messages;
				
	}
    public Message deleteMessageById(int messageId) {
		Connection connection = ConnectionUtil.getConnection();
		try{
			String sql = "DELETE FROM message WHERE message_id = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, messageId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				Message message = new Message(rs.getInt("message_id"),
						rs.getInt("posted_by"),
						rs.getString("message_text"),
						rs.getLong("time_posted_epoch"));
				return message;
			}
					
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
    }
	public Message updateMessageById(Message message, int messageId) {
		Connection connection = ConnectionUtil.getConnection();
		try{
			String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, message.getMessage_text());
			preparedStatement.setInt(2, messageId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				Message updatedMessage = new Message(rs.getInt("message_id"),
						rs.getInt("posted_by"),
						rs.getString("message_text"),
						rs.getLong("time_posted_epoch"));
				return updatedMessage;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
    public List<Message> getMessagesByAcctId(int accountId) {
		Connection connection = ConnectionUtil.getConnection();
		List<Message> messages = new ArrayList<>();
		try{
			String sql = "SELECT * FROM messages WHERE posted_by = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message(rs.getInt("message_id"), 
						rs.getInt("posted_by"),
						rs.getString("message_text"),
						rs.getLong("time_posted_epoch"));
				messages.add(message);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
        return null;
    
    }
}
