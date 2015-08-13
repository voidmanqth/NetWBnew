package nwbc;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import javax.swing.*;

import nwbt.WbGraphics;


@SuppressWarnings("serial")
public class WbCliDrawgra extends JPanel {

	int shapeType = -1; // ͼ������
	Vector<WbGraphics> vecShapes = null;// ����ͼ��
	public WbGraphics tempShape = null;// ��ʱͼ��
	WbCliLink clientData = null;// ���ݴ�����
	public static Color backColor = Color.white;
	// ���캯������Ҫ�Ƿ���ռ�

	public WbCliDrawgra() throws UnknownHostException, IOException,
	ClassNotFoundException {
		vecShapes = new Vector<WbGraphics>();
		tempShape = new WbGraphics();
	}

	// ��дpaint��������ͼ
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;// ʹ��Graphics2D��ͼ
		super.paint(g2);
		this.setBackground(backColor);// ���ñ���ɫ
		for(WbGraphics temp : vecShapes) {// ��ʾ�Ѿ����Ƶ�
			drawShape(g2, (WbGraphics) temp.clone());
		}
		if(tempShape.type != -1)// ��ʾ��ǰ���ڻ��Ƶ�
			drawShape(g2, tempShape);
	}

	// ����ͼ�κ���
	public void drawShape(Graphics2D g2, WbGraphics shape) {

		g2.setColor(shape.fontColor);// ����ǰ��ɫ
		g2.setStroke(new BasicStroke(shape.penLength)); // ���û��ʿ��
		g2.setFont(shape.font);// ��������
		int[][] points = null;
		// ��vector��Point��תΪ��ά����int[][]
		if(shape.vecPoints.size() > 1) {
			points = new int[2][shape.vecPoints.size()];
			for(int i1 = 0; i1 < shape.vecPoints.size(); i1++) {
				points[0][i1] = shape.vecPoints.get(i1).x;
				points[1][i1] = shape.vecPoints.get(i1).y;
			}
		}
		// ���ݾ������ƣ�����ͼ��
		switch(shape.type) {
			case 0: // ���Ʊ�׼��ͼ�Σ����߶Σ����Σ�Բ�Ǿ��Σ���Բ
				g2.draw(shape.shape);
				break;
			case 1:// ʵ�ֻ��ʣ���һϵ�е���
				if(shape.vecPoints.size() > 1)
					g2.drawPolyline(points[0], points[1], shape.vecPoints.size());
				break;
			case 2:// ʵ����ǹ����һϵ�е���
				if(shape.vecPoints.size() > 1) {
					for(int i = 0; i < shape.vecPoints.size(); i++)// ���㻻Ϊ���Ա�ֱ��Ϊstroke�ĵ�
					{

						points[0][i] = (int) (points[0][i] + Math.pow(-1, i)
								* 3
								* Math.sin(Math.PI * (100 * i - 180) / 180));
						points[1][i] = (int) (points[1][i] + Math.pow(-1, i)
								* 3
								* Math.cos(Math.PI * (100 * i - 180) / 180));
					}
					for(int i1 = 0; i1 < shape.vecPoints.size(); i1++)
						// ���㻭����
						g2.drawLine(points[0][i1], points[1][i1],
								points[0][i1], points[1][i1]);
				}
				break;

			case 3:// ʵ����Ƥ��Ҳ�ǵ��󣬵���ɫΪ����ɫ
				if(shape.vecPoints.size() > 1) {
					Color old = g2.getColor();
					g2.setColor(backColor);

					for(int i = 0; i < shape.vecPoints.size(); i++)
						g2.fillRect(points[0][i], points[1][i],
								shape.eraserLength, shape.eraserLength);
					g2.setColor(old);
				}
				break;

			case 4: // ��������
				int x = Math.min(shape.pointOld.x, shape.pointNew.x),
				y = Math.min(shape.pointOld.y, shape.pointNew.y);
				int weight = Math.abs(shape.pointOld.x - shape.pointNew.x),
						height = Math.abs(shape.pointOld.y - shape.pointNew.y);
				int xx[] = new int[4];
				int yy[] = new int[4];
				xx[0] = x + weight / 2;
				yy[0] = y;
				xx[2] = x + weight / 2;
				yy[2] = y + height;
				xx[1] = x;
				yy[1] = y + height / 2;
				xx[3] = x + weight;
				yy[3] = y + height / 2;
				g2.drawPolygon(xx, yy, 4);
				break;
			case 5:// �����ַ���
				g2.drawString(shape.text, shape.pointOld.x, shape.pointOld.y);
				break;
		}

	}

	// �����������д��ݵ�ͼ�������Լ��㣬������Ӧ��ͼ��
	public void drawShape(String shapeType, Point pOld, Point pNew,
			boolean isFinish) throws ClassNotFoundException, IOException {
		switch(shapeType) {
			case "LINE":// ͼ��Ϊֱ��
				tempShape.shape = new Line2D.Double(pOld, pNew);
				tempShape.type = 0;
				break;
			case "RECT":// ͼ��Ϊ����
				tempShape.shape = new Rectangle2D.Double(Math.min(pOld.x,
						pNew.x), Math.min(pOld.y, pNew.y), Math.abs(pOld.x
								- pNew.x), Math.abs(pOld.y - pNew.y));
				tempShape.type = 0;
				break;
			case "ECLI":// ͼ��Ϊ��Բ
				tempShape.shape = new Ellipse2D.Double(
						Math.min(pOld.x, pNew.x), Math.min(pOld.y, pNew.y),
						Math.abs(pOld.x - pNew.x), Math.abs(pOld.y - pNew.y));
				tempShape.type = 0;
				break;
			case "RRECT":// ͼ��ΪԲ�Ǿ���
				tempShape.shape = new RoundRectangle2D.Double(Math.min(pOld.x,
						pNew.x), Math.min(pOld.y, pNew.y), Math.abs(pOld.x
								- pNew.x), Math.abs(pOld.y - pNew.y), 45, 45);
				tempShape.type = 0;
				break;
			case "PEN":// ͼ��Ϊ���ʣ�Ҫʵ�ֵ���
				tempShape.vecPoints.add(pNew);
				tempShape.type = 1;
				break;
			case "SPEN":// ͼ��Ϊ��ǹ��Ҫʵ�ֵ���
				tempShape.vecPoints.add(pNew);
				tempShape.type = 2;
				break;
			case "EARSER":// ͼ��Ϊ��Ƥ����Ҫʵ�ֵ���
				tempShape.vecPoints.add(pNew);
				tempShape.type = 3;
				break;
			case "DIAMOND":// ͼ��Ϊ���Σ�Ҫʵ�������յ�
				tempShape.pointOld = (Point) pOld.clone();
				tempShape.pointNew = (Point) pNew.clone();
				tempShape.type = 4;
				break;
			case "TEXT":// ͼ��Ϊ�ַ�����Ҫʵ�����
				tempShape.text = JOptionPane.showInputDialog("���������֣�", "�������");
				tempShape.pointOld = (Point) pOld.clone();
				tempShape.type = 5;
				break;
		}
		// ������ͷţ�����Ϊ��ͼ��ɣ�����ͼ�Σ��������������
		if(isFinish == true && tempShape.type <= 4 && tempShape.type >= 0) {
			vecShapes.add((WbGraphics) tempShape.clone());
			if(WbCliDraw.isOnNet == true)
				clientData.sendData((WbGraphics) tempShape.clone());
			tempShape.reset();
		}
		// ��Ϊ�ַ���������ͼ�Σ��������������
		if(tempShape.type == 5) {
			vecShapes.add((WbGraphics) tempShape.clone());
			if(WbCliDraw.isOnNet == true)
				clientData.sendData((WbGraphics) tempShape.clone());
			tempShape.reset();
		}
		repaint();
	}

	// ��÷��������ݵ�����
	@SuppressWarnings("unchecked")
	public void getDataAndRepaint(WbGraphics shape) {
		// TODO �Զ����ɵķ������
		//��ȡ�����б�
		clientData.vecOnlineList = (Vector<String>) shape.vecOnlineList.clone();
		WbCliDraw.jtonline.setText("");
		WbCliDraw.jtonline.append("Total��"+String.valueOf(clientData.vecOnlineList.size()));
		for(String str : clientData.vecOnlineList)
			WbCliDraw.jtonline.append( "\n" +"User:"+ str);
		if(shape.type == 6) {
			getMessage(shape.message);
			return;
		}
		if(shape.type == 7) {
			backColor = shape.backColor;
			repaint();
			return;
		}
		vecShapes.add((WbGraphics) shape.clone());
		repaint();
	}

	// ��������
	public void connect(InetAddress address, int port, boolean just)
			throws IOException {
		// TODO �Զ����ɵķ������
		if(just == true) {
			clientData = new WbCliLink(address, port);
			clientData.start();
		}else {

			WbCliDraw.drawPanel.sendMessage(" ���ĺ���:"+WbCliDraw.userName
					+ "������!");
			clientData.close();
			WbCliDraw.isOnNet = false;
		}

	}
	// ������ɫ����ǰ��ɫ�ı�ʱ���ã�
	public void Repaint(Color cB, Color cF) {
		// TODO �Զ����ɵķ������

		vecShapes.add((WbGraphics) tempShape.clone());
		if(WbCliDraw.isOnNet == true)
			clientData.sendData((WbGraphics) tempShape.clone());
		tempShape.reset();
		if(cB != null) {
			backColor = cB;
			tempShape.backColor = cB;
			tempShape.type = 7;
			vecShapes.add((WbGraphics) tempShape.clone());
			if(WbCliDraw.isOnNet == true)
				clientData.sendData((WbGraphics) tempShape.clone());
			repaint();
		}
		tempShape.reset();
		if(cF != null)
			tempShape.fontColor = cF;

	}

	public void sendMessage(String str) {
		// TODO �Զ����ɵķ������
		WbGraphics shape = new WbGraphics();
		shape.message = str;
		shape.type = 6;
		if(WbCliDraw.isOnNet == true)
			clientData.sendData((WbGraphics) shape.clone());

	}
	public void getMessage(String str) {
		// TODO �Զ����ɵķ������
		String[] sttr = str.split("@");
		if(sttr.length < 2)
			WbCliDraw.jta.append(str + "\n");
		else
		{
			if(sttr[1].equals(WbCliDraw.userName))
				WbCliDraw.jta.append("(���Ļ�)" + sttr[0] + sttr[2] + "\n");
			else
				return;
		}

	}
}
