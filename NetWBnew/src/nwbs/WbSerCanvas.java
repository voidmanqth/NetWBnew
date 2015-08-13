package nwbs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
class WbSerCanvas extends JPanel {

	private JTextField textField = new JTextField();
	JTextArea textArea = null;
	WbSerCanvas(final JTextArea textArea) {
		this.textArea = textArea;
		JLabel lblNewLabel = new JLabel("Port：");
		lblNewLabel.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 20));
		lblNewLabel.setForeground(Color.orange);
		this.add(lblNewLabel);

		textField.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField.setText("7800");
		textField.setForeground(Color.red);
		this.add(textField);
		textField.setColumns(8);
		// 以下为对按钮的响应
		final JButton btnNewButton = new JButton("Start");
		btnNewButton.setBackground(Color.orange);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = Integer.parseInt(textField.getText().trim());
				new WbSerDataTran(port, textArea).start();
				btnNewButton.setEnabled(false);
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
		this.add(btnNewButton);

		JButton btnNewButton3 = new JButton("Shut");
		btnNewButton3.setBackground(Color.gray);
		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton3.setFont(new Font("宋体", Font.PLAIN, 16));
		this.add(btnNewButton3);

	}
}
