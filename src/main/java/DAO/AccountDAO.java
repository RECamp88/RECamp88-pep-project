package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    

    public Account addAccount(Account account){
		Connection connection = ConnectionUtil.getConnection();
		try{
			String sql = "INSERT INTO account (username, password) VALUES (?, ?) WHERE LEN(password) > 3;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.executeUpdate();

			ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
			if(pkeyResultSet.next()){
				int generated_account_id = (int) pkeyResultSet.getLong(1);
				return new Account(generated_account_id, account.getUsername());
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<Message> getAllMessages(){
		Connection connection = ConnectionUtil.getConnection();
		List<Message> messages = new ArrayList<>();
		try{
			String sql= "SELECT * FROM message;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message(re.getInt("message_id"), 
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
