package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User(0,"ada1234","user0@gmail.com","aaab1213c");
        UserDao userDao = new UserDao();
        System.out.println("wpisany user");

        //userDao.create(user);
        User user7 = userDao.read(7);
        System.out.println(user7);

       // userDao.delete(5);

        //User user = new User(0,"test2@test.com","test2ada1234","test2aaab1213c");
        //userDao.update(user);
        //System.out.println("update user");


      System.out.println("Wszyscy:");
      List<User> all = userDao.findAll();
      //System.out.println(all);


    }
}