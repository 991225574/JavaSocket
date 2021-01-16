package Multithreading;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//服务器端
public class SocketDemo {
    public static void main(String[] args) {
        try {
            //声明且创建服务
            Socket socket = null;
            ServerSocket ss = new ServerSocket(8888);

            //循环接收用户请求，和收消息(多线程)
            while (true) {
                System.out.println("等待新用户连接......");
                socket = ss.accept();    //接受请求的用户,如无就处于阻塞状态
                System.out.println("新用户进入系统");

                //发动消息客户端
                OutputStream os = socket.getOutputStream();//先找出该行有几个对象，然后在该行上面依次打印对象的值
                OutputStreamWriter osw = new OutputStreamWriter(os);
                PrintWriter out = new PrintWriter(osw);
                out.println("欢迎你XXX");
                out.flush();

                // 启动收消息线程
                ReceiveInfo receiveInfo = new ReceiveInfo(socket);
                receiveInfo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
