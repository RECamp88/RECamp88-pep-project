package DAO;

import Util.ConnectionUtil;
import Model.Account;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
				return new Account(generated_account_id, account.getUsername(), account.getPassword());
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Account loginAccount(Account account){
		Connection connection = ConnectionUtil.getConnection();
		try{
			String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());

			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
                Account login  = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        );
                return login;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
	}
	
}
