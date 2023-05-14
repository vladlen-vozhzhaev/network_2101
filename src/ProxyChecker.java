import java.io.*;
import java.net.*;

public class ProxyChecker {
    public static void main(String[] args) {
        try {
            /*FileOutputStream fos = new FileOutputStream("C://java/test.txt");
            String text = "Hello world";
            byte[] buffer = text.getBytes();
            fos.write(buffer);*/
            FileInputStream fis = new FileInputStream("C://java/ip.txt");
            int i;
            String ipTmp = "";
            while ((i = fis.read()) != -1){
                if(i == 13) continue; // Возврат каретки
                else if (i == 10) { // Перенос строки \n
                    //System.out.println(ipTmp);
                    String ip = ipTmp.split(":")[0];
                    String port = ipTmp.split(":")[1];
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            checkProxy(ip, Integer.parseInt(port));
                        }
                    });
                    thread.start();
                    ipTmp = "";
                } else if (i == 9) { // Табуляция
                    ipTmp += ":";
                } else{
                    ipTmp += (char)i;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkProxy(String ip, int port){
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            URL url = new URL("https://vozhzhaev.ru/test.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            /*while ((line = rd.readLine())!=null){
                System.out.println(line);
            }*/
            System.out.println(ip+":"+port+" - РАБОТАЕТ!");
        } catch (IOException e) {
            System.out.println(ip+":"+port+" - не работает");
        }
    }
}
