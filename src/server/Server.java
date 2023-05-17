package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/2101_test";
    static final String DB_LOGIN = "root";
    static final String DB_PASS = "";
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Класс JDBC не найден");
        }
        try {
            // Создаём сокет сервера (открывем порт для прослушивания)
            ServerSocket serverSocket = new ServerSocket(9321);
            while (true){
                // Ожидаем подключения клиента и сохраняем его ip и порт (Socket)
                Socket socket = serverSocket.accept();
                User user = new User(socket); // Создаём пользователя
                users.add(user); // Добавляем пользователя в коллекцию подключенных пользователей
                System.out.println("Клиент подключился");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                            String msg = null;
                            try {
                                JSONParser jsonParser = new JSONParser();
                                JSONObject jsonObject;
                                while (true){
                                    user.getOut().writeUTF("Введите логин: ");
                                    jsonObject = (JSONObject) jsonParser.parse(user.getIn().readUTF());
                                    String email = jsonObject.get("msg").toString();
                                    user.getOut().writeUTF("Введите пароль: ");
                                    jsonObject = (JSONObject) jsonParser.parse(user.getIn().readUTF());
                                    String pass = jsonObject.get("msg").toString();
                                    Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
                                    PreparedStatement preparedStatement = connection.prepareStatement(
                                            "SELECT * FROM users WHERE email=? AND pass=?"
                                    );
                                    preparedStatement.setString(1, email);
                                    preparedStatement.setString(2, pass);
                                    ResultSet resultSet = preparedStatement.executeQuery();
                                    if(resultSet.next()){
                                        String name = resultSet.getString("name");
                                        user.setName(name);
                                        break;
                                    }else{
                                        user.getOut().writeUTF("Неправильный логин или пароль");
                                    }
                                }
                                user.getOut().writeUTF(user.getName()+" добро пожаловать на сервер!");
                                while (true) {
                                    jsonObject = (JSONObject) jsonParser.parse(user.getIn().readUTF());
                                    msg = jsonObject.get("msg").toString();
                                    if(Boolean.parseBoolean(jsonObject.get("public").toString())){
                                        System.out.println(user.getName() +": "+ msg);
                                        for (User user1 : users) {
                                            if(user1 == user) continue;
                                            user1.getOut().writeUTF(user.getName() +": "+ msg);
                                        }
                                    }else{
                                        for (User user1 : users) {
                                            String toUser = jsonObject.get("to_user").toString();
                                            if(user1.getName().equals(toUser)){
                                                user1.getOut().writeUTF(user.getName()+": "+msg);
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                users.remove(user);
                                System.out.println("Кто-то отключился");
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
