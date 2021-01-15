package simpleSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//仅实现单个用户通信

//服务器端创建端口
public class SocketDemo {
    public static void main(String[] args) {
        try {
            //创建服务器 端口号8888 本机地址为默认
            ServerSocket ss=new ServerSocket(8888);
            //接收请求
            Socket socket=ss.accept();
            System.out.println("新用户进入系统");

            //创建输出流对象
            OutputStream os=socket.getOutputStream();
            //创建写入流对象
            OutputStreamWriter osw=new OutputStreamWriter(os);
            //创建写入对象
            PrintWriter out=new PrintWriter(osw);
            //输出消息
            out.println("欢迎你XXX");
            //发送刷新
            out.flush();

            //实现无限接收消息
            while(true) {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String serverMessage = in.readLine();
                System.out.println("客户端说：" + serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
