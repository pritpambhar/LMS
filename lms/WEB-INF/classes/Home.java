import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Home extends HttpServlet 
{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		HttpSession session=req.getSession();
		int i=(int)session.getAttribute("student_id");
		String func=req.getParameter("func");
		if(func.equals("i"))
		{
				//res.sendRedirect("Issue.java");
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from book");
					out.print("<table border='1'>");
					out.print("<tr>");
					out.print("<td><b>book_id</b></td>");
					out.print("<td><b>book_title</b></td>");
					out.print("<td><b>subject_id</b></td>");
					out.print("<td><b>author_id</b></td>");
					out.print("<td><b>book_qty</b></td>");
					out.print("<td><b>book_cost</b></td>");
					out.print("<td><b>book_fine_day</b></td>");
					out.print("<td><b>book_fine_lost</b></td>");
					out.print("<td><b>issue</b></td>");
					out.print("</tr>");
					while(rs.next())
					{
						out.print("<tr>");
						out.print("<td>"+rs.getInt(1)+"</td>");
						out.print("<td>"+rs.getString(2)+"</td>");
						out.print("<td>"+rs.getInt(3)+"</td>");
						out.print("<td>"+rs.getInt(4)+"</td>");
						out.print("<td>"+rs.getInt(5)+"</td>");
						out.print("<td>"+rs.getFloat(6)+"</td>");
						out.print("<td>"+rs.getFloat(7)+"</td>");
						out.print("<td>"+rs.getFloat(8)+"</td>");
						out.print("<td><a href='issue?book_id="+rs.getInt(1)+"'>issue</a></td>");
						out.print("</tr>");
						//res.sendRedirect("home.html");
						//out.print("succesfully logged in");
					}
					out.print("</table>");
				}
				catch(Exception e)
				{
				}
		}
		else if(func.equals("r"))
		{
			try
			{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","");
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from issue where student_id='"+i+"' and status='issued'");
					out.print("<table border='1'>");
					out.print("<tr>");
					out.print("<td><b>book_id</b></td>");
					out.print("<td><b>book_title</b></td>");
					/*out.print("<td><b>subject_id</b></td>");
					out.print("<td><b>author_id</b></td>");
					out.print("<td><b>book_qty</b></td>");
					out.print("<td><b>book_cost</b></td>");
					out.print("<td><b>book_fine_day</b></td>");
					out.print("<td><b>book_fine_lost</b></td>");*/
					out.print("<td><b>return</b></td>");
					out.print("<td><b>lost</b></td>");
					out.print("</tr>");
					while(rs.next())
					{
						int bid=rs.getInt(3);
						//out.print(bid);
						Statement stmt1=con.createStatement();
						ResultSet rs1=stmt1.executeQuery("select * from book where book_id='"+bid+"'");
						//out.print("startest");
						while(rs1.next())
						{
							//out.print("start");
							out.print("<tr>");
							out.print("<td>"+rs1.getInt(1)+"</td>");
							out.print("<td>"+rs1.getString(2)+"</td>");
							/*out.print("<td>"+rs1.getInt(3)+"</td>");
							out.print("<td>"+rs1.getInt(4)+"</td>");
							out.print("<td>"+rs1.getInt(5)+"</td>");
							out.print("<td>"+rs1.getFloat(6)+"</td>");
							out.print("<td>"+rs1.getFloat(7)+"</td>");
							out.print("<td>"+rs1.getFloat(8)+"</td>");*/
							out.print("<td><a href='return?book_id="+rs1.getInt(1)+"&issue_id="+rs.getInt(1)+"&state=r'>return</a></td>");
							out.print("<td><a href='return?book_id="+rs1.getInt(1)+"&issue_id="+rs.getInt(1)+"&state=l'>lost</a></td>");
							out.print("</tr>");
							//out.print("end");
						}
						//out.print("endest");
					}
					

			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
	}
	
}