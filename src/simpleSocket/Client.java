package simpleSocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//客户端接收请求
public class Client {
    public static void main(String[] args) {
        try {
            //建立通信的地址和端口
            Socket socket=new Socket("localhost",8888);
            //接收服务器端消息
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader in=new BufferedReader(isr);
            String serverMessage=in.readLine();
            System.out.println("服务器端说："+serverMessage);

            //创建输入对象
            Scanner sc = new Scanner(System.in);

            //while 实现无限发消息
            while(true) {
                System.out.println("请输入你要发送的消息：");
                String message = sc.next();
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                PrintWriter out = new PrintWriter(osw);
                out.println(message);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
