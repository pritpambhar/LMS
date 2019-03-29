import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Register extends HttpServlet 
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		String name=req.getParameter("stname");
		String email=req.getParameter("stemail");
		String password=req.getParameter("stpwd");
		String cpassword=req.getParameter("cstpwd");
		String contact=req.getParameter("stphn");
		if(password.equals(cpassword))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select * from students where student_email='"+email+"'");
				//String s1=rs.getString(3);
				if(rs.next())
				{
					out.print("email address already registered");
					out.print("<a href='login.html'>click here</a> to log in");
				}
				else
				{
				stmt.executeUpdate("insert into students (student_name,student_email,student_contact,student_password) values('"+name+"','"+email+"','"+contact+"','"+password+"')");
				res.sendRedirect("login.html");
				}
				//out.print("successfully registered");
			}
			catch(Exception e)
			{
				//out.print(e.printStackTrace());
				out.print("exception throwed");
			}
		}
		else
		{
			out.print("password and confirm password does not match.");
			out.print("<a href='register.html'>click here</a> to register");
		}
		
	}
	
}