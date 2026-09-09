import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberInsert {

    // 회원가입 기능을 실행하는 메서드
    public static void register(Scanner scanner) {

        System.out.println("\n[영화관 회원가입]");

        System.out.print("아이디: ");
        String inputId = scanner.nextLine().trim();

        System.out.print("비밀번호: ");
        String inputPassword = scanner.nextLine();

        System.out.print("이름: ");
        String inputName = scanner.nextLine().trim();

        // 빈 입력값 검사
        if (inputId.isEmpty() || inputPassword.isEmpty() || inputName.isEmpty()) {
            System.out.println("아이디, 비밀번호, 이름을 모두 입력하세요.");
            return;
        }

        try {
            // movie_app 계정으로 DB 연결
            Connection conn = DBConnection.getConnection();

            // 회원마다 새로운 솔트 생성
            String salt = PasswordUtil.generateSalt();

            // 평문 비밀번호를 PBKDF2 해시로 변환
            String passwordHash = PasswordUtil.hashPassword(inputPassword, salt);

            // 평문 비밀번호는 저장하지 않음
            String sql = "INSERT INTO member (user_id, m_name, user_password_hash, password_salt) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, inputId);
            stmt.setString(2, inputName);
            stmt.setString(3, passwordHash);
            stmt.setString(4, salt);

            int result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("\n회원가입 성공!");
                System.out.println(inputName + "님의 정보가 저장되었습니다.");
            } else {
                System.out.println("\n회원가입에 실패했습니다.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("데이터베이스 오류가 발생했습니다.");

            // 중복 아이디 오류 확인
            if (e.getErrorCode() == 1062) {
                System.out.println("이미 사용 중인 아이디입니다.");
            } else {
                System.out.println("오류 내용: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("비밀번호 처리 중 오류가 발생했습니다.");
            System.out.println("오류 내용: " + e.getMessage());
        }
    }
}