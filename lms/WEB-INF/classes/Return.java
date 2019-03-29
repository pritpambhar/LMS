import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Return extends HttpServlet 
{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int bid=Integer.parseInt(req.getParameter("book_id"));
		int isid=Integer.parseInt(req.getParameter("issue_id"));
		String state=req.getParameter("state");
		try
		{
				HttpSession session=req.getSession();
				int i=(int)session.getAttribute("student_id");
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
				Statement stmt=con.createStatement();
			if(state.equals("r"))
			{		
				//stmt.executeUpdate("delete from issue where issue_id='"+isid+"'");
				/*ResultSet rs=stmt.executeQuery("select * from issue where issue_id='"+isid+"'");
				while(rs.next())
				{
					//Date dn=new Date();
					Date d=rs.getDate(2);
					out.print(d);
					//out.print(dn);
					if()
					{
						out.print("you have to pay this much fine");
					}
					
				}*/
				stmt.executeUpdate("update issue set status='returned' where issue_id='"+isid+"'");
				stmt.executeUpdate("update book set book_qty=book_qty + 1 where book_id='"+bid+"'");
				out.print("succesfully returned");	
			}
			else
			{
				ResultSet rs=stmt.executeQuery("select * from book where book_id='"+bid+"'");
				while(rs.next())
				{
					float fine=rs.getFloat(8);
					String bname=rs.getString(2);
					out.print("you have to pay fine of Rs. "+fine+"/- foe the loss of "+bname);
					stmt.executeUpdate("update issue set status='lost' where issue_id='"+isid+"'");
				}
				
				
			}
			//out.print(bid);
			//out.print("hello");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}