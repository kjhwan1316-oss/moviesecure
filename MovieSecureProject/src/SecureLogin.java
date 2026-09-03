import java.sql.*;
import java.util.Scanner;

public class SecureLogin {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/movie_db";
        String user = "root";
        String password = "sql12345";
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("영화관 회원 로그인");
            System.out.print("아이디: ");
            String inputId = sc.nextLine();
            System.out.print("비밀번호: ");
            String inputPass = sc.nextLine();

            String sql = "SELECT m_id, user_id, m_name, m_role " +
                    "FROM member " +
                    "WHERE user_id = ? " +
                    "AND user_password = ? ";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 첫 번째 ?: 아이디
            pstmt.setString(1, inputId);
            // 두 번째 ?: 비밀번호
            pstmt.setString(2, inputPass);

            System.out.println("\n실행할 sql문");
            System.out.println(sql);

            // [수정 포인트] PreparedStatement 실행 시 매개변수 없이 호출
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String memName = rs.getString("m_name");
                String memRole = rs.getString("m_role");
                System.out.println("\n로그인 성공");
                System.out.println(memName + "님! 환영합니다!");
                System.out.println("회원권한: " + memRole);
            } else {
                System.out.println("아이디나 비밀번호가 다릅니다");
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("데이터베이스 오류");
            System.out.println("오류 내용: " + e.getMessage());
        }
        finally {
            sc.close();
        }
    }
}