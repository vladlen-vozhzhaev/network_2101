package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
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
                                user.getOut().writeUTF("Введите имя: ");
                                JSONObject jsonObject = (JSONObject) jsonParser.parse(user.getIn().readUTF());
                                String name = jsonObject.get("msg").toString();
                                user.setName(name);
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
