
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			PrintWriter out = res.getWriter();
			out.print("<html>");
			out.print("<head>");
			out.print("<title>Home</title>");
			out.print("</head>");
			out.print("<body>");
			out.print("<h1>MUITO BEM VINDO AMIGO!</h1>");
			out.print("<hr>");
			out.print("<p>Navegue pela pagina:</p>");
			out.print("<a href='http://localhost:8080/JEERLSYSTEM/newemail'>Enviar email</a> <br>");
			out.print("<a href='http://localhost:8080/JEERLSYSTEM/emails'>Ver emails</a> <br>");
			out.print("<a href='http://localhost:8080/JEERLSYSTEM/logoff'>Sair</a> <br>");
			out.print("</body>");
			out.print("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
}