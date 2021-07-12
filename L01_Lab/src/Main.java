import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        //Създавеме връзка към базата
        //jdbc -> Това е конективитито.Казваме, че ще изпозлваме java database connectivity Driver
        //mysql -> Това е драйвърния протокол. Казваме че с java database connectivity Driver ще се вържем към вида база mysql
        ////localhost:3306 - > Тук подаваме адреса на базата към която ще се вържем.(212.100.200.5 - това също е валиден адрес)
        // soft_uni -> Това е името на БАзата към която искаме да се вържем.
        // "root", "" -> Тук подаваме потребителлското име и паролата за достъп до базата.
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", "root", "");

//        //statement
          //Създаваме стейтмънт, койото да води към базата
//        Statement statement = connection.createStatement();
//
          //Стейтмънта подава заявка към базата и Връща резултат от данните в Сет.
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees WHERE salary > 50000 ");

        //Създавам stmt който праща заявка към базата
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");

        //Подаваме параметър на стейтмънта. Така се предпаваме от SQL Injection.
        preparedStatement.setString(1, "50000");

        //Стейтмънмта връща резултат в Сет, койтоо може да обходим.
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            String salary = rs.getString(7);
            System.out.println("id: " + id + " Name: " + name + " salary: " + salary);
        }


    }
}
