package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users VALUES (?, ?, ?, ?);";



    //dodawanie użytkownika

    public void create(User user){
        try {
            Connection connection = DbUtil.getConnection();
              PreparedStatement preparedStatement= connection.prepareStatement(CREATE_USER_QUERY,PreparedStatement.RETURN_GENERATED_KEYS);
              preparedStatement.setInt(1, 0);
              preparedStatement.setString(2, user.getUsername());
              preparedStatement.setString(3, user.getEmail());
              preparedStatement.setString(4,hashPassword(user.getPassword()) );
              preparedStatement.execute();
             ResultSet rs = preparedStatement.getGeneratedKeys();
              if(rs.next()){
                  long id = rs.getLong(1);
                  System.out.println("inserted ID: " + id);
                  user.setId((int) id);
              }
        } catch (SQLException e) {
            System.out.println("błąd! dodawanie uzytkownika");
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
      return   BCrypt.hashpw(password,BCrypt.gensalt());
    }

    //zmiana danych
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username=?, email=?, password=? WHERE id=?;";
    public void update (User user) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,hashPassword(user.getPassword()));
            preparedStatement.setInt(4,user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("błąd! zmiana danych");
            e.printStackTrace();
        }



    }



    //pobieranie po id
    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE id = ?;";

    public User read (int id){
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_QUERY);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return readUser(resultSet);
            }
        } catch (SQLException e) {
           System.out.println("błąd! pobieranie po id");
           e.printStackTrace();
        }
        return null;
    }

    //usuwanie po id
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id =?;";
    public void delete(int id){

        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("błąd! usuwanie po id");
            e.printStackTrace();
        }


    }
    //pobieranie wszystkich użytkowników


    private static final String SELECT_USERS_QUERY = "SELECT * FROM users";
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = readUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("błąd! pobieranie wszystkich uzytkownikow");
            e.printStackTrace();
        }
        return users;
    }

    private User readUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt(1);
        String username = resultSet.getString(2);
        String email = resultSet.getString(3);
        String password = resultSet.getString(4);
        return new User(userId, username, email, password);
    }
}
