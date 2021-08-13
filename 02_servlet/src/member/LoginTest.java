package member;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
 
@WebServlet("/LoginTest")
public class LoginTest extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//변수

		String DRIVER="com.mysql.jdbc.Driver";
        String URL="jdbc:mysql://localhost:3306/mydb2"; // 3306 포트 자바 mysql 전용
        String USER="root";
        String PWD="12345";
		
		//변수 : java에서 제공한 인터페이스들
		Connection con = null; //클래스는 null로 초기화 한다
		Statement stmt = null;
		ResultSet rs = null;
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); //out객체 생성
		
		//클라이언트가 보내준 데이터 받기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String db_pw = "";
		
		
		try {
			Class.forName(DRIVER);
			System.out.println(" 드라이버 로딩 성공");
		} catch (ClassNotFoundException cnf) {
			System.out.println(" 드라이버 로딩 실패 : " + cnf);
			// TODO: handle exception
		}
		
		try {
			con = DriverManager.getConnection(URL,USER,PWD);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from member1 where id = '" + id + "'");
			
			if (rs.next()) {
				db_pw = rs.getString("pw");
				
				//입력한 암호와 DB의 암호가 같으면 인증 성공
				if(db_pw.equals(pw)){
					//로그인성공
					out.println("<html>");
					out.println("<body>");
					
					out.println("<Meta http-equiv=refresh content='2;url=/02_servlet/member/main.html");
					out.println("<font size=5 color=blue>" + id + "님 저희 사이트를 방문해주셔셔 갑사합니다</font>");
					
					out.println("</html>");
					out.println("</body>");
					
					out.close();
				} else{
					out.println("<html>");
					out.println("<body>");
					
					out.println("<Meta http-equiv=refresh content='2;url=/02_servlet/member/loginForm.html");
					out.println("<font size=5 color=red>암호가 틀립니다</font>");
					
					out.println("</html>");
					out.println("</body>");
					
					out.close();
				} //else-end
			}else{
				out.println("<html>");
				out.println("<body>");
				
				out.println("<Meta http-equiv=refresh content='2;url=/02_servlet/member/loginForm.html");
				out.println("<font size=5 color=red>ID가 존재하지 않습니다</font>");
				
				out.println("</html>");
				out.println("</body>");
				
				out.close();
			}
		
		} catch (SQLException se) {
			System.out.println("SQL 예외 : " + se);
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}//finally-end
		
	}//doPost()-end

}//class-end
