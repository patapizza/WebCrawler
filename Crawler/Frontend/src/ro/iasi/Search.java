package ro.iasi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Fetch the data from database using the cosine similarity algorhitm
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
 
		    response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        out.println("<html>");
	        out.println("<head>");

		    out.println("<title>" + "</title>");
	        out.println("</head>");
	        out.println("<body bgcolor=\"white\">");


		    out.println("<a href=\"../helloworld.html\">");
	        out.println("<img src=\"../images/code.gif\" height=24 " +
	                    "width=24 align=right border=0 alt=\"view code\"></a>");
	        out.println("<a href=\"../index.html\">");
	        out.println("<img src=\"../images/return.gif\" height=24 " +
	                    "width=24 align=right border=0 alt=\"return\"></a>");
	        out.println("<h1>" + "</h1>");
	        out.println("</body>");
	        out.println("</html>");		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}