import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			PrintWriter out = res.getWriter();
			out.print("<html>");
			out.print("<head>");
			out.print("<title>Login</title>");
			out.print("</head>");
			out.print("<body>");
			out.print("<h1>LOGIN DE USUARIO!</h1>");
			out.print("<hr>");
			
			if(req.getParameter("msg") != null) {
				out.print("<p style='color: red'>Usuario e/ou senha incorreto!</p>");
			}
			
			String user = "", password = "";
			Cookie[] cookies = req.getCookies();
			for(Cookie c : cookies) {
				if(c.getName().equals("user")) {
					user = c.getValue();
				}else if(c.getName().equals("password")) {
					password = c.getValue();
				}
			}
			
			out.print("<form method='POST'>");
			out.printf("<strong>Usuario:</strong> <br> <input type='text' value='%s' name='iptUser'> <br>",
					user);
			out.printf("<strong>Senha:</strong> <br> <input type='password' value='%s' name='iptPassword'>",
					password);
			out.print("<br><input type='submit' value='Iniciar Sessao'>");
			out.print("</form>");
			out.print("<br>");
			out.print("<a href='http://localhost:8080/JEERLSYSTEM/index'> &lt;- Home</a>");
			out.print("</body>");
			out.print("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		String user = req.getParameter("iptUser");
		String password = req.getParameter("iptPassword");
		
		Cookie cookie = new Cookie("user", user);
		Cookie cookie2 = new Cookie("password", password);
		cookie.setMaxAge(60*60*24*30);
		cookie2.setMaxAge(60*60*24);
		res.addCookie(cookie);
		res.addCookie(cookie2);
		
		String SQL = "SELECT * FROM users WHERE user = ? and password = ?";
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, user);
			pstm.setString(2, password);
			ResultSet rset = pstm.executeQuery();
			
			if(rset.next()) {
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
				session.setAttribute("ip", req.getRemoteAddr());
				try {
					res.sendRedirect("http://localhost:8080/JEERLSYSTEM/emails");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				try {
					res.sendRedirect("http://localhost:8080/JEERLSYSTEM/login?msg=error");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
