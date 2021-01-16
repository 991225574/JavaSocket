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
 * 聊天线程
 */
public class ChatThreadWindow {
    String name;            //默认权限
    DatagramSocket ds;
    JComboBox cb;
    private JFrame f;
    JTextArea ta;
    private JTextField tf;
    private static int total=0;// 在线人数统计

    //构造方法
    public ChatThreadWindow(String name, DatagramSocket ds) {
        //赋值变量
        this.ds=ds;
        this.name=name;

        /*
         * 设置聊天室窗口界面
         */
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 400);
        f.setTitle("聊天室" + " - " + name + "     当前在线人数:"+ ++total);
        f.setLocation(300, 200);
        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);
        ta.setEditable(false);
        tf = new JTextField();
        cb = new JComboBox();
        cb.addItem("All");
        JButton jb = new JButton("私聊窗口");
        JPanel pl = new JPanel(new BorderLayout());
        pl.add(cb);
        pl.add(jb, BorderLayout.WEST);
        JPanel p = new JPanel(new BorderLayout());
        p.add(pl, BorderLayout.WEST);
        p.add(tf);
        f.getContentPane().add(p, BorderLayout.SOUTH);
        f.getContentPane().add(sp);
        f.setVisible(true);

        //多线程，创建GetMessageThread对象
        GetMessageThread gt=new GetMessageThread(this);
        //调用它的方法
        gt.start();

        ShowInfoChatRoom(); //调用提示方法
    }

    //作用：获取数据库已登陆的用户信息，然后把自己登陆的信息发给他们
    public void ShowInfoChatRoom(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username_db="scott";
        String pssword_db="123123";
        PreparedStatement pstmt=null;
        Connection conn=null;

        try {
            //查询登陆的用户，且返回他们的ip和port
            conn=DriverManager.getConnection(url,username_db,pssword_db);
            String sql="select username,ip,port from users where status='online'";
            pstmt=conn.prepareStatement(sql);
            ResultSet rs =pstmt.executeQuery();

            while (rs.next()){
                String username=rs.getString("username");
                String ip=rs.getString("ip");
                int port=rs.getInt("port");

                byte[] ipB=new byte[4];   //定义字节长度为4的数组

                String ips[] =ip.split("\\.");  //把ip中的数字除了.都分割出来
                //遍历数字出来
                for (int i=0;i<ips.length;i++){
                    ipB[i]=(byte)Integer.parseInt(ips[i]);   //把数字强制转换为字节数组类型
                }

                //判断不为自己，然后群发消息
                if(!username.equals(name)){
                    String message=name+"进入了聊天室";
                    byte[] m=message.getBytes();  //创建一个字节数组
                    DatagramPacket dp=new DatagramPacket(m,m.length);
                    dp.setAddress(InetAddress.getByAddress(ipB));    //设置ip地址
                    dp.setPort(port);                                //设置端口
                    DatagramSocket ds=new DatagramSocket();
                    ds.send(dp);                                     //向已经登录的用户发消息

                    cb.addItem(username);  //把要群发的用户也要添加到自己的下拉框
                }else{
                    ta.append(username+"正在聊天室");
                }
            }

        } catch (SQLException | UnknownHostException | SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}