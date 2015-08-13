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
	private JMenuBar bar ;//定义菜单条
	private JMenu files,options,help;//定义菜单
	private JMenuItem start,pause,exit;//菜单中的菜单项
	private JMenuItem fun1,fun2,fun3;//菜单项
	private JMenuItem aboutp,abouta;//菜单项

	private Point pOld = null; // 绘图的起点
	private Point pNew = null; // 绘图的终点
	private Point pTemp = null; // 中间辅助点
	private Point pTempOld = null;// 中间辅助点

	public static String shapeType = "PEN";// 默认绘图类型为直线

	public static boolean isOnNet = false;// 检测是否联网绘制
	private WbCliCanvas optionPanel = null; // 集中各种控件的Jpanel
	public static WbCliDrawgra drawPanel = null;// 绘制图形JPanel
	JPanel jpChat = null; // 聊天Jpanel及其相关组件
	JPanel jponline = null; // 显示在线人员
	JPanel jpMessage = null; // 发送消息组件
	JButton jbSend = null; // 发送按钮
	JButton jbClear = null; // 清除按钮
	JTextField jtf = null; // 消息编辑框
	public static JTextArea jta = null; // 聊天记录框
	public static JTextArea jtonline = null;
	public static String userName = "未命名用户";// 记录使用客户端的用户名
	JScrollPane jscr = null;// 聊天记录框滚动条
	JScrollPane jscro = null;// 在线人员显示框滚动条

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
		setTitle("Whiteboard Network Client");// 网络白板客户端

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口正确关闭
		setBounds(100, 100, 980, 710);// 设置窗口大小
		
		//添加菜单栏
				bar=new JMenuBar();//初始化控件
				files=new JMenu("开始(B)");
				options=new JMenu("选项(O)");
				help=new JMenu("帮助(H)");
				
				bar.add(files);
				bar.add(options);
				bar.add(help);
				
				setJMenuBar(bar);
				files.setMnemonic('F');//即ALT+“F”，添加菜单快捷键
				options.setMnemonic('O');
				help.setMnemonic('H');
				
				start=new JMenuItem("单机模式");//文件菜单项目设定
				pause=new JMenuItem("联网模拟");
				exit=new JMenuItem("退出");		
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
				start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));//开始菜单中各项快捷键
				pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
				exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
				start.addActionListener(this);
				pause.addActionListener(this);
				exit.addActionListener(this);
				
				fun1=new JMenuItem("背景色");//选项菜单设定
				fun2=new JMenuItem("前景色");
				fun3=new JMenuItem("字体");
				options.add(fun1);
				fun1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						drawPanel.Repaint(JColorChooser.showDialog(null,"背景色设置",Color.black), null);//////////////////////////
					}
				});
				options.add(fun2);
				fun2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						drawPanel.Repaint(null,
								JColorChooser.showDialog(null, "前景色设置", Color.black));
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
				
				aboutp=new JMenuItem("关于程序");//帮助菜单设定
				abouta=new JMenuItem("相关人员");
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

		optionPanel = new WbCliCanvas(); // 创建工具Japenl类
		// 设置工具Japenl类的轮廓标题
		optionPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "--------工具栏-------",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(
						200, 123, 100)));
		optionPanel.setBackground(Color.lightGray);// 设置工具Japenl类的背景色
		getContentPane().add(optionPanel, BorderLayout.NORTH);// 加载工具Japenl类到主窗口顶部

		drawPanel = new WbCliDrawgra(); // 创建绘图JPanel类实例
		drawPanel.setBackground(Color.white);// 绘图JPanel类背景色默认设置为白色

		getContentPane().add(drawPanel, BorderLayout.CENTER);// 加载绘图JPanel类到主窗口中间

		// 以下为聊天Jpanel的创建，布局
		jpChat = new JPanel();
		jponline = new JPanel();
		jpMessage = new JPanel();
		// 发送消息
		jbSend = new JButton("Send");
		jbSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = jtf.getText().trim();
				jta.append("Me：" + str + "\n");
				jtf.setText("");
				if (isOnNet == true)
					drawPanel.sendMessage(userName + ":" + str);
			}
		});
		// 清空聊天框
		jbClear = new JButton("Clear");
		jbClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtf.setText("");
				jta.setText("");
			}
		});
		jtf = new JTextField();
		jtf.setForeground(Color.BLUE);
		jtf.setFont(new Font("宋体", Font.PLAIN, 16));
		jtf.addKeyListener(new KeyAdapter() {// 捕获JTextFieldde回车事件
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String str = jtf.getText().trim();
					jta.append("Me：" + str + "\n");
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
		// 分别设置水平和垂直滚动条自动出现
		jscr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta.setLineWrap(true);
		jpMessage.setLayout(new GridLayout(2, 1, 0, 0));
		
		// 设置在线人员显示框
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
		// 添加在线人数JPanel
		jponline = new JPanel();
		jponline.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "聊天窗口：",
				TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, null,
				Color.orange));
		//jponline.setPreferredSize(new Dimension(200, 180));// 将人员显示框添加至JPanel
		jponline.add(jscro,BorderLayout.CENTER);

		jpChat = new JPanel();
		jpChat.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "在线人员：",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, Color.orange));
		jpChat.setLayout(new BorderLayout());
		jpChat.setPreferredSize(new Dimension(200, 90));
		jpChat.add(jscr);
		jpChat.add(jponline, BorderLayout.NORTH);
		jpChat.add(jpMessage, BorderLayout.SOUTH);
		// 加载聊天到右边
		getContentPane().add(jpChat, BorderLayout.WEST);

		// 监听主窗口的关闭事件，主要是释放相关资源
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (isOnNet == false)
					return;
				else
					optionPanel.canConnect(false);
			}
		});

		// 以下为监听鼠标的事件，包括按下，移动，释放
		pTempOld = new Point(0, 0);// 初始化辅助绘图点
		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {// 鼠标被按下事件
				pOld = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 鼠标被释放事件
				pNew = e.getPoint();
				try {
					pTempOld.x = pOld.x;
					pTempOld.y = pOld.y;
					drawPanel.drawShape(shapeType, pTempOld, pNew, true);
				} catch (ClassNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		drawPanel.addMouseMotionListener(new MouseMotionAdapter() {// 鼠标移动事件
					@Override
					public void mouseDragged(MouseEvent e) {
						pTemp = e.getPoint();
						try {
							pTempOld.x = pOld.x;
							pTempOld.y = pOld.y;
							drawPanel.drawShape(shapeType,
									(Point) pTempOld.clone(), pTemp, false);
						} catch (ClassNotFoundException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
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
