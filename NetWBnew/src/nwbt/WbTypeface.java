package nwbt;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import nwbc.WbCliDraw;



public class WbTypeface implements ActionListener {

	final JDialog fontDialog = new JDialog(); // 字体选择对话框
	final JTextField tfFont = new JTextField(15); // 字体风格
	final JTextField tfSize = new JTextField(8); // 字体大小
	final JTextField tfStyle = new JTextField(15); // 字体形式
	// 字体形式常量
	final int fontStyleConst[] = {Font.PLAIN, Font.BOLD, Font.ITALIC,
			Font.BOLD + Font.ITALIC};
	@SuppressWarnings("rawtypes")
	final JList listStyle; // 选择字体形式的列表
	@SuppressWarnings("rawtypes")
	final JList listFont; // 选择字体风格的列表
	@SuppressWarnings("rawtypes")
	final JList listSize; // 选择字体型号的列表
	private JLabel sample; // 示例
	private JButton fontOkButton = new JButton("确定"); // 字体设置里的"确定"按钮

	// 设置字体风格，大小将其添加到面板中.

	@SuppressWarnings({"unchecked", "rawtypes"})
	public WbTypeface() {

		// 初始化面板
		Container container = fontDialog.getContentPane();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));

		Font currentFont = container.getFont();// 获取当前的字体格式

		JLabel lblFont = new JLabel("字体(F):");
		lblFont.setPreferredSize(new Dimension(100, 20));
		JLabel lblStyle = new JLabel("字形(Y):");
		lblStyle.setPreferredSize(new Dimension(100, 20));
		JLabel lblSize = new JLabel("大小(S):");
		lblSize.setPreferredSize(new Dimension(100, 20));

		// 字体名称
		tfFont.setText(currentFont.getFontName());
		tfFont.selectAll();
		tfFont.setPreferredSize(new Dimension(200, 20));

		// 设置字体风格
		if(currentFont.getStyle() == Font.PLAIN)
			tfStyle.setText("常规");
		else if(currentFont.getStyle() == Font.BOLD)
			tfStyle.setText("粗体");
		else if(currentFont.getStyle() == Font.ITALIC)
			tfStyle.setText("斜体");
		else if(currentFont.getStyle() == (Font.BOLD + Font.ITALIC))
			tfStyle.setText("粗斜体");

		tfStyle.setPreferredSize(new Dimension(200, 20));

		// 字体大小
		tfSize.setText(currentFont.getSize() + "");
		tfSize.setPreferredSize(new Dimension(200, 20));

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		final String fontName[] = ge.getAvailableFontFamilyNames();
		int defaultFontIndex = 0;
		for(int i = 0; i < fontName.length; i++) {
			if(fontName[i].equals(currentFont.getFontName())) {
				defaultFontIndex = i;
				break;
			}
		}
		listFont = new JList(fontName);
		listFont.setSelectedIndex(defaultFontIndex);
		listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFont.setVisibleRowCount(7);
		listFont.setFixedCellWidth(82);
		listFont.setFixedCellHeight(20);
		listFont.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				tfFont.setText(fontName[listFont.getSelectedIndex()]);
				tfFont.requestFocus();
				tfFont.selectAll();
				updateSample();
			}
		});

		final String fontStyle[] = {"常规", "粗体", "斜体", "粗斜体"};
		listStyle = new JList(fontStyle);
		listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if(currentFont.getStyle() == Font.PLAIN)
			listStyle.setSelectedIndex(0);
		else if(currentFont.getStyle() == Font.BOLD)
			listStyle.setSelectedIndex(1);
		else if(currentFont.getStyle() == Font.ITALIC)
			listStyle.setSelectedIndex(2);
		else if(currentFont.getStyle() == (Font.BOLD + Font.ITALIC))
			listStyle.setSelectedIndex(3);

		listStyle.setVisibleRowCount(7);
		listStyle.setFixedCellWidth(99);
		listStyle.setFixedCellHeight(20);
		listStyle.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				tfStyle.setText(fontStyle[listStyle.getSelectedIndex()]);
				tfStyle.requestFocus();
				tfStyle.selectAll();
				updateSample();
			}
		});

		final String fontSize[] = {"8", "9", "10", "11", "12", "14", "16",
				"18", "20", "22", "24", "26", "28", "36", "48", "72"};
		listSize = new JList(fontSize);

		int defaultFontSizeIndex = 0;

		for(int i = 0; i < fontSize.length; i++) {
			if(fontSize[i].equals(currentFont.getSize() + "")) {
				defaultFontSizeIndex = i;
				break;
			}
		}

		listSize.setSelectedIndex(defaultFontSizeIndex);
		listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSize.setVisibleRowCount(7);
		listSize.setFixedCellWidth(39);
		listSize.setFixedCellHeight(20);
		listSize.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				tfSize.setText(fontSize[listSize.getSelectedIndex()]);
				tfSize.requestFocus();
				tfSize.selectAll();
				updateSample();
			}
		});

		fontOkButton.addActionListener(this);
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontDialog.dispose();
			}
		});

		sample = new JLabel("hehehe Notepad");
		sample.setHorizontalAlignment(SwingConstants.CENTER);
		sample.setPreferredSize(new Dimension(300, 50));

		JPanel samplePanel = new JPanel();
		samplePanel.setBorder(BorderFactory.createTitledBorder("示例"));
		samplePanel.add(sample);

		// 添加组建到面板
		container.add(lblFont);
		container.add(lblStyle);
		container.add(lblSize);
		container.add(tfFont);
		container.add(tfStyle);
		container.add(tfSize);
		container.add(fontOkButton);
		container.add(new JScrollPane(listFont));
		container.add(new JScrollPane(listStyle));
		container.add(new JScrollPane(listSize));
		container.add(cancelButton);
		container.add(samplePanel);

		// 更新示例文字
		updateSample();

		fontDialog.setSize(350, 340);
		fontDialog.setLocation(200, 200);
		fontDialog.setResizable(false);
		fontDialog.setVisible(true);

	}// 构造函数结束

	// 更新示例显示的字体和风格大小.

	public void updateSample() {
		Font sampleFont = new Font(tfFont.getText(),
				fontStyleConst[listStyle.getSelectedIndex()],
				Integer.parseInt(tfSize.getText()));
		sample.setFont(sampleFont);
	}

	// 设置文字的字体.

	public void actionPerformed(ActionEvent e) {
		// 按确认时设置字体
		if(e.getSource() == fontOkButton) {
			WbCliDraw.drawPanel.tempShape.font = new Font(tfFont.getText(),
					fontStyleConst[listStyle.getSelectedIndex()],
					Integer.parseInt(tfSize.getText()));
			fontDialog.dispose();
		}
	}

}
