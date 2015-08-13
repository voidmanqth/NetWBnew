package nwbs;

import java.io.IOException;
import java.net.*;
import javax.swing.JTextArea;

public class WbSerDataTran extends Thread {

	ServerSocket serversocket = null;// 服务端serversocket
	JTextArea textArea = null;
	WbSerDataTran(int port, JTextArea textArea) {
		this.textArea = textArea;
		try {

			serversocket = new ServerSocket(port);// 创建服务端serversocket
			Thread thListener=new Thread(this);
			thListener.start();//把监听也做成一个线程
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	public void run()// 监听网络中的客服端，需要使用进程，否则会阻塞
	{
		while(true) {
			Socket socket = null;
			try {
				socket = serversocket.accept();
			}catch(IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			// 一旦连接成功，则启动一个xian程
			new WbSerMullink(socket, textArea).start();
		}

	}
}
