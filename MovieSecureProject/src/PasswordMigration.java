import java.sql.*;

public class PasswordMigration {
     public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/movie_db";
        String user = "root";
        String password = "sql12345";
        String sql = "SELECT m_id, user_id, user_password " +
                "FROM member " +
                "WHERE user_password_hash IS NULL";
        String updateSql = "update member " +
                "set user_password_hash = ?, password_salt = ? " +
                "where m_id = ?";
        Connection conn = null;
        PreparedStatement selectstmt = null;
        PreparedStatement updatestmt = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("mysql 연결 성공");
            //해시값이 없는 회원을 조회할 객체 생성
            selectstmt = conn.prepareStatement(sql);
            //select문을 실행하여 결과 받음
            rs = selectstmt.executeQuery();
            //해시값과 솔트 값을 수정
            updatestmt = conn.prepareStatement(updateSql);
            int count = 0;
            while (rs.next()){
                int memberId = rs.getInt("m_id");
                String userId = rs.getString("user_id");
                String passw = rs.getString("user_password");
                String salt = PasswordUtil.generateSalt();
                String hash = PasswordUtil.hashPassword(passw, salt);

                updatestmt.setString(1, hash);
                updatestmt.setString(2, salt);
                updatestmt.setInt(3, memberId);

                int result = updatestmt.executeUpdate();

                if (result == 1){
                    count++;
                    System.out.println(userId + "회원 수정완료");
                }
            }
            System.out.println("\n 총 "+ count +"명 수정");
        }
        catch (Exception e){
            System.out.println("데이터베이스 오류");
            System.out.println("오류 내용: " + e.getMessage());
        }
        finally {
            try{
                if (rs != null){rs.close();}
                if (updatestmt != null) {updatestmt.close();}
                if (selectstmt != null) {selectstmt.close();}
                if (conn != null){conn.close();}
            }
            catch (SQLException e) {
                System.out.println("데이터베이스 오류");
                System.out.println("오류 내용: " + e.getMessage());
            }
        }
    }
}
