import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
                if(i == 13) continue;
                else if (i == 10) {
                    //System.out.println(ipTmp);
                    String ip = ipTmp.split(":")[0];
                    String port = ipTmp.split(":")[1];
                    System.out.println("IP: "+ip+", PORT: "+port);
                    ipTmp = "";
                } else if (i == 9) {
                    ipTmp += ":";
                } else{
                    ipTmp += (char)i;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
