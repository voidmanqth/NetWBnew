package nwbc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import nwbt.WbGraphics;


public class WbCliLink extends Thread {

	Socket socket = null; // 客服端Socket
	public Vector<String> vecOnlineList = new Vector<String>();
	ObjectOutputStream objOut = null; // 输出对象流
	ObjectInputStream objIn = null;// 输入对象流

	// 构造函数，链接服务端,获得对象的输入输出流
	WbCliLink(InetAddress address, int port) {
		// 链接服务端
		try {
			socket = new Socket(address, 7800);
			WbCliDraw.isOnNet = true;
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			JOptionPane.showMessageDialog(null, "服务器连接错误!!!", "链接错误!",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		//发送客户端名字
		try
		{
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println(WbCliDraw.userName);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		// 注意客服端的对象输入输出流应该与服务端顺序反置
		// 如果服务端先创建对象输出流，则服务端先创建对象输入流，否则会发生阻塞，反之亦然

		// 获取对象输出流
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 获得对象输入流

		try {
			objIn = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	// 重载run函数，主要用于不断监听服务端是否发送消息，需要使用线程，否则会发生阻塞
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true) {

			try {
				WbCliDraw.drawPanel.getDataAndRepaint((WbGraphics) objIn
						.readObject());
			}catch(ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}catch(IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}

	// 向客服端发送消息
	public void sendData(WbGraphics tempShape) {
		try {
			objOut.writeObject(tempShape);
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	// 关闭socket，释放相关资源
	@SuppressWarnings("deprecation")
	public void close() throws IOException {
		// TODO 自动生成的方法存根
		objOut.close();
		objIn.close();
		socket.close();
		this.stop();
	}
}
