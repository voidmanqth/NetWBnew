package nwbs;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;

import nwbt.WbGraphics;


public class WbSerMullink extends Thread {
	Socket socket = null;
	ObjectOutputStream objOut = null;
	ObjectInputStream objIn = null;
	// 用于保存启动的所有线程
	public static Vector<WbSerMullink> vecAllThread = new Vector<WbSerMullink>();
	public static Vector<String> vecClientName = new Vector<String>();
	JTextArea textArea = null;

	 public WbSerMullink(Socket socket, JTextArea textArea) {
		// TODO 自动生成的构造函数存根
		this.socket = socket;
		//获得客户端的名字
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			vecClientName.add(in.readLine());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		this.textArea = textArea;
		this.textArea.append("\n" + socket.getInetAddress() + "-Connect！");
		vecAllThread.add(this);
		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void run() {
		
		Thread.currentThread().setName(vecClientName.get(vecAllThread.indexOf(this)));
		// 主要对象流的创建顺序
		try {
			objIn = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 监听是否有数据传入
		while(true) {
			try {
				WbGraphics shape = (WbGraphics) objIn.readObject();
				shape.vecOnlineList = (Vector<String>) WbSerMullink.vecClientName.clone();
				sendAllClientData(shape, this);
			}catch(ClassNotFoundException e) {
				// TODO 自动生成的 catch 块

				// e.printStackTrace();
			}catch(IOException e) {
				// TODO 自动生成的 catch 块

				System.out.println(this.socket.getInetAddress());
				textArea.append("\n" + Thread.currentThread().getName() + " " + this.socket.getInetAddress() + "-Disconnect！");
				this.stop();
				vecClientName.remove(vecAllThread.indexOf(this));	
				vecAllThread.remove(this);
				
				e.printStackTrace();

			}
		}
	}
	// 向所有的客服端发送
	public void sendAllClientData(WbGraphics shape, WbSerMullink tempSST) {

		for(WbSerMullink SST : vecAllThread) {
			if(SST != tempSST)
				try {
					SST.objOut.writeObject(shape);
				}catch(IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		}
	}
}
