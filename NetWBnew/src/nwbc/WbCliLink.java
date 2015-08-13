package nwbc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import nwbt.WbGraphics;


public class WbCliLink extends Thread {

	Socket socket = null; // �ͷ���Socket
	public Vector<String> vecOnlineList = new Vector<String>();
	ObjectOutputStream objOut = null; // ���������
	ObjectInputStream objIn = null;// ���������

	// ���캯�������ӷ����,��ö�������������
	WbCliLink(InetAddress address, int port) {
		// ���ӷ����
		try {
			socket = new Socket(address, 7800);
			WbCliDraw.isOnNet = true;
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			JOptionPane.showMessageDialog(null, "���������Ӵ���!!!", "���Ӵ���!",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		//���Ϳͻ�������
		try
		{
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println(WbCliDraw.userName);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		// ע��ͷ��˵Ķ������������Ӧ��������˳����
		// ���������ȴ���������������������ȴ�������������������ᷢ����������֮��Ȼ

		// ��ȡ���������
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		// ��ö���������

		try {
			objIn = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

	}

	// ����run��������Ҫ���ڲ��ϼ���������Ƿ�����Ϣ����Ҫʹ���̣߳�����ᷢ������
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		while(true) {

			try {
				WbCliDraw.drawPanel.getDataAndRepaint((WbGraphics) objIn
						.readObject());
			}catch(ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}catch(IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}

	}

	// ��ͷ��˷�����Ϣ
	public void sendData(WbGraphics tempShape) {
		try {
			objOut.writeObject(tempShape);
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	// �ر�socket���ͷ������Դ
	@SuppressWarnings("deprecation")
	public void close() throws IOException {
		// TODO �Զ����ɵķ������
		objOut.close();
		objIn.close();
		socket.close();
		this.stop();
	}
}
