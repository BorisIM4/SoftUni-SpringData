import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String SCHEMA_NAME = "minions_db";
    private static final BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));
    private static Connection connection;

    public static void main(String[] args) throws IOException, SQLException {
        connection = getConnection();

        System.out.println("Enter the number of Exercise.");
        System.out.println("If you select 5, 6, 8 or 9 first, you will have invalid data.");
        int exNumber = Integer.parseInt(bufferedReader.readLine());
        switch (exNumber) {
            case 2:
                exTwo();
            case 3:
                exThree();
            case 4:
                exFour();
            case 5:
                exFive();
            case 6:
                exSix();
            case 7:
                exSeven();
            case 8:
                exEight();
            case 9:
                exNine();
        }



    }

    private static void exTwo() throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT v.`name` ,COUNT(DISTINCT mv.minion_id) as `m_count` FROM villains as v\n" +
                        "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                        "GROUP BY v.`name`\n" +
                        "HAVING m_count > ?\n" +
                        "ORDER BY m_count DESC;");

        preparedStatement.setInt(1, 15);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.printf("| %-10s | %d |%n", result.getString("name"),
                    result.getInt("m_count"));

        }
    }

    private static void exThree() throws IOException, SQLException {
        System.out.println("Enter Villain id");
        int id = Integer.parseInt(bufferedReader.readLine());


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.name,m.`name`,m.age FROM minions as m\n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "JOIN villains v on v.id = mv.villain_id\n" +
                "WHERE v.id = ?;");

        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
            System.out.printf("| %s %s%n", "Villain: ", result.getString("v.name"));
            System.out.printf("| %-20s | %02d |%n", result.getString("m.name"),
                    result.getInt("age"));
            while (result.next()) {
                System.out.printf("| %-20s | %02d |%n", result.getString("m.name"),
                        result.getInt("age"));
            }
        } else {
            System.out.println("No villain with ID " + id + " exists in the database.");
        }
    }

    private static void exFour() throws IOException, SQLException {

        System.out.println("Enter input.");

        String[] input = bufferedReader.readLine().split(": ");

        String[] infoMinion = input[1].split("\\s+");
        String minionName = infoMinion[0];
        int minionAge = Integer.parseInt(infoMinion[1]);
        String minionTown = infoMinion[2];

        input = bufferedReader.readLine().split(": ");

        String villainName = input[1];

        int townId = findTownIdByName(minionTown);
        if (townId == 0){
            addTown(minionTown);
            System.out.printf("Town %s was added to the database.%n",minionTown);
        }

        String entityVillainName = findVillainByName(villainName);
        if (entityVillainName == null){
            addVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n",villainName);
        }


        entityVillainName = findVillainByName(villainName);
        townId = findTownIdByName(minionTown);
        addMinion(minionName,minionAge,townId);
        int minionId = findMinionByName(minionName);
        int villainId =  findVillainIdByName(entityVillainName);
        addMinionToVillain(minionId,villainId);
        System.out.printf("Successfully added %s to be minion of %s.%n",minionName,villainName);

    }

    private static void addMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO minions (name, age, town_id) VALUES (?,?,?)");
        preparedStatement.setString(1,minionName);
        preparedStatement.setInt(2,minionAge);
        preparedStatement.setInt(3,townId);

        preparedStatement.executeUpdate();


    }


    private static void addMinionToVillain(int minionId1, int villainId1) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE minions_villains\n" +
                "JOIN minions m on m.id = minions_villains.minion_id\n" +
                "join villains v on v.id = minions_villains.villain_id\n" +
                "SET minion_id = ?,villain_id= ?\n" +
                "WHERE m.id = minions_villains.minion_id and v.id = minions_villains.villain_id;");
        preparedStatement.setInt(1,minionId1);
        preparedStatement.setInt(2,villainId1);

        preparedStatement.executeUpdate();
    }

    private static int findVillainIdByName(String villainName) throws SQLException {

        PreparedStatement preparedStatement= connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1,villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        int result = 0;
        if (resultSet.next()){
            result = resultSet.getInt("id");
        }
        return result;

    }

    private static int findMinionByName(String minionName) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1,minionName);
        ResultSet resultSet = preparedStatement.executeQuery();

        int result = 0;
        while (resultSet.next()){
            result = resultSet.getInt("id");
        }
        return result;

    }

    private static void addTown(String minionTown) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO towns(name) VALUES (?)");
        preparedStatement.setString(1,minionTown) ;
        preparedStatement.executeUpdate();

    }

    private static int findTownIdByName(String minionTown) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM towns WHERE name = ?");

        preparedStatement.setString(1,minionTown);
        ResultSet resultSet = preparedStatement.executeQuery();

        int result =0;
        while (resultSet.next()){
            result = resultSet.getInt("id");
        }
        return result;
    }

    private static void addVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO villains(name)" +
                "VALUES (?)");
        preparedStatement.setString(1,villainName);
        preparedStatement.executeUpdate();
    }

    private static String findVillainByName(String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE name = ?");
        preparedStatement.setString(1,villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        String name = null;
        while (resultSet.next()){
            name= resultSet.getString("name");
        }
        return name;


    }

    private static void exFive() throws IOException, SQLException {
        System.out.println("Enter county name.");
        String countryName = bufferedReader.readLine();

        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");

        preparedStatement.setString(1, countryName);

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("No town names were affected.");
            return;
        }
        System.out.println(affectedRows + " town names were affected.");

        PreparedStatement preparedStatementTowns =
                connection.prepareStatement("SELECT name FROM towns WHERE country = ?");

        preparedStatementTowns.setString(1, countryName);
        ResultSet resultSet = preparedStatementTowns.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }


    }

    private static void exSix() throws SQLException, IOException {

        System.out.println("Enter villain id.");
        int id = Integer.parseInt(bufferedReader.readLine());

        int affectedEntities = deleteMinionsByVillainId(id);


        String villainsName = findEntityNameById("villains", id);
        if (villainsName == null) {
            System.out.println("No such villain was found");
            return;
        }

        deleteEntityById(id);

        System.out.printf("%s was deleted%n" +
                "%d minions released%n", villainsName, affectedEntities);


    }

    private static void deleteEntityById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from villains where id = ?");

        preparedStatement.setInt(1, villainId);
    }

    private static int deleteMinionsByVillainId(int id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");

        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate();
    }

    private static void exSeven() throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT name FROM minions");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> allMinionsNames = new ArrayList<>();
        while (resultSet.next()) {
            allMinionsNames.add(resultSet.getString(1));

        }
        int start = 0;
        int end = allMinionsNames.size() - 1;

        for (int i = 0; i < allMinionsNames.size(); i++) {
            if (i % 2 == 0) {
                System.out.println(allMinionsNames.get(start++));
            } else {
                System.out.println(allMinionsNames.get(end--));
            }

        }
    }

    private static void exEight() throws IOException, SQLException {
        System.out.println("Enter minions id in one line separated by space.");
        String[] input = bufferedReader.readLine().split("\\s+");


        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE minions as m\n" +
                        "SET m.age = m.age + 1 , m.name = LOWER(m.name)\n" +
                        "WHERE id = ?;");

        for (int i = 0; i < input.length; i++) {
            int minionId = Integer.parseInt(input[i]);
            preparedStatement.setInt(1, minionId);
            preparedStatement.executeUpdate();

        }
        PreparedStatement preparedStatementAll = connection.prepareStatement("SELECT name,age FROM minions;");
        ResultSet resultSet = preparedStatementAll.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
        }
    }

    private static void exNine() throws IOException {
        System.out.println("You must have Procedure with name usp_get_older");
        System.out.println("Enter minion id.");
        int minionId = Integer.parseInt(bufferedReader.readLine());

        CallableStatement callableStatement =
                null;
        try {
            callableStatement = connection.prepareCall("CALL usp_get_older(?)");
            callableStatement.setInt(1, minionId);
            int affected = callableStatement.executeUpdate();


            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  name,age FROM minions");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.printf("%s %d%n",resultSet.getString("name")
                        ,resultSet.getInt("age"));
            }
        } catch (SQLException throwables) {
            System.out.println("You dont have the Procedure");
        }


    }

    private static Connection getConnection() throws IOException, SQLException {
        System.out.println("Enter user:");
        String user = bufferedReader.readLine();

        System.out.println("Enter password:");
        String password = bufferedReader.readLine();

        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection(CONNECTION_STRING + SCHEMA_NAME, prop);
        return connection;
    }

    private static String findEntityNameById(String tableName, int entityId) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entityId);
        ResultSet resultSet = preparedStatement.executeQuery();

        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("name");
        }
        return result;


    }
}