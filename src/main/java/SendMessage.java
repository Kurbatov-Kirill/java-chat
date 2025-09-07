

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.Dialog;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/chat";
    static final String DATABASE_USER = "root";
    static final String DATABASE_PASSWORD = "qwszxder";
    static final String INSERT_NEW_MESSAGE = "INSERT INTO `messages` (`msg_sender_id`, `msg_receiver_id`, `msg_content`, `msg_send_timestamp`) VALUES (?, ?, ?, ?);";

    public SendMessage() {
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		int user_id = (int) session.getAttribute("current_user_id");
		Dialog currentDialog = (Dialog) session.getAttribute("currentDialog");
		int msgReceiverId = currentDialog.getCurrentDialogCompanionId();
		String msgContent = (String) request.getParameter("message_body");
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    	
		try(Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    			Statement stmt = conn.createStatement();
				) {
			LocalDateTime currentDate = LocalDateTime.now();
			String sql_insert = INSERT_NEW_MESSAGE;
			
            PreparedStatement preparedStatementInsert = conn.prepareStatement(sql_insert);
            preparedStatementInsert.setInt(1, user_id);
            preparedStatementInsert.setInt(2, msgReceiverId);
            preparedStatementInsert.setString(3, msgContent);
            preparedStatementInsert.setObject(4, currentDate);
             
            preparedStatementInsert.executeUpdate();
		} catch (SQLException e) {
  			e.printStackTrace();
		}
		doGet(request, response);
	}

}
