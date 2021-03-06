package nwbc;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;

import nwbt.WbTypeface;

//加载工具JPanel类
@SuppressWarnings("serial")
class WbCliCanvas extends JPanel implements ActionListener, WindowListener {
	// 以下为定义的各种控件
	private ImageIcon[] icons;///定义按钮图标
	//private JToolBar btntoolbars;////定义工具栏
	JPanel jpDrawMode = new JPanel();
	JPanel jpFontAndColor = new JPanel();
	JPanel jpDrawPen = new JPanel();
	JPanel jpShapeSelect = new JPanel();
	JPanel jpEarser = new JPanel();

	JLabel jbName = new JLabel("您的昵称：", JLabel.CENTER);
	JLabel jbMode = null;
	JLabel jbIP = new JLabel("目标IP：", JLabel.CENTER);
	JLabel jbPort = new JLabel("目标端口：", JLabel.CENTER);
	JTextField jtfName = new JTextField("输入昵称");
	JTextField jtfIP = new JTextField("127.0.0.1");
	JTextField jtfPort = new JTextField("7800");
	JDialog jd = null;

	JButton btnLine = new JButton("A");//"直线"
	JButton btnRect = new JButton("B");//"矩形"
	JButton btnRoundRect = new JButton("I");//"圆角矩形"
	JButton btnEcli = new JButton("D");//"椭圆"
	JButton btnPen = new JButton("F");//"钢笔"
	JButton btnDiamond = new JButton("C");//"方形"
	JButton btnEraser = new JButton("E");//"橡皮擦"

	JButton btnText = new JButton("G");//"文字"
	JButton btnSprayPen = new JButton("H");//"喷枪"

	JButton btnConnect = new JButton("Connect");//连接
	JButton btnDisconnect = new JButton("Disconnect");//断开连接
	JButton btnLocal = new JButton("M");//"本机绘图"
	JButton btnNet = new JButton("N");//"网络绘图"

	JButton btnBackColor = new JButton("J");//"背景颜色"
	JButton btnFrontColor = new JButton("K");//"前景颜色"
	JButton btnFont = new JButton("L");//"字体"

	JSlider jsPen = new JSlider(SwingConstants.HORIZONTAL, 0, 30, 15);
	JSlider jsEarser = new JSlider(SwingConstants.HORIZONTAL, 0, 60, 30);

	ButtonGroup btnGDrawMode = new ButtonGroup();
	ButtonGroup btnGShapeSelect = new ButtonGroup();

	// 这里是鼠标移到相应的按钮上给出相应的提示
	private String tiptext[] = { "单机模式", "联机模式", "直线", "矩形", "菱形", "椭圆",
			"橡皮擦", "钢笔", "喷枪", "文字", "圆角矩形", "前景色", "背景色", "字体" };

	public WbCliCanvas() {

		// 添加按钮图标
		icons = new ImageIcon[14];
		for (int i = 0; i < 14; i++) {
			icons[i] = new ImageIcon("./src/icon/" + (i + 1) + ".JPG");
		}
		jbName.setForeground(Color.red);
		jbIP.setForeground(Color.red);
		jbPort.setForeground(Color.red);
		jtfName.setFont(new Font("Dialog", Font.PLAIN, 18));
		jtfIP.setFont(new Font("Dialog", Font.PLAIN, 18));
		jtfPort.setFont(new Font("Dialog", Font.PLAIN, 18));
		// 为按钮添加监、图片及提示语
		btnLine.addActionListener(this);
		btnLine.setIcon(icons[0]);
		btnLine.setToolTipText(tiptext[2]);
		btnLine.setBackground(new Color(240,240,180));
		btnRect.addActionListener(this);
		btnRect.setIcon(icons[2]);
		btnRect.setToolTipText(tiptext[3]);
		btnRect.setBackground(new Color(240,240,180));
		btnRoundRect.addActionListener(this);
		btnRoundRect.setIcon(icons[4]);
		btnRoundRect.setToolTipText(tiptext[10]);
		btnRoundRect.setBackground(new Color(240,240,180));
		btnEcli.addActionListener(this);
		btnEcli.setIcon(icons[1]);
		btnEcli.setToolTipText(tiptext[5]);
		btnEcli.setBackground(new Color(240,240,180));
		btnPen.addActionListener(this);
		btnPen.setIcon(icons[9]);
		btnPen.setToolTipText(tiptext[7]);
		btnPen.setBackground(new Color(240,240,180));
		btnDiamond.addActionListener(this);
		btnDiamond.setIcon(icons[3]);
		btnDiamond.setToolTipText(tiptext[4]);
		btnDiamond.setBackground(new Color(240,240,180));
		btnEraser.addActionListener(this);
		btnEraser.setIcon(icons[5]);
		btnEraser.setToolTipText(tiptext[6]);
		btnEraser.setBackground(new Color(240,240,180));
		btnText.addActionListener(this);
		btnText.setIcon(icons[6]);
		btnText.setToolTipText(tiptext[9]);
		btnText.setBackground(new Color(240,240,180));
		btnSprayPen.addActionListener(this);
		btnSprayPen.setIcon(icons[10]);
		btnSprayPen.setToolTipText(tiptext[8]);
		btnSprayPen.setBackground(new Color(240,240,180));
		btnConnect.addActionListener(this);
		btnDisconnect.addActionListener(this);
		btnLocal.addActionListener(this);
		btnLocal.setIcon(icons[13]);
		btnLocal.setToolTipText(tiptext[0]);
		btnLocal.setBackground(new Color(240,240,180));
		btnNet.addActionListener(this);
		btnNet.setIcon(icons[12]);
		btnNet.setToolTipText(tiptext[1]);
		btnNet.setBackground(new Color(240,240,180));
		btnBackColor.addActionListener(this);
		btnBackColor.setIcon(icons[7]);
		btnBackColor.setToolTipText(tiptext[12]);
		btnBackColor.setBackground(new Color(240,240,180));
		btnFrontColor.addActionListener(this);
		btnFrontColor.setIcon(icons[8]);
		btnFrontColor.setToolTipText(tiptext[11]);
		btnFrontColor.setBackground(new Color(240,240,180));
		btnFont.addActionListener(this);
		btnFont.setIcon(icons[11]);
		btnFont.setToolTipText(tiptext[13]);
		btnFont.setBackground(new Color(240,240,180));

		// 选择绘图模式
		jpDrawMode.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "模式选择", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));////new Color(244,227,186)
		jpDrawMode.setLayout(new GridLayout(1, 2));//////////

		jpDrawMode.add(btnLocal);
		jpDrawMode.add(btnNet);
		btnGDrawMode.add(btnLocal);
		btnGDrawMode.add(btnNet);
		jpDrawMode.setPreferredSize(new Dimension(84, 50));

		// 图形选择
		jpShapeSelect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "图形工具", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));
		jpShapeSelect.setLayout(new GridLayout(1, 9));//////////////////
		jpShapeSelect.setPreferredSize(new Dimension(380, 50));
		jpShapeSelect.add(btnLine);
		jpShapeSelect.add(btnRect);
		jpShapeSelect.add(btnDiamond);
		jpShapeSelect.add(btnEcli);
		jpShapeSelect.add(btnRoundRect);
		jpShapeSelect.add(btnEraser);
		jpShapeSelect.add(btnPen);
		jpShapeSelect.add(btnText);
		jpShapeSelect.add(btnSprayPen);

		btnGShapeSelect.add(btnLine);
		btnGShapeSelect.add(btnRect);
		btnGShapeSelect.add(btnDiamond);
		btnGShapeSelect.add(btnEcli);
		btnGShapeSelect.add(btnEraser);
		btnGShapeSelect.add(btnPen);
		btnGShapeSelect.add(btnText);
		btnGShapeSelect.add(btnSprayPen);
		btnGShapeSelect.add(btnRoundRect);
		// 字体以及颜色设置
		jpFontAndColor.setLayout(new GridLayout(1, 3));
		jpFontAndColor.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "颜色字体",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, Color.gray));
		jpFontAndColor.add(btnFrontColor);
		jpFontAndColor.add(btnFont);
		jpFontAndColor.add(btnBackColor);
		jpFontAndColor.setPreferredSize(new Dimension(150, 50));

		// 选择画笔以及橡皮设置
		jpDrawPen.setLayout(new GridLayout(1, 1, 0, 0));
		jpDrawPen.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "画笔粗细", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));

		jsPen.setMajorTickSpacing(3);
		jsPen.setMinorTickSpacing(1);
		jsPen.setPaintTicks(true);// 显示刻度
		jsPen.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent evt) {
				WbCliDraw.drawPanel.tempShape.penLength = jsPen.getValue();
			}
		});
		jpEarser.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "橡皮大小", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));
		jpEarser.setLayout(new GridLayout(1, 1, 0, 0));

		jsEarser.setMajorTickSpacing(6);
		jsEarser.setMinorTickSpacing(2);
		jsEarser.setPaintTicks(true);// 显示刻度
		jsEarser.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent evt) {
				WbCliDraw.drawPanel.tempShape.eraserLength = jsEarser
						.getValue();
			}
		});
		jpEarser.add(jsEarser);
		jpDrawPen.add(jsPen);
		//jpDrawPen.add(jpEarser);
		jpDrawPen.setPreferredSize(new Dimension(120, 50));
		jpEarser.setPreferredSize(new Dimension(120, 50));
		// 添加各个JPanel
		this.setLayout(new FlowLayout());//GridLayout(1,4))
		this.add(jpDrawMode);
		this.add(jpFontAndColor);
		this.add(jpShapeSelect);		
		this.add(jpDrawPen);
		this.add(jpEarser);
	}

	// 处理各个按钮的点击事件
	public void actionPerformed(final ActionEvent e) {
				
		switch (e.getActionCommand()) {
		case "Disconnect"://断开连接
			canConnect(false);
			jd.dispose();
			break;
		case "Connect"://连接
			canConnect(true);
			jd.dispose();
			break;
		case "A"://"直线"
			WbCliDraw.shapeType = "LINE";
			break;
		case "G"://文字
			WbCliDraw.shapeType = "TEXT";
			break;
		case "D"://椭圆
			WbCliDraw.shapeType = "ECLI";
			break;
		case "B"://矩形
			WbCliDraw.shapeType = "RECT";
			break;
		case "I"://圆角矩形
			WbCliDraw.shapeType = "RRECT";
			break;
		case "E"://橡皮擦
			WbCliDraw.shapeType = "EARSER";
			break;

		case "C"://正方形
			WbCliDraw.shapeType = "DIAMOND";
			break;
		case "F"://钢笔
			WbCliDraw.shapeType = "PEN";
			break;
		case "H"://喷枪
			WbCliDraw.shapeType = "SPEN";
			break;

		case "N":///网络绘图
			showModeJDialog();
			break;
		case "本机绘图":
			break;
		case "J"://背景颜色
			WbCliDraw.drawPanel
					.Repaint(JColorChooser.showDialog(this, "背景色设置",Color.black), null);
			break;
		case "K"://前景颜色
			WbCliDraw.drawPanel.Repaint(null,
					JColorChooser.showDialog(this, "前景色设置", Color.black));
			break;
		case "L"://字体
			new WbTypeface();
			break;
		default:
			WbCliDraw.shapeType = "PEN";
			break;
		}
	}

	// 显示联网模式的对话框，输入端口号，IP等
	public void showModeJDialog() {
		jd = new JDialog();
		jd.setTitle("Connect to the server...");
		jd.setSize(250, 200);
		jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		jd.setResizable(false);
		jd.setLayout(new GridLayout(4, 2, 5, 5));
		jd.add(jbName);
		jd.add(jtfName);
		jd.add(jbPort);
		jd.add(jtfPort);
		jd.add(jbIP);
		jd.add(jtfIP);
		jd.add(btnDisconnect);
		jd.add(btnConnect);
		jd.setVisible(true);
		jd.setBackground(Color.cyan);
		final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		jd.setLocation(width / 2 - 125, height / 2 - 125);
		jd.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				jd.dispose();
			}
		});

	}

	// 确认联网
	public void canConnect(final boolean just) {
		if (just == false) {
			try {
				WbCliDraw.drawPanel.connect(null, -1, just);
			} catch (final IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return;
		}
		WbCliDraw.userName = jtfName.getText().trim();
		final String portstr = jtfPort.getText().trim();
		final String IPstr = jtfIP.getText().trim(); // 得到IP，端口号
		final int port = Integer.valueOf(portstr);
		boolean temp = false;
		InetAddress address = null;
		try {
			address = InetAddress.getByName(IPstr);
		} catch (final UnknownHostException e1) {
			// TODO 自动生成的 catch 块
			temp = true;
			JOptionPane.showMessageDialog(null, "你输入的IP有误，请重新输入!", "IP错误",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		if (temp == false)
			try {
				WbCliDraw.drawPanel.connect(address, port, just);
				WbCliDraw.isOnNet = true;
				WbCliDraw.drawPanel.sendMessage("您的好友:" + WbCliDraw.userName
						+ "已上线!。");
			} catch (final IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	}

	// 以下为窗口事件的其他必须实现的函数
	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO 自动生成的方法存根

	}

}
