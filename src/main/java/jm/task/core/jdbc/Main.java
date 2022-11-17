package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<User> usersList;

        UserDaoJDBCImpl user = new User();

        user.createUsersTable();

        user.saveUser("Name_1","lastName_1", (byte) 10);
        user.saveUser("Name_2","lastName_2", (byte) 20);
        user.saveUser("Name_3","lastName_3", (byte) 30);
        user.saveUser("Name_4","lastName_4", (byte) 40);

        user.removeUserById(2);

        usersList = user.getAllUsers();
        usersList.stream().map(Object::toString).forEach(System.out::println);
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
