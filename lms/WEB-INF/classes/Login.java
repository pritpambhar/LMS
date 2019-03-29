import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Login extends HttpServlet 
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		//out.print("hello");
		String email=req.getParameter("stemail");
		String password=req.getParameter("stpwd");
		try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select * from students where student_email='"+email+"' and student_password='"+password+"'");
				if(rs.next())
				{
					HttpSession session=req.getSession();  
					int i=rs.getInt(1);
					//out.print(i);
					session.setAttribute("student_id",i);
					res.sendRedirect("home.html");
					//out.print("succesfully logged in");
				}
				else
				{
					out.print("registration required.");
					out.print("<a href='register.html'>click here</a> to register");
				}
				//res.sendRedirect("login.html");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				out.print(e);
			}
	}
}