package Project;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


//Ⱥ����Ϣ
public class GetMessageThread extends Thread {
    DatagramSocket ds;
    JTextArea ta;
    JComboBox cb;

    public GetMessageThread(ChatThreadWindow ctw) {  //��������ChatThreadWindow�����
        this.ds = ctw.ds;  //DatagramSocket UDP����
        this.ta=ctw.ta;    //JTextArea ����
        this.cb=ctw.cb;    //JComboBox����
    }

    public void run() {
        try {
            while (true) {
                byte buff[] = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buff, 200);
                ds.receive(dp);
                String message = new String(buff, 0, dp.getLength());
                System.out.println("����message" + message);
                ta.append(message + "\n");
                if (message.contains("������������")) {
                    message = message.replace("������������", "");
                    System.out.println("��������Ϣ" + message);
                /*
                wjl������������
                1.�ָ��������Ϣ�õ��û���������:wjl
                2.������ʹ��JcomboBox���û�������������
                */
                cb.addItem(message);  //jiaru������
                System.out.println("UDP�յ���Ϣ" + message);
            }
        }
    } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
