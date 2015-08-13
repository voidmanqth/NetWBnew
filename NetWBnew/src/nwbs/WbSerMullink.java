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
	// ���ڱ��������������߳�
	public static Vector<WbSerMullink> vecAllThread = new Vector<WbSerMullink>();
	public static Vector<String> vecClientName = new Vector<String>();
	JTextArea textArea = null;

	 public WbSerMullink(Socket socket, JTextArea textArea) {
		// TODO �Զ����ɵĹ��캯�����
		this.socket = socket;
		//��ÿͻ��˵�����
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
		this.textArea.append("\n" + socket.getInetAddress() + "-Connect��");
		vecAllThread.add(this);
		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void run() {
		
		Thread.currentThread().setName(vecClientName.get(vecAllThread.indexOf(this)));
		// ��Ҫ�������Ĵ���˳��
		try {
			objIn = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		// �����Ƿ������ݴ���
		while(true) {
			try {
				WbGraphics shape = (WbGraphics) objIn.readObject();
				shape.vecOnlineList = (Vector<String>) WbSerMullink.vecClientName.clone();
				sendAllClientData(shape, this);
			}catch(ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��

				// e.printStackTrace();
			}catch(IOException e) {
				// TODO �Զ����ɵ� catch ��

				System.out.println(this.socket.getInetAddress());
				textArea.append("\n" + Thread.currentThread().getName() + " " + this.socket.getInetAddress() + "-Disconnect��");
				this.stop();
				vecClientName.remove(vecAllThread.indexOf(this));	
				vecAllThread.remove(this);
				
				e.printStackTrace();

			}
		}
	}
	// �����еĿͷ��˷���
	public void sendAllClientData(WbGraphics shape, WbSerMullink tempSST) {

		for(WbSerMullink SST : vecAllThread) {
			if(SST != tempSST)
				try {
					SST.objOut.writeObject(shape);
				}catch(IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
		}
	}
}
