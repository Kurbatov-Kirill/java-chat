package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chat {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/chat";
    static final String DATABASE_USER = "root";
    static final String DATABASE_PASSWORD = "qwszxder";
    static final String SELECT_ALL_MESSAGES = "SELECT * FROM `users` WHERE `username` == ?;";
    static final String INSERT_NEW_MESSAGE = "INSERT INTO `messages` (`msg_sender_id`, `msg_receiver_id`, `msg_content`, `msg_send_timestamp`) VALUES (?, ?, ?, ?);";
    static final String GET_DIALOGUES_INFO = "SELECT `username` FROM `users` WHERE `id` IN (?);";
	
	private int user_id;
	private String user_name;
	private LocalDateTime user_reg_date;
	private LocalDateTime last_seen;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public LocalDateTime getUser_reg_date() {
		return user_reg_date;
	}
	public void setUser_reg_date(LocalDateTime user_reg_date) {
		this.user_reg_date = user_reg_date;
	}
	public LocalDateTime getLast_seen() {
		return last_seen;
	}
	public void setLast_seen(LocalDateTime last_seen) {
		this.last_seen = last_seen;
	}
	
	public ArrayList<Message> getMessages() {
		ArrayList<Message> messagesList = new ArrayList<>();
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    	
		try(Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    			Statement stmt = conn.createStatement();
    			ResultSet rs = stmt.executeQuery("SELECT * FROM `messages` WHERE `msg_sender_id` = '" + user_id + "' OR `msg_receiver_id` = '" + user_id + "';");
				) {		      
	         	while(rs.next()){
	         			messagesList.add(new Message());
		                
		         		messagesList.get(messagesList.size() - 1).setMessageId(rs.getInt(1));
		         		messagesList.get(messagesList.size() - 1).setMessageSenderId(rs.getInt(2));
		         		messagesList.get(messagesList.size() - 1).setMessageReceiverId(rs.getInt(3));
		         		messagesList.get(messagesList.size() - 1).setMessageContent(rs.getString(4));
		         		messagesList.get(messagesList.size() - 1).setMessageSendTimestamp(rs.getTimestamp(5).toLocalDateTime());
		         }
		} catch (SQLException e) {
  			e.printStackTrace();
		}
		
		return messagesList;
	}
	
	public List<ArrayList<Object>> getDialogs(ArrayList<Message> messages) {
		
		String output = "";
		ArrayList<Message> messagesList = messages;
		List<ArrayList<Object>> arr_main=new ArrayList<ArrayList<Object>>();
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    	
		try(Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
				) {	
	         	
	         	Set<Integer> set = new HashSet<>();
	    		List<Object> dialogues = new ArrayList<Object>();
	    		
	    		for (int i = 0; i < messagesList.size(); i++) {
	    			if (set.add(messagesList.get(i).getMessageSenderId()) && (messagesList.get(i).getMessageSenderId() != user_id) ) {
	    				dialogues.add(messagesList.get(i).getMessageSenderId());
	    			  }
	    			
	    			if (set.add(messagesList.get(i).getMessageReceiverId()) && (messagesList.get(i).getMessageReceiverId() != user_id) ) {
	    				dialogues.add(messagesList.get(i).getMessageReceiverId());
	    			  }
	    		}
	    		
	    		String sql_part = "";
	    		
	    		for(int i = 0; i < dialogues.size(); i++) {
	    			if (i == dialogues.size() - 1) {
	    				sql_part = sql_part + dialogues.get(i);
	    			}
	    			else sql_part = sql_part + dialogues.get(i) + ", ";
	    		}
	    		
	    		Statement stmt = conn.createStatement();
	    		ResultSet rs_2 = stmt.executeQuery("SELECT * FROM `users` WHERE `id` IN (" + sql_part  +");");
	    		
	    		while(rs_2.next()){
	    			ArrayList<Object> arr=new ArrayList<Object>();
	    			arr.add(rs_2.getInt(1));
	    			arr.add(rs_2.getString(2));
	         		output += "<p><a href=\"/TestTomcat/dialog?uid=" + rs_2.getInt(1) + "\" name=\"" + rs_2.getString(2) + "\">" + rs_2.getString(2) + "</a></p>";
	         		arr_main.add(arr);
		         }
	         	
  		} catch (SQLException e) {
	      			e.printStackTrace();
  		}
		return arr_main;
	}
	
	public String showDialogs(List<ArrayList<Object>> dialogs) {
		
		String output = "";
		
		for (int i = 0; i < dialogs.size(); i++) {
			output += "<p><a href=\"/TestTomcat/dialog?uid=" + dialogs.get(i).get(0) + "\" name=\"" + dialogs.get(i).get(1) + "\">Chat with " + dialogs.get(i).get(1) + "</a></p>";
		}
		
		return output;
	}
	
	public ArrayList<Object> getCurrentChatMessages(int otherUserId) {
		
		ArrayList<Message> messagesList = new ArrayList<>();
		ArrayList<Object> output = new ArrayList<Object>();
		String otherUserIdName = null;
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    	
		try(Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    			Statement stmt = conn.createStatement();
    			ResultSet rs = stmt.executeQuery("SELECT * FROM `messages` WHERE `msg_sender_id` = " + user_id + " AND `msg_receiver_id` = " + otherUserId + " OR `msg_receiver_id` = " + user_id + " AND `msg_sender_id` = " + otherUserId + ";");
				) {		      
				
	         	while(rs.next()){
	         			messagesList.add(new Message());
		         		messagesList.get(messagesList.size() - 1).setMessageId(rs.getInt(1));
		         		messagesList.get(messagesList.size() - 1).setMessageSenderId(rs.getInt(2));
		         		messagesList.get(messagesList.size() - 1).setMessageReceiverId(rs.getInt(3));
		         		messagesList.get(messagesList.size() - 1).setMessageContent(rs.getString(4));
		         		messagesList.get(messagesList.size() - 1).setMessageSendTimestamp(rs.getTimestamp(5).toLocalDateTime());
		         }
	         	
	         	ResultSet rs_2 = stmt.executeQuery("SELECT `username` FROM `users` WHERE `id` = '" + otherUserId + "';");
	         	
	         	while(rs_2.next()){
	         		otherUserIdName = rs_2.getString(1);
		         }
		} catch (SQLException e) {
  			e.printStackTrace();
		}
		
		output.add(otherUserIdName);
		output.add(messagesList);
		
		return output;
	}
	
	public String displayChatMessages(ArrayList<Message> chatMessages) {
		
		String output = "";
		
		for (int i = 0; i < chatMessages.size(); i++) {
			if(chatMessages.get(i).getMessageSenderId() == user_id) {
				output += "<p style=\"color: blue\">" + chatMessages.get(i).getMessageContent() + "</p>";
			}
			else {
				output += "<p style=\"color: red\">" + chatMessages.get(i).getMessageContent() + "</p>";
			}
		}
		
		return output;
	}
	
}
