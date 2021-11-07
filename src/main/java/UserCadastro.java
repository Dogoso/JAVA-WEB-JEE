import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserCadastro extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		if(req.getParameter("txtUser") != null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			Connection conn = null;
			PreparedStatement pstm = null;
			
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
				String SQL = "INSERT INTO users (user, password) VALUES (?, ?)";
				pstm = conn.prepareStatement(SQL);
				pstm.setString(1, req.getParameter("txtUser"));
				pstm.setString(2, req.getParameter("txtPassword"));
				pstm.execute();
				
				HttpSession hSession = req.getSession();
				hSession.setAttribute("user", req.getParameter("txtUser"));
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
					pstm.close();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			try {
				res.sendRedirect("http://localhost:8080/JEERLSYSTEM/emails");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
