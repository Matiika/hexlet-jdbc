package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {


        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");
        var dao = new UserDAO(conn);

        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        try (var statement = conn.createStatement()) {
            statement.execute(sql);
        }

        var user = new User("Maria", "888888888");
        System.out.println(user.getId()); // null
        dao.save(user);
        System.out.println(user.getId()); // Здесь уже выводится какой-то id

// Возвращается Optional<User>
        var user2 = dao.find(user.getId()).get();
        System.out.println(user2.getId() == user.getId()); // true

        user = new User("Mat", "23233232");
        dao.save(user);
        user = new User("Kerry", "asdasdas");
        dao.save(user);

        dao.showAll();

        dao.deleteById(1L);

        dao.showAll();


       /* try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, "Sarah");
                preparedStatement.setString(2, "333333333");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Matvei");
                preparedStatement.setString(2, "222222222");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Kate");
                preparedStatement.setString(2, "444444444");
                preparedStatement.executeUpdate();

                // Если ключ составной, значений может быть несколько
                // В нашем случае, ключ всего один
                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving the entity");
                }
            }

            var sqlDel = "DELETE FROM users WHERE username = (?)";
            try (var preparedStatement = conn.prepareStatement(sqlDel)) {
                preparedStatement.setString(1, "Sarah");
                preparedStatement.executeUpdate();
            }

            var sql3 = "SELECT * FROM users";
            try (var statement3 = conn.createStatement()) {
                var resultSet = statement3.executeQuery(sql3);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
            }
        }*/
    }
}