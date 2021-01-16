package Multithreading;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//客户端 登录的用户
public class Client {
    public static void main(String[] args) {
        try {
            //请求服务器
            Socket socket=new Socket("localhost",8888);
            //接收服务端的消息 仅接收一次
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader in=new BufferedReader(isr);
            String serverMessage=in.readLine();
            System.out.println("服务器端说："+serverMessage);

            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.println("请输入你要发送的消息：");
                String message = sc.next();
                //发送消息给服务端
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                PrintWriter out = new PrintWriter(osw);
                out.println(message); //发送内容
                out.flush();           //把内容递交出去
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
