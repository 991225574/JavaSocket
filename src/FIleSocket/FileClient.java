package FIleSocket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
TCP/IP协议：有2个卷
 */
public class FileClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("192.168.1.99",9999);
            FileOutputStream fos=new FileOutputStream("F:\\write.txt");
            InputStream is=socket.getInputStream();
            byte buff[]=new byte[1024];
            int a=0;
            while((a=is.read(buff))!=-1){
                fos.write(buff,0,a);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
