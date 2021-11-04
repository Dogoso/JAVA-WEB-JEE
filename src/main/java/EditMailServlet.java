import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditMailServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		if(req.getSession().getAttribute("user") == null) {
			try {
				res.sendRedirect("http://localhost:8080/JEERLSYSTEM/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			PrintWriter out = res.getWriter();
			out.print("<html>");
			out.print("<head>");
			out.print("<title>Edit Mail</title>");
			out.print("</head>");
			out.print("<body>");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			Connection conn = null;
			PreparedStatement pstm = null;
			String SQL = "SELECT * FROM letters WHERE id = ?";
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, id);
				ResultSet result = pstm.executeQuery();
				if(result.next()) {
					String textArea = result.getString("conteudo"), title = result.getString("title");
					out.print("<h1>EDICAO</h1>");
					out.print("<hr>");
					out.print("<form method='POST'>");
					out.printf("<strong>Id:</strong> <br> <input type='text' readonly='readonly' value='%d' name='iptid'> <br>",
							id);
					out.printf("<strong>Titulo:</strong> <br> <input type='text' value='%s' name='ipttext'> <br>",
							title);
					out.printf("<strong>Conteudo:</strong> <br> <textarea name='textcont' rows='10' cols='50'>%s</textarea>",
							textArea);
					out.print("<br><input type='submit' value='Atualizar email'>");
					out.print("</form>");
					out.print("<br>");
					out.print("<a href='http://localhost:8080/JEERLSYSTEM/emails'> &lt;- Emails</a>");
				}else {
					out.print("<h1>EMAIL NAO ENCONTRADO</h1>");
					out.print("<a href='http://localhost:8080/JEERLSYSTEM/emails'> &lt;- Emails</a>");
					out.print("</body>");
					out.print("</html>");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		int id = Integer.parseInt(req.getParameter("iptid"));
		String title = req.getParameter("ipttext");
		String text = req.getParameter("textcont");
		
		String SQL = "UPDATE letters SET title = ?, conteudo = ? WHERE id = ?";
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, title);
			pstm.setString(2, text);
			pstm.setInt(3, id);
			pstm.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			try {
				conn.close();
				pstm.close();
			} catch (SQLException e) {
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
