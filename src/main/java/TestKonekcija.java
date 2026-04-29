import java.sql.Connection;
import java.sql.DriverManager;

public class TestKonekcija {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/laboratorija";
        String user = "root";
        String pass = "Akakak4459#";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Konekcija uspesna!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Greska: " + e.getMessage());
        }
    }
}