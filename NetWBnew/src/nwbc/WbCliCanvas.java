package nwbc;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;

import nwbt.WbTypeface;

//���ع���JPanel��
@SuppressWarnings("serial")
class WbCliCanvas extends JPanel implements ActionListener, WindowListener {
	// ����Ϊ����ĸ��ֿؼ�
	private ImageIcon[] icons;///���尴ťͼ��
	//private JToolBar btntoolbars;////���幤����
	JPanel jpDrawMode = new JPanel();
	JPanel jpFontAndColor = new JPanel();
	JPanel jpDrawPen = new JPanel();
	JPanel jpShapeSelect = new JPanel();
	JPanel jpEarser = new JPanel();

	JLabel jbName = new JLabel("�����ǳƣ�", JLabel.CENTER);
	JLabel jbMode = null;
	JLabel jbIP = new JLabel("Ŀ��IP��", JLabel.CENTER);
	JLabel jbPort = new JLabel("Ŀ��˿ڣ�", JLabel.CENTER);
	JTextField jtfName = new JTextField("�����ǳ�");
	JTextField jtfIP = new JTextField("127.0.0.1");
	JTextField jtfPort = new JTextField("7800");
	JDialog jd = null;

	JButton btnLine = new JButton("A");//"ֱ��"
	JButton btnRect = new JButton("B");//"����"
	JButton btnRoundRect = new JButton("I");//"Բ�Ǿ���"
	JButton btnEcli = new JButton("D");//"��Բ"
	JButton btnPen = new JButton("F");//"�ֱ�"
	JButton btnDiamond = new JButton("C");//"����"
	JButton btnEraser = new JButton("E");//"��Ƥ��"

	JButton btnText = new JButton("G");//"����"
	JButton btnSprayPen = new JButton("H");//"��ǹ"

	JButton btnConnect = new JButton("Connect");//����
	JButton btnDisconnect = new JButton("Disconnect");//�Ͽ�����
	JButton btnLocal = new JButton("M");//"������ͼ"
	JButton btnNet = new JButton("N");//"�����ͼ"

	JButton btnBackColor = new JButton("J");//"������ɫ"
	JButton btnFrontColor = new JButton("K");//"ǰ����ɫ"
	JButton btnFont = new JButton("L");//"����"

	JSlider jsPen = new JSlider(SwingConstants.HORIZONTAL, 0, 30, 15);
	JSlider jsEarser = new JSlider(SwingConstants.HORIZONTAL, 0, 60, 30);

	ButtonGroup btnGDrawMode = new ButtonGroup();
	ButtonGroup btnGShapeSelect = new ButtonGroup();

	// ����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
	private String tiptext[] = { "����ģʽ", "����ģʽ", "ֱ��", "����", "����", "��Բ",
			"��Ƥ��", "�ֱ�", "��ǹ", "����", "Բ�Ǿ���", "ǰ��ɫ", "����ɫ", "����" };

	public WbCliCanvas() {

		// ���Ӱ�ťͼ��
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
		// Ϊ��ť���ӼࡢͼƬ����ʾ��
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

		// ѡ���ͼģʽ
		jpDrawMode.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "ģʽѡ��", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));////new Color(244,227,186)
		jpDrawMode.setLayout(new GridLayout(1, 2));//////////

		jpDrawMode.add(btnLocal);
		jpDrawMode.add(btnNet);
		btnGDrawMode.add(btnLocal);
		btnGDrawMode.add(btnNet);
		jpDrawMode.setPreferredSize(new Dimension(84, 50));

		// ͼ��ѡ��
		jpShapeSelect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "ͼ�ι���", TitledBorder.CENTER,
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
		// �����Լ���ɫ����
		jpFontAndColor.setLayout(new GridLayout(1, 3));
		jpFontAndColor.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "��ɫ����",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, Color.gray));
		jpFontAndColor.add(btnFrontColor);
		jpFontAndColor.add(btnFont);
		jpFontAndColor.add(btnBackColor);
		jpFontAndColor.setPreferredSize(new Dimension(150, 50));

		// ѡ�񻭱��Լ���Ƥ����
		jpDrawPen.setLayout(new GridLayout(1, 1, 0, 0));
		jpDrawPen.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "���ʴ�ϸ", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));

		jsPen.setMajorTickSpacing(3);
		jsPen.setMinorTickSpacing(1);
		jsPen.setPaintTicks(true);// ��ʾ�̶�
		jsPen.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent evt) {
				WbCliDraw.drawPanel.tempShape.penLength = jsPen.getValue();
			}
		});
		jpEarser.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "��Ƥ��С", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, Color.gray));
		jpEarser.setLayout(new GridLayout(1, 1, 0, 0));

		jsEarser.setMajorTickSpacing(6);
		jsEarser.setMinorTickSpacing(2);
		jsEarser.setPaintTicks(true);// ��ʾ�̶�
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
		// ���Ӹ���JPanel
		this.setLayout(new FlowLayout());//GridLayout(1,4))
		this.add(jpDrawMode);
		this.add(jpFontAndColor);
		this.add(jpShapeSelect);		
		this.add(jpDrawPen);
		this.add(jpEarser);
	}

	// ����������ť�ĵ���¼�
	public void actionPerformed(final ActionEvent e) {
				
		switch (e.getActionCommand()) {
		case "Disconnect"://�Ͽ�����
			canConnect(false);
			jd.dispose();
			break;
		case "Connect"://����
			canConnect(true);
			jd.dispose();
			break;
		case "A"://"ֱ��"
			WbCliDraw.shapeType = "LINE";
			break;
		case "G"://����
			WbCliDraw.shapeType = "TEXT";
			break;
		case "D"://��Բ
			WbCliDraw.shapeType = "ECLI";
			break;
		case "B"://����
			WbCliDraw.shapeType = "RECT";
			break;
		case "I"://Բ�Ǿ���
			WbCliDraw.shapeType = "RRECT";
			break;
		case "E"://��Ƥ��
			WbCliDraw.shapeType = "EARSER";
			break;

		case "C"://������
			WbCliDraw.shapeType = "DIAMOND";
			break;
		case "F"://�ֱ�
			WbCliDraw.shapeType = "PEN";
			break;
		case "H"://��ǹ
			WbCliDraw.shapeType = "SPEN";
			break;

		case "N":///�����ͼ
			showModeJDialog();
			break;
		case "������ͼ":
			break;
		case "J"://������ɫ
			WbCliDraw.drawPanel
					.Repaint(JColorChooser.showDialog(this, "����ɫ����",Color.black), null);
			break;
		case "K"://ǰ����ɫ
			WbCliDraw.drawPanel.Repaint(null,
					JColorChooser.showDialog(this, "ǰ��ɫ����", Color.black));
			break;
		case "L"://����
			new WbTypeface();
			break;
		default:
			WbCliDraw.shapeType = "PEN";
			break;
		}
	}

	// ��ʾ����ģʽ�ĶԻ�������˿ںţ�IP��
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

	// ȷ������
	public void canConnect(final boolean just) {
		if (just == false) {
			try {
				WbCliDraw.drawPanel.connect(null, -1, just);
			} catch (final IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return;
		}
		WbCliDraw.userName = jtfName.getText().trim();
		final String portstr = jtfPort.getText().trim();
		final String IPstr = jtfIP.getText().trim(); // �õ�IP���˿ں�
		final int port = Integer.valueOf(portstr);
		boolean temp = false;
		InetAddress address = null;
		try {
			address = InetAddress.getByName(IPstr);
		} catch (final UnknownHostException e1) {
			// TODO �Զ����ɵ� catch ��
			temp = true;
			JOptionPane.showMessageDialog(null, "�������IP��������������!", "IP����",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		if (temp == false)
			try {
				WbCliDraw.drawPanel.connect(address, port, just);
				WbCliDraw.isOnNet = true;
				WbCliDraw.drawPanel.sendMessage("���ĺ���:" + WbCliDraw.userName
						+ "������!��");
			} catch (final IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
	}

	// ����Ϊ�����¼�����������ʵ�ֵĺ���
	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO �Զ����ɵķ������

	}

}