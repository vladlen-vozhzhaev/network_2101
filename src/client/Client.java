package client;

import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9321);
            System.out.println("Успешное подключение к серверу");
            // Поток ввода
            DataInputStream in = new DataInputStream(socket.getInputStream());
            // Поток вывода
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String serverMsg = in.readUTF();
                            System.out.println(serverMsg);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            thread.start();
            Scanner scanner = new Scanner(System.in);
            while (true){
                /*
                * Hello
                * /m Ivan hello
                * */
                String msg = scanner.nextLine();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("public", true);
                if(msg.split(" ")[0].equals("/m")){
                    String toUser = msg.split(" ")[1];
                    msg = msg.substring(3+toUser.length()+1);
                    jsonObject.put("public", false);
                    jsonObject.put("to_user", toUser);
                }
                jsonObject.put("msg", msg);
                out.writeUTF(jsonObject.toJSONString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}