import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class ExampleDB {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/2101_test";
    static final String DB_LOGIN = "root";
    static final String DB_PASS = "";
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            /*System.out.println("Введите имя: ");
            String name = scanner.nextLine();*/
            System.out.println("Введите email: ");
            String email = scanner.nextLine();
            System.out.println("Введите пароль: ");
            String pass = scanner.nextLine();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND pass=?"
            );
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            /*
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE email='"+email+"' AND pass='"+pass+"'"
            ); // так делать опасно!!!*/
            if(resultSet.next()){
                System.out.println("Успешный вход в систему");
                System.out.println("Привет "+resultSet.getString("name"));
            }else{
                System.out.println("Неправильный логин или пароль");
            }
            /*statement.executeUpdate(
                    "INSERT INTO `users`(`name`, `email`, `pass`) VALUES ('"+name+"','"+email+"','"+pass+"')"
            );
            System.out.println("Пользователь успешно добавлен");*/
            /*
            Выборка
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`");
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("email"));
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
