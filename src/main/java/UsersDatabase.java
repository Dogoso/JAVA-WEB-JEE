import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsersDatabase extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			PrintWriter out = res.getWriter();
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection conn = null;
			PreparedStatement pstm = null;
			
			try {
				conn = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
				String SQL = "DELETE FROM users WHERE id = ?";
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, id);
				pstm.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			res.sendRedirect("http://localhost:8080/JEERLSYSTEM/UsersDatabase.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
}
