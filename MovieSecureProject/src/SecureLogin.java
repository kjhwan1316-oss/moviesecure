import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SecureLogin {

    /*
     * Main에서 Scanner를 전달받는다.
     *
     * 로그인 성공 → USER 또는 ADMIN 반환
     * 로그인 실패 → null 반환
     */
    public static String login(Scanner scanner) {

        System.out.println("\n[영화관 회원 로그인]"  );

        // 아이디 입력
        System.out.print("아이디: ");
        String inputId = scanner.nextLine().trim();

        // 비밀번호 입력
        System.out.print("비밀번호: ");
        String inputPassword =scanner.nextLine();

        /* 입력한 아이디의 회원을 찾는다.
          해시와 솔트도 함께 조회한다.        */
        String sql =
                "SELECT m_name, m_role, " +
                        "user_password_hash, password_salt " +
                        "FROM member " +
                        "WHERE user_id = ?";

        try {
            // DBConnection을 이용하여 MySQL 연결
            Connection conn =   DBConnection.getConnection();

            // SQL 준비
            PreparedStatement stmt = conn.prepareStatement(sql);

            // 첫 번째 ?에 아이디 넣기
            stmt.setString(1, inputId);

            // SELECT 실행
            ResultSet rs = stmt.executeQuery();

            // 입력한 아이디의 회원이 있는 경우
            if (rs.next()) {
                // DB에 저장된 해시 가져오기
                String savedHash = rs.getString("user_password_hash");

                // DB에 저장된 솔트 가져오기
                String savedSalt = rs.getString("password_salt");

                /* 입력 비밀번호가 맞는지 확인
                 * 맞으면 true  틀리면 false   */
                boolean passwordCorrect =
                        PasswordUtil.verifyPassword(
                                inputPassword,
                                savedSalt,
                                savedHash );

                // 비밀번호까지 맞는 경우
                if (passwordCorrect) {
                    String memberName = rs.getString("m_name");
                    String memberRole = rs.getString("m_role");
                    System.out.println("\n로그인 성공!");
                    System.out.println(memberName +"님, 환영합니다.");
                    System.out.println("회원 권한: " + memberRole );

                    rs.close();
                    stmt.close();
                    conn.close();

                    // 로그인한 회원의 권한을 Main으로 돌려준다.
                    return memberRole;
                }
            }

            // 아이디가 없거나 비밀번호가 틀린 경우
            System.out.println("\n아이디 또는 비밀번호가 틀렸습니다.");

            rs.close();
            stmt.close();
            conn.close();

            // 로그인 실패를 의미
            return null;

        } catch (Exception e) {
            System.out.println(
                    "로그인 처리 중 오류가 발생했습니다."
            );

            System.out.println(
                    e.getMessage()
            );

            // 오류가 발생해도 로그인 실패로 처리
            return null;
        }
    }
}