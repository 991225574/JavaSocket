package Multithreading;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//��������
public class SocketDemo {
    public static void main(String[] args) {
        try {
            //�����Ҵ�������
            Socket socket = null;
            ServerSocket ss = new ServerSocket(8888);

            //ѭ�������û����󣬺�����Ϣ(���߳�)
            while (true) {
                System.out.println("�ȴ����û�����......");
                socket = ss.accept();    //����������û�,���޾ʹ�������״̬
                System.out.println("���û�����ϵͳ");

                //������Ϣ�ͻ���
                OutputStream os = socket.getOutputStream();//���ҳ������м�������Ȼ���ڸ����������δ�ӡ�����ֵ
                OutputStreamWriter osw = new OutputStreamWriter(os);
                PrintWriter out = new PrintWriter(osw);
                out.println("��ӭ��XXX");
                out.flush();

                // ��������Ϣ�߳�
                ReceiveInfo receiveInfo = new ReceiveInfo(socket);
                receiveInfo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
