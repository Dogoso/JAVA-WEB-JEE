import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EmailsServlet extends HttpServlet {

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
			out.print("<title>MailBox</title>");
			out.print("</head>");
			out.print("<body>");
			out.print("<h1>EMAILS:</h1>");
			out.print("<table width='100%'>");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/emails", "root", "");
			
			// Checando se existe alguma Querry String na URL para iniciar o comando DELETE.
			String parameter_id = req.getParameter("id");
			if(parameter_id != null) {
				String SQLDelete = "DELETE FROM letters WHERE id = ?";
				int id = Integer.parseInt(parameter_id);
				PreparedStatement pstm = conn.prepareStatement(SQLDelete);
				pstm.setInt(1, id);
				pstm.execute();
			}
			
			// Nesse caso usamos o STM pois a nossa Querry não terá parâmetros!
			Statement stm = conn.createStatement();
			String SQL = "SELECT * FROM letters";
			
			ResultSet result = stm.executeQuery(SQL);
			
			HttpSession session = req.getSession();
			if(session.getAttribute("ip") != null) {
				out.printf("<p style='color: green'>%s</p>", session.getAttribute("ip"));
			}
			
			out.print("<tr bgcolor='blue' style='color: white'>");
			out.printf("<td><strong>ID</strong></td>");
			out.printf("<td><strong>TITULO</strong></td>");
			out.printf("<td><strong>CONTEUDO</strong></td>");
			out.printf("<td><strong>DATA</strong></td>");
			out.printf("<td><strong>EDITAR</strong></td>");
			out.printf("<td><strong>APAGAR</strong></td>");
			out.print("</tr>");
			while(result.next()) {
				out.print("<tr>");
				out.printf("<td> %d </td>", result.getInt("id"));
				out.printf("<td> %s </td>", result.getString("title"));
				out.printf("<td> %s </td>", result.getString("conteudo"));
				out.printf("<td> %s </td>", result.getDate("data"));
				out.printf("<td> <a href='http://localhost:8080/JEERLSYSTEM/editmail?id=%s'>[EDITAR]</a> </td>",
						result.getInt("id"));
				out.printf("<td> <a href='http://localhost:8080/JEERLSYSTEM/emails?id=%s'>[APAGAR]</a> </td>",
						result.getInt("id"));
				out.print("</tr>");
			}
			out.print("</table>");
			out.print("<br>");
			out.print("<a href='http://localhost:8080/JEERLSYSTEM/index'> &lt;- Home</a>");
			out.print("</body>");
			out.print("</html>");
			
			conn.close();
			stm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
}
