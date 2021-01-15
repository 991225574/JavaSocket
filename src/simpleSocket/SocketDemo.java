package simpleSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//��ʵ�ֵ����û�ͨ��

//�������˴����˿�
public class SocketDemo {
    public static void main(String[] args) {
        try {
            //���������� �˿ں�8888 ������ַΪĬ��
            ServerSocket ss=new ServerSocket(8888);
            //��������
            Socket socket=ss.accept();
            System.out.println("���û�����ϵͳ");

            //�������������
            OutputStream os=socket.getOutputStream();
            //����д��������
            OutputStreamWriter osw=new OutputStreamWriter(os);
            //����д�����
            PrintWriter out=new PrintWriter(osw);
            //�����Ϣ
            out.println("��ӭ��XXX");
            //����ˢ��
            out.flush();

            //ʵ�����޽�����Ϣ
            while(true) {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String serverMessage = in.readLine();
                System.out.println("�ͻ���˵��" + serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
