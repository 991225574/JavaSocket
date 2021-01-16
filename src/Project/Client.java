package Project;

import Project.LoginThread;

//聊天室客户端
public class Client {
	//主方法:启动登录线程
	public static void main(String[] args) throws Exception {
		Thread login = new LoginThread();  //实现向上转型
		login.start(); //调用父类的方法 会调用loginThread的run方法
	}
}