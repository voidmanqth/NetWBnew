package nwbc;

import java.awt.EventQueue;

public class WbCliTools {

	// �������
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WbCliDraw frame = new WbCliDraw(); // ����ʵ��
					frame.setVisible(true); // ���ÿμ�
					frame.setResizable(false);// ���ô�С���ɱ�
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
