package nwbs;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class WbSerfun extends JFrame {

	private JPanel contentPane = new JPanel();
	WbSerCanvas panel = null; // ����Jtextareaʹ��
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
	// ���ø����ռ�λ��
	public WbSerfun() {
		setTitle("Network Whiteboard Server");//����װ��������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// ����JTextArea
		JTextArea txtrn = new JTextArea();
		txtrn.setText("Server Log:");
		txtrn.setToolTipText("");
		txtrn.setForeground(Color.pink);
		txtrn.setBackground(Color.WHITE);
		txtrn.setFont(new Font("΢���ź�", Font.PLAIN, 18));

		JScrollPane scroll = new JScrollPane(txtrn);
		// �Ѷ����JTextArea�ŵ�JScrollPane����ȥ

		// �ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		txtrn.setLineWrap(true);

		contentPane.add(scroll, BorderLayout.CENTER);
		panel = new WbSerCanvas(txtrn);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
}