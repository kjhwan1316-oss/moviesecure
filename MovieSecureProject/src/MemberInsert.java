import java.sql.*;
import java.util.Scanner;

public class MemberInsert {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/movie_db";
        String user = "root";
        String password = "sql12345";
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("영화관 회원가입");
            System.out.print("아이디: ");
            String inputId = sc.nextLine();
            System.out.print("비밀번호: ");
            String inputPass = sc.nextLine();
            System.out.println("이름: ");
            String inputName = sc.nextLine();
            System.out.println("회원권한: ");
            String inputRole= sc.nextLine();
            String sql = "INSERT INTO member " + "(user_id, user_password, m_name, m_role) " + "VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 첫 번째 ?: 아이디
            pstmt.setString(1, inputId);
            // 두 번째 ?: 비밀번호
            pstmt.setString(2, inputPass);
            pstmt.setString(3, inputName);
            pstmt.setString(4, inputRole);

            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                System.out.println("\n회원 등록 성공!");
                System.out.println(inputName + "님 등록이 완료되었습니다.");
                System.out.println("회원권한: " + inputRole);
            } else {
                System.out.println("\n회원 등록에 실패했습니다.");
            }

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
