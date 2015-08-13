package nwbs;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class WbSerfun extends JFrame {

	private JPanel contentPane = new JPanel();
	WbSerCanvas panel = null; // 加载Jtextarea使用
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WbSerfun frame = new WbSerfun();
					frame.setVisible(true);
					frame.setResizable(false);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	// 设置各个空间位置
	public WbSerfun() {
		setTitle("Network Whiteboard Server");//网络白板服务器端
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// 创建JTextArea
		JTextArea txtrn = new JTextArea();
		txtrn.setText("Server Log:");
		txtrn.setToolTipText("");
		txtrn.setForeground(Color.pink);
		txtrn.setBackground(Color.WHITE);
		txtrn.setFont(new Font("微软雅黑", Font.PLAIN, 18));

		JScrollPane scroll = new JScrollPane(txtrn);
		// 把定义的JTextArea放到JScrollPane里面去

		// 分别设置水平和垂直滚动条自动出现
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		txtrn.setLineWrap(true);

		contentPane.add(scroll, BorderLayout.CENTER);
		panel = new WbSerCanvas(txtrn);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
}