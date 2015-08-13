package nwbs;

import java.io.IOException;
import java.net.*;
import javax.swing.JTextArea;

public class WbSerDataTran extends Thread {

	ServerSocket serversocket = null;// �����serversocket
	JTextArea textArea = null;
	WbSerDataTran(int port, JTextArea textArea) {
		this.textArea = textArea;
		try {

			serversocket = new ServerSocket(port);// ���������serversocket
			Thread thListener=new Thread(this);
			thListener.start();//�Ѽ���Ҳ����һ���߳�
		}catch(IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

	}
	public void run()// ���������еĿͷ��ˣ���Ҫʹ�ý��̣����������
	{
		while(true) {
			Socket socket = null;
			try {
				socket = serversocket.accept();
			}catch(IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			// һ�����ӳɹ���������һ��xian��
			new WbSerMullink(socket, textArea).start();
		}

	}
}
