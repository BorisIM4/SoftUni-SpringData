import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", "root", "");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees WHERE salary > 50000 ");


        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String salary = resultSet.getString(7);
            System.out.println("id: " + id + " Name: " + name + " salary: " + salary);
        }

    }
}
