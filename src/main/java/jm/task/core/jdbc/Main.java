package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        Util.getConnection();

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Name_1", "lastName_1", (byte) 10);
        userDao.saveUser("Name_2", "lastName_2", (byte) 20);
        userDao.saveUser("Name_3", "lastName_3", (byte) 30);
        userDao.saveUser("Name_4", "lastName_4", (byte) 40);
        userDao.removeUserById(2);
        userDao.getAllUsers().stream().map(Object::toString).forEach(System.out::println);

        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        Util.closeConnection();
    }
}
