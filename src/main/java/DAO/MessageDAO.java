package DAO;

import Model.Account;
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
			String sql = "INSERT INTO messages (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?) WHERE LEN(message_text) < 256;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, message.getPosted_by());
			preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
			preparedStatement.executeUpdate();

			ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
			if(pkeyResultSet.next()){
				int generated_message_id = (int) pkeyResultSet.getInt(1);
				return new Message(generated_message_id, message.getMessage_text(), message.getTime_posted_epoch());
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
                        rs.getLong("time_posted_epach"));
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
}
