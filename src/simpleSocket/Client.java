package simpleSocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//�ͻ��˽�������
public class Client {
    public static void main(String[] args) {
        try {
            //����ͨ�ŵĵ�ַ�Ͷ˿�
            Socket socket=new Socket("localhost",8888);
            //���շ���������Ϣ
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader in=new BufferedReader(isr);
            String serverMessage=in.readLine();
            System.out.println("��������˵��"+serverMessage);

            //�����������
            Scanner sc = new Scanner(System.in);

            //while ʵ�����޷���Ϣ
            while(true) {
                System.out.println("��������Ҫ���͵���Ϣ��");
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
