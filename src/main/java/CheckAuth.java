

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Servlet implementation class CheckAuth
 */
@WebServlet("/CheckAuth")
public class CheckAuth extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/chat";
    static final String DATABASE_USER = "root";
    static final String DATABASE_PASSWORD = "qwszxder";
    static final String CHECK_IF_USER_EXISTS = "SELECT * FROM `users` WHERE `username` == ?;";
    static final String INSERT_NEW_USER = "INSERT INTO `users` (`username`, `password`, `registration_date`, `last_seen_online`) VALUES (?, ?, ?, ?);";
	
	private static final long serialVersionUID = 1L;

    public CheckAuth() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String user = (String)session.getAttribute("current_user");
		
		if (user == null) {
			// anonymous
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// logged in
			getServletContext().getRequestDispatcher("/chatlist.jsp").forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String action = request.getParameter("action");
		
		if ("Signup".equals(action)) {
			try {
				try {
					Class.forName(JDBC_DRIVER);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				Connection myConn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
				
				Statement statement = myConn.createStatement();

				int versionNo = 0;
				
				try (ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `username` = '" + login + "';")) {
				    versionNo = 0;
				    while (res.next()) {
				        versionNo = res.getInt(1);
				    }
				}
				
				if (versionNo == 0) {
					LocalDateTime currentDate = LocalDateTime.now();
					String sql_insert = INSERT_NEW_USER;

	                PreparedStatement preparedStatementInsert = myConn.prepareStatement(sql_insert);
	                preparedStatementInsert.setString(1, login);
	                preparedStatementInsert.setString(2, password);
	                preparedStatementInsert.setObject(3, currentDate);
	                preparedStatementInsert.setObject(4, currentDate);
	                 
	                preparedStatementInsert.executeUpdate();
				} else {
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

            
        } else if ("Login".equals(action)) {
        	HttpSession session = request.getSession();
        	
        	try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        	
        	try(Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        	         Statement stmt = conn.createStatement();
        	         ResultSet rs = stmt.executeQuery("SELECT * FROM `users` WHERE `username` = '" + login + "';");
        	      ) {		      
        	         while(rs.next()){
    	        	 	if (password.equals(rs.getString("password"))){
    	        	 		session.setAttribute("current_user_id", rs.getInt("id"));
    	        	 		session.setAttribute("current_user", login);
    	        	 	}
        	         }
        	      } catch (SQLException e) {
        	         e.printStackTrace();
        	      }
        	getServletContext().getRequestDispatcher("/chatlist.jsp").forward(request, response);
        }
	}

}
