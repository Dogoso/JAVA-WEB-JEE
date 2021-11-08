import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
			PreparedStatement pstm2 = null;
			
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
				String SQL = "INSERT INTO users (user, password) VALUES (?, ?)";
				String SQL2 = "SELECT * FROM users WHERE user = ?";
				pstm = conn.prepareStatement(SQL2);
				pstm.setString(1, req.getParameter("txtUser"));
				ResultSet rset = pstm.executeQuery();
				if(!rset.next()) {
					pstm2 = conn.prepareStatement(SQL);
					pstm2.setString(1, req.getParameter("txtUser"));
					pstm2.setString(2, req.getParameter("txtPassword"));
					pstm2.execute();
					HttpSession hSession = req.getSession();
					hSession.setAttribute("user", req.getParameter("txtUser"));
				}else {
					try {
						res.sendRedirect("http://localhost:8080/JEERLSYSTEM/UserCadastro.jsp?msg=usererror");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				res.sendRedirect("http://localhost:8080/JEERLSYSTEM/emails");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
