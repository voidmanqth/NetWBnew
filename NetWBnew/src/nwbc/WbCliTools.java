package nwbc;

import java.awt.EventQueue;

public class WbCliTools {

	// 程序入口
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WbCliDraw frame = new WbCliDraw(); // 创建实例
					frame.setVisible(true); // 设置课间
					frame.setResizable(false);// 设置大小不可变
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
