package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("45.146.166.151", 9321);
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
                            System.out.println("Кто-то написал: " + serverMsg);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            thread.start();
            Scanner scanner = new Scanner(System.in);
            while (true){
                String msg = scanner.nextLine();
                out.writeUTF(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}