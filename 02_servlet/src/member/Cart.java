package member;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*; //Connection/ PreparedStatmemt
import java.util.Date;
import java.text.SimpleDateFormat;


@WebServlet("/Cart")
public class Cart extends HttpServlet {
	
	String DRIVER = "com.mysql.jdbc.Driver"; //MySql제공
	String URL = "jdbc:mysql://localhost:3306/mydb2";
	String USER = "root";
	String PWD = "12345";
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	java.util.Date date = new java.util.Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:mm:ss EEEE");
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); //out 객체 생성
		
		try {
			Class.forName(DRIVER); //드라이버 로딩
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException cnf) {
			System.out.println("드라이버 로딩 실패 : " + cnf);
		}
		
		try {
			con = DriverManager.getConnection(URL,USER,PWD);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from sangpum");
			
			out.println("<html><body><br><br>");
			out.println("<center><h2>상품목록리스트</h2></center>");
			
			out.println("<table align=center width=70% border=1>");
			
			while(rs.next()){
				String title = rs.getString("title");
				String imgfile = rs.getString("imgfile");
				String imghtml = rs.getString("imghtml");
				String content = rs.getString("content");
				
				out.println("<tr><td>상품명</td>");
				out.println("<td align=center>"+title+"</td></tr>");
				
				out.println("<tr><td colspan=2 align=center>");
				out.println("<a href='/02_servlet/member/aa.jsp?imgfile="+imgfile+"&title="+title+"&content="+content+"'>");
				out.println("<img src=/02_servlet/imgs/"+imgfile+" width=100 height=100>");
				out.println("</a></td></tr>");
				
				out.println("<tr><td>설명</td>");
				out.println("<td>"+content+"</td></tr>");
				
				out.println("<tr><td colspan=2 align=center>");
				out.println(sdf.format(date)+"</td></tr>");
				
			}//while-end
			out.println("</table></body></html>");
			out.close();
			
		} catch (SQLException se) {
			System.out.println("SQL예외 : " + se);
		}finally{
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {stmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e) {}
		}//finally-end
		
	}//doGet() - end

}//class()-end
