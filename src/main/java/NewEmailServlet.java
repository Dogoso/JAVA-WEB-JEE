import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewEmailServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			PrintWriter out = res.getWriter();
			out.print("<html>");
			out.print("<head>");
			out.print("<title>Email</title>");
			out.print("</head>");
			out.print("<body>");
			out.print("<h1>Nos envie um email!</h1>");
			out.print("<hr>");
			out.print("<form method='POST'>");
			out.print("<strong>Titulo:</strong> <br> <input type='text' name='ipttext'> <br>");
			out.print("<strong>Conteudo:</strong> <br> <textarea name='textcont' rows='10' cols='50'></textarea>");
			out.print("<br><input type='submit' value='Enviar email'>");
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
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String title = req.getParameter("ipttext");
		String text = req.getParameter("textcont");
		if(!title.equals("") || !text.equals("")) {
			try {
				// Carrega a classe Driver para poder ser usada.
				Class.forName("com.mysql.jdbc.Driver"); 
				try {
					String SQL = "INSERT INTO letters(title, conteudo) values (?, ?)";
					Connection conn = 
							DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
					PreparedStatement pstm = conn.prepareStatement(SQL);
					pstm.setString(1, title);
					pstm.setString(2, text);
					pstm.execute();
					pstm.close();
					conn.close();	
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		try {
			res.sendRedirect("http://localhost:8080/JEERLSYSTEM/emails");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
