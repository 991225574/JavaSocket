package Project;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.*;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * �����߳�
 */
public class ChatThreadWindow {
    String name;            //Ĭ��Ȩ��
    DatagramSocket ds;
    JComboBox cb;
    private JFrame f;
    JTextArea ta;
    private JTextField tf;
    private static int total=0;// ��������ͳ��

    //���췽��
    public ChatThreadWindow(String name, DatagramSocket ds) {
        //��ֵ����
        this.ds=ds;
        this.name=name;

        /*
         * ���������Ҵ��ڽ���
         */
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 400);
        f.setTitle("������" + " - " + name + "     ��ǰ��������:"+ ++total);
        f.setLocation(300, 200);
        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);
        ta.setEditable(false);
        tf = new JTextField();
        cb = new JComboBox();
        cb.addItem("All");
        JButton jb = new JButton("˽�Ĵ���");
        JPanel pl = new JPanel(new BorderLayout());
        pl.add(cb);
        pl.add(jb, BorderLayout.WEST);
        JPanel p = new JPanel(new BorderLayout());
        p.add(pl, BorderLayout.WEST);
        p.add(tf);
        f.getContentPane().add(p, BorderLayout.SOUTH);
        f.getContentPane().add(sp);
        f.setVisible(true);

        //���̣߳�����GetMessageThread����
        GetMessageThread gt=new GetMessageThread(this);
        //�������ķ���
        gt.start();

        ShowInfoChatRoom(); //������ʾ����
    }

    //���ã���ȡ���ݿ��ѵ�½���û���Ϣ��Ȼ����Լ���½����Ϣ��������
    public void ShowInfoChatRoom(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username_db="scott";
        String pssword_db="123123";
        PreparedStatement pstmt=null;
        Connection conn=null;

        try {
            //��ѯ��½���û����ҷ������ǵ�ip��port
            conn=DriverManager.getConnection(url,username_db,pssword_db);
            String sql="select username,ip,port from users where status='online'";
            pstmt=conn.prepareStatement(sql);
            ResultSet rs =pstmt.executeQuery();

            while (rs.next()){
                String username=rs.getString("username");
                String ip=rs.getString("ip");
                int port=rs.getInt("port");

                byte[] ipB=new byte[4];   //�����ֽڳ���Ϊ4������

                String ips[] =ip.split("\\.");  //��ip�е����ֳ���.���ָ����
                //�������ֳ���
                for (int i=0;i<ips.length;i++){
                    ipB[i]=(byte)Integer.parseInt(ips[i]);   //������ǿ��ת��Ϊ�ֽ���������
                }

                //�жϲ�Ϊ�Լ���Ȼ��Ⱥ����Ϣ
                if(!username.equals(name)){
                    String message=name+"������������";
                    byte[] m=message.getBytes();  //����һ���ֽ�����
                    DatagramPacket dp=new DatagramPacket(m,m.length);
                    dp.setAddress(InetAddress.getByAddress(ipB));    //����ip��ַ
                    dp.setPort(port);                                //���ö˿�
                    DatagramSocket ds=new DatagramSocket();
                    ds.send(dp);                                     //���Ѿ���¼���û�����Ϣ

                    cb.addItem(username);  //��ҪȺ�����û�ҲҪ��ӵ��Լ���������
                }else{
                    ta.append(username+"����������");
                }
            }

        } catch (SQLException | UnknownHostException | SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}