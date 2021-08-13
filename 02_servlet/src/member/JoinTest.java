package member;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//import oracle.net.aso.p;

import java.sql.*; 

/**DB를 사용하기 위해서 자바에서 제공하는 인터페이스 
Connetion, Statment, PreparedStatement, ResultSet*/


@WebServlet("/JoinTest")
public class JoinTest extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String DRIVER = "com.mysql.jdbc.Driver"; //MySql제공
		String URL = "jdbc:mysql://localhost:3306/mydb2";
		String USER = "root";
		String PWD = "12345";
		
		Connection con = null; // 모든 클래스는 null로 초기화 한다
		PreparedStatement pstmt = null; //SQL 쿼리를 수행하기 위해서
		
		response.setContentType("text/html;charset=UTF-8"); //웹으로 응답할때 한글처리
		
		request.setCharacterEncoding("UTF-8"); //post 요청시 클라이언트에서 보낸 한글처리 
		PrintWriter out = response.getWriter(); //응답하기위해 out객체 생성
		
		//클라이언트가 보내준 데이터 받기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		
		//DB작업
		
		try {
			Class.forName(DRIVER); //드라이버 로딩
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException cnf) {
			System.out.println("드라이버 로딩 실패 : " + cnf);
			// TODO: handle exception
		}
		
		try {
			con = DriverManager.getConnection(URL,USER,PWD); //DB연결
			//존재하는 ID인지 검사(ID중복 체크)
			ResultSet rs = null;
			String sql = "select * from member1 where id=?";
			pstmt =  con.prepareStatement(sql); //생성시 인자가 들어감
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){  //사용중인 id
				out.println("<script>");
				out.println("alert('사용중인 ID입니다')");
				out.println("</script>");
				
				out.println("<Meta http-equiv=refresh content='2;url=/02_servlet/member/memberForm.html");
				out.println("사용중인 ID입니다");
				return;
			}//if - end
			rs.close();
			
			//사용가능한 ID일때 회원 가입힌다.
			sql = "insert into member1 values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql); //생성시 인자 들어감
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, tel);
			pstmt.setString(5, email);
			
			pstmt.executeUpdate(); //쿼리 수행, 호원가입
			
		} catch (SQLException se) {
			System.out.println("SQL 예외 : " + se);
			// TODO: handle exception
		} finally{
			try {
				if (pstmt != null) {pstmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}//finally - end
		out.println("<html>");
		out.println("<body>");
		out.println("<h2>회원가입 성공</h2>");
		out.println("</body>");
		out.println("</html>");
		out.close();
		//memberForm.html 실행
		//DB가서 확인 작업
		
	}//doPost() - end

}//class - end
