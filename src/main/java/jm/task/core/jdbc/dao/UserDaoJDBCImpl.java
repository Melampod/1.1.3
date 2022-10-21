package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE `mydb`.`users` (\n" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `age` TINYINT(3) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";
    public static final String SAVE_USER = "INSERT into users(name, lastName, age) values(?,?,?)";
    public static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    public static final String GET_ALL = "SELECT * FROM users";
    public static final String CLEAN_USERS_TABLE = "DELETE FROM users;";
    public static final String DROP_TABLE = "DROP TABLE `mydb`.`users`;";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement createTable = Util.getConnection().createStatement()) {
            dropUsersTable();
            createTable.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement dropUsersTable = Util.getConnection().createStatement()) {
            dropUsersTable.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement saveUser = Util.getConnection().prepareStatement(SAVE_USER)) {
            saveUser.setString(1,name);
            saveUser.setString(2, lastName);
            saveUser.setByte(3, age);
            saveUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try (PreparedStatement removeUserById = Util.getConnection().prepareStatement(REMOVE_USER_BY_ID)) {
            removeUserById.setLong(1,id);
            removeUserById.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList= new ArrayList<>();
        try (PreparedStatement getAllUsers = Util.getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = getAllUsers.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement removeUserById = Util.getConnection().prepareStatement(CLEAN_USERS_TABLE)) {
            removeUserById.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
