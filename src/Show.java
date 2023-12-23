import java.sql.*;
import java.util.Properties;

public class Show {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Show s = new Show();
        s.setConnection();
    }

    public void setConnection() {

        Properties userInfo = new Properties();
        userInfo.put("user", "root");
        userInfo.put("password", "");

        String dbUrl = "jdbc:mysql://localhost:3306/jdbcDemo";
        String selectQuery = "Select * from student";

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = DriverManager.getConnection(dbUrl, userInfo);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next())
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}