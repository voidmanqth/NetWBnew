package nwbc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

import nwbt.WbTypeface;

@SuppressWarnings("serial")
public class WbCliDraw extends JFrame implements ActionListener{

	/**
	 * Launch the application.
	 */
	private JMenuBar bar ;//����˵���
	private JMenu files,options,help;//����˵�
	private JMenuItem start,pause,exit;//�˵��еĲ˵���
	private JMenuItem fun1,fun2,fun3;//�˵���
	private JMenuItem aboutp,abouta;//�˵���

	private Point pOld = null; // ��ͼ�����
	private Point pNew = null; // ��ͼ���յ�
	private Point pTemp = null; // �м丨����
	private Point pTempOld = null;// �м丨����

	public static String shapeType = "PEN";// Ĭ�ϻ�ͼ����Ϊֱ��

	public static boolean isOnNet = false;// ����Ƿ���������
	private WbCliCanvas optionPanel = null; // ���и��ֿؼ���Jpanel
	public static WbCliDrawgra drawPanel = null;// ����ͼ��JPanel
	JPanel jpChat = null; // ����Jpanel����������
	JPanel jponline = null; // ��ʾ������Ա
	JPanel jpMessage = null; // ������Ϣ���
	JButton jbSend = null; // ���Ͱ�ť
	JButton jbClear = null; // �����ť
	JTextField jtf = null; // ��Ϣ�༭��
	public static JTextArea jta = null; // �����¼��
	public static JTextArea jtonline = null;
	public static String userName = "δ�����û�";// ��¼ʹ�ÿͻ��˵��û���
	JScrollPane jscr = null;// �����¼�������
	JScrollPane jscro = null;// ������Ա��ʾ�������

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws ClassNotFoundException
	 */
	public WbCliDraw() throws UnknownHostException, IOException,
			ClassNotFoundException {
		setResizable(false);
		setTitle("Whiteboard Network Client");// ����װ�ͻ���

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô�����ȷ�ر�
		setBounds(100, 100, 980, 710);// ���ô��ڴ�С
		
		//��Ӳ˵���
				bar=new JMenuBar();//��ʼ���ؼ�
				files=new JMenu("��ʼ(B)");
				options=new JMenu("ѡ��(O)");
				help=new JMenu("����(H)");
				
				bar.add(files);
				bar.add(options);
				bar.add(help);
				
				setJMenuBar(bar);
				files.setMnemonic('F');//��ALT+��F������Ӳ˵���ݼ�
				options.setMnemonic('O');
				help.setMnemonic('H');
				
				start=new JMenuItem("����ģʽ");//�ļ��˵���Ŀ�趨
				pause=new JMenuItem("����ģ��");
				exit=new JMenuItem("�˳�");		
				files.add(start);
				files.add(pause);
				pause.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						optionPanel.showModeJDialog();//////////////////////////
					}
				});
				files.add(exit);
				exit.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						 System.exit(0);//////////////////////////
					}
				});
				start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));//��ʼ�˵��и����ݼ�
				pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
				exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
				start.addActionListener(this);
				pause.addActionListener(this);
				exit.addActionListener(this);
				
				fun1=new JMenuItem("����ɫ");//ѡ��˵��趨
				fun2=new JMenuItem("ǰ��ɫ");
				fun3=new JMenuItem("����");
				options.add(fun1);
				fun1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						drawPanel.Repaint(JColorChooser.showDialog(null,"����ɫ����",Color.black), null);//////////////////////////
					}
				});
				options.add(fun2);
				fun2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						drawPanel.Repaint(null,
								JColorChooser.showDialog(null, "ǰ��ɫ����", Color.black));
					}
				});
				options.add(fun3);
				fun3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						new WbTypeface();
					}
				});
				
				fun1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,InputEvent.CTRL_MASK));
				fun2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));
				fun3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
				fun1.addActionListener(this);
				fun2.addActionListener(this);
				fun3.addActionListener(this);
				
				aboutp=new JMenuItem("���ڳ���");//�����˵��趨
				abouta=new JMenuItem("�����Ա");
				help.add(aboutp);
				aboutp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						//////////////////////////////////
					}
				});
				help.add(abouta);
				abouta.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						////////////////////////////////////
					}
				});
				aboutp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
				abouta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
				aboutp.addActionListener(this);
				abouta.addActionListener(this);

		optionPanel = new WbCliCanvas(); // ��������Japenl��
		// ���ù���Japenl�����������
		optionPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "--------������-------",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(
						200, 123, 100)));
		optionPanel.setBackground(Color.lightGray);// ���ù���Japenl��ı���ɫ
		getContentPane().add(optionPanel, BorderLayout.NORTH);// ���ع���Japenl�ൽ�����ڶ���

		drawPanel = new WbCliDrawgra(); // ������ͼJPanel��ʵ��
		drawPanel.setBackground(Color.white);// ��ͼJPanel�౳��ɫĬ������Ϊ��ɫ

		getContentPane().add(drawPanel, BorderLayout.CENTER);// ���ػ�ͼJPanel�ൽ�������м�

		// ����Ϊ����Jpanel�Ĵ���������
		jpChat = new JPanel();
		jponline = new JPanel();
		jpMessage = new JPanel();
		// ������Ϣ
		jbSend = new JButton("Send");
		jbSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = jtf.getText().trim();
				jta.append("Me��" + str + "\n");
				jtf.setText("");
				if (isOnNet == true)
					drawPanel.sendMessage(userName + ":" + str);
			}
		});
		// ��������
		jbClear = new JButton("Clear");
		jbClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtf.setText("");
				jta.setText("");
			}
		});
		jtf = new JTextField();
		jtf.setForeground(Color.BLUE);
		jtf.setFont(new Font("����", Font.PLAIN, 16));
		jtf.addKeyListener(new KeyAdapter() {// ����JTextFieldde�س��¼�
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String str = jtf.getText().trim();
					jta.append("Me��" + str + "\n");
					jtf.setText("");
					if (isOnNet == true)
						drawPanel.sendMessage(userName + ":" + str);
				}
			}
		});
		jta = new JTextArea();
		jta.setForeground(Color.blue);
		jta.setFont(new Font("Monospaced", Font.PLAIN, 16));
		jscr = new JScrollPane(jta);
		// �ֱ�����ˮƽ�ʹ�ֱ�������Զ�����
		jscr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta.setLineWrap(true);
		jpMessage.setLayout(new GridLayout(2, 1, 0, 0));
		
		// ����������Ա��ʾ��
		jtonline = new JTextArea();
		jtonline.setForeground(Color.magenta);
		jtonline.setFont(new Font("Monospaced", Font.PLAIN, 16));
		//jtonline.setPreferredSize(new Dimension(180,60));//(40, 40, 180, 60);
		jscro = new JScrollPane(jtonline);
		jscro.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscro.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jscro.setPreferredSize(new Dimension(180,60));/////////////////////////////
		jtonline.setLineWrap(true);
		jponline.setLayout(new BorderLayout());
		jtonline.setText("");
		
		JPanel panel = new JPanel();
		jpMessage.add(jtf);
		jpMessage.add(panel);
		panel.add(jbClear);
		panel.add(jbSend);
		// �����������JPanel
		jponline = new JPanel();
		jponline.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "���촰�ڣ�",
				TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, null,
				Color.orange));
		//jponline.setPreferredSize(new Dimension(200, 180));// ����Ա��ʾ�������JPanel
		jponline.add(jscro,BorderLayout.CENTER);

		jpChat = new JPanel();
		jpChat.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "������Ա��",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, Color.orange));
		jpChat.setLayout(new BorderLayout());
		jpChat.setPreferredSize(new Dimension(200, 90));
		jpChat.add(jscr);
		jpChat.add(jponline, BorderLayout.NORTH);
		jpChat.add(jpMessage, BorderLayout.SOUTH);
		// �������쵽�ұ�
		getContentPane().add(jpChat, BorderLayout.WEST);

		// ���������ڵĹر��¼�����Ҫ���ͷ������Դ
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (isOnNet == false)
					return;
				else
					optionPanel.canConnect(false);
			}
		});

		// ����Ϊ���������¼����������£��ƶ����ͷ�
		pTempOld = new Point(0, 0);// ��ʼ��������ͼ��
		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {// ��걻�����¼�
				pOld = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {// ��걻�ͷ��¼�
				pNew = e.getPoint();
				try {
					pTempOld.x = pOld.x;
					pTempOld.y = pOld.y;
					drawPanel.drawShape(shapeType, pTempOld, pNew, true);
				} catch (ClassNotFoundException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		drawPanel.addMouseMotionListener(new MouseMotionAdapter() {// ����ƶ��¼�
					@Override
					public void mouseDragged(MouseEvent e) {
						pTemp = e.getPoint();
						try {
							pTempOld.x = pOld.x;
							pTempOld.y = pOld.y;
							drawPanel.drawShape(shapeType,
									(Point) pTempOld.clone(), pTemp, false);
						} catch (ClassNotFoundException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
					}
				});

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
