import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // 영화관 프로그램 전용 DB 연결 정보
    private static final String URL =
            "jdbc:mysql://localhost:3306/movie_db";

    private static final String USER =  "movie_app";

    private static final String PASSWORD = "movie_app12345";

    // DB 연결 객체를 만들어 반환하는 메서드
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}