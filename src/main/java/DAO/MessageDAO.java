package DAO;

public class MessageDAO {
    

    public Account addMessage(Message message){
		Connection connection = ConnectionUtil.getConnection();
		try{
			String sql = "INSERT INTO messages (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?) WHERE LEN(message_text) < 256;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, message.getPosted_by());
			preparedStatement.setString(2, account.getMessage_text());
            preparedStatement.setString(3, getTime_posted_epoch());
			preparedStatement.executeUpdate();

			ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
			if(pkeyResultSet.next()){
				int generated_message_id = (int) pkeyResultSet.getLong(1);
				return new Message(generated_message_id, message.getMessage_id());
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
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
}
