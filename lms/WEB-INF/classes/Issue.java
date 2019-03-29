import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Issue extends HttpServlet 
{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		//out.print("hello");
		try
		{
			HttpSession session=req.getSession();
			int i=(int)session.getAttribute("student_id");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
			Statement stmt=con.createStatement();
			int a=Integer.parseInt(req.getParameter("book_id"));
			//out.print(a);
			//out.print("hello");
			ResultSet rs=stmt.executeQuery("select * from book where book_id='"+a+"'");
			//out.print("hello1");
			int qty=0;
			while(rs.next())
			{
			qty=rs.getInt(5);
			//out.print(qty);
			}
			if(qty==0)
			{
				out.print("out of stock");
			}
			else
			{
				qty--;
				stmt.executeUpdate("insert into issue (issue_date,book_id,student_id,status) values (now(),'"+a+"','"+i+"','issued')");
				out.print("succesfully added");
				stmt.executeUpdate("update book set book_qty='"+qty+"' where book_id='"+a+"'");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}