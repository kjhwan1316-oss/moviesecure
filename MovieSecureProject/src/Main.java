import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main() {
        //MySQL 연결 주소
        String url = "jdbc:mysql://localhost:3306/movie_db";
        //MySQL 사용자 계정
        String user = "root";
        //MySQL 비밀번호
        String password = "sql12345";

        try{
            //JAVA와 MySQL 연결
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("MySQL 연결 성공!");
            //연결 종료
            conn.close();
        }
        catch (SQLException e){
            System.out.println("MySQL 연결 실패!");

            System.out.println("오류 내용: "+ e.getMessage());
        }
    }
}
