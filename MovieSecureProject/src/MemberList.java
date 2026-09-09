import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberList {

    // 관리자만 회원 목록을 조회하는 메서드 -> 메서드명 수정
    public static void showMembers(String loginRole) {

        // ADMIN이 아니면 조회 중단
        if (!"ADMIN".equals(loginRole)) {
            System.out.println("\n관리자만 회원 목록을 조회할 수 있습니다.");
            return;
        }

        // 비밀번호 해시와 솔트를 제외하고 조회
        String sql =
                "SELECT m_id, user_id, m_name, " +
                        "m_role, created_at " +
                        "FROM member " +
                        "ORDER BY m_id";

        try {
            // movie_app 계정으로 DB 연결
            Connection conn = DBConnection.getConnection();

            // SELECT문 준비 및 실행
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n[영화관 전체 회원 목록]");

            // 회원정보를 한 행씩 출력
            while (rs.next()) {
                int memberId = rs.getInt("m_id");
                String userId = rs.getString("user_id");
                String memberName = rs.getString("m_name");
                String memberRole = rs.getString("m_role");
                String createdAt = rs.getString("created_at");

                System.out.println(
                        memberId + " | " +
                                userId + " | " +
                                memberName + " | " +
                                memberRole + " | " +
                                createdAt
                );
            }

            // DB 객체 종료
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("회원 목록 조회 중 오류가 발생했습니다.");
            System.out.println("오류 내용: " + e.getMessage());
        }
    }
}



