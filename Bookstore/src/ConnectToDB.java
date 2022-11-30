import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
    public static void main(String[] args) {

        Connection connection;

        {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Assignment2", "postgres", "Lexi");

                if (connection != null) {
                    System.out.println("Connection OK");
                } else {
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
