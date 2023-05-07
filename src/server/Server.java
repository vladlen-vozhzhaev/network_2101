package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> sockets = new ArrayList<>();
        try {
            // Создаём сокет сервера (открывем порт для прослушивания)
            ServerSocket serverSocket = new ServerSocket(9321);
            while (true){
                // Ожидаем подключения клиента и сохраняем его ip и порт (Socket)
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("Клиент подключился");
                // Поток ввода
                DataInputStream in = new DataInputStream(socket.getInputStream());
                // Поток вывода
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                            String msg = null;
                            try {
                                while (true) {
                                    msg = in.readUTF();
                                    System.out.println("Клиент прислал сообщение:" + msg);
                                    for (Socket userSocket : sockets) {
                                        DataOutputStream userSocketOut = new DataOutputStream(userSocket.getOutputStream());
                                        userSocketOut.writeUTF(msg);
                                    }
                                }
                            } catch (IOException e) {
                                sockets.remove(socket);
                                System.out.println("Кто-то отключился");
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
