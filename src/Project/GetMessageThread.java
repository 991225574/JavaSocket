package Project;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


//群发信息
public class GetMessageThread extends Thread {
    DatagramSocket ds;
    JTextArea ta;
    JComboBox cb;

    public GetMessageThread(ChatThreadWindow ctw) {  //传过来的ChatThreadWindow类对象
        this.ds = ctw.ds;  //DatagramSocket UDP对象
        this.ta=ctw.ta;    //JTextArea 对象
        this.cb=ctw.cb;    //JComboBox对象
    }

    public void run() {
        try {
            while (true) {
                byte buff[] = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buff, 200);
                ds.receive(dp);
                String message = new String(buff, 0, dp.getLength());
                System.out.println("我是message" + message);
                ta.append(message + "\n");
                if (message.contains("进入了聊天室")) {
                    message = message.replace("进入了聊天室", "");
                    System.out.println("处理后的信息" + message);
                /*
                wjl进入了聊天室
                1.分割上面的消息拿到用户名，例如:wjl
                2.接着在使用JcomboBox把用户名加入下拉框
                */
                cb.addItem(message);  //jiaru下拉框
                System.out.println("UDP收的消息" + message);
            }
        }
    } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
