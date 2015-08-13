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

	int shapeType = -1; // 图形类型
	Vector<WbGraphics> vecShapes = null;// 所有图形
	public WbGraphics tempShape = null;// 临时图形
	WbCliLink clientData = null;// 数据传送类
	public static Color backColor = Color.white;
	// 构造函数，主要是分配空间

	public WbCliDrawgra() throws UnknownHostException, IOException,
	ClassNotFoundException {
		vecShapes = new Vector<WbGraphics>();
		tempShape = new WbGraphics();
	}

	// 重写paint函数，绘图
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;// 使用Graphics2D绘图
		super.paint(g2);
		this.setBackground(backColor);// 设置背景色
		for(WbGraphics temp : vecShapes) {// 显示已经绘制的
			drawShape(g2, (WbGraphics) temp.clone());
		}
		if(tempShape.type != -1)// 显示当前正在绘制的
			drawShape(g2, tempShape);
	}

	// 绘制图形函数
	public void drawShape(Graphics2D g2, WbGraphics shape) {

		g2.setColor(shape.fontColor);// 设置前景色
		g2.setStroke(new BasicStroke(shape.penLength)); // 设置画笔宽度
		g2.setFont(shape.font);// 设置字体
		int[][] points = null;
		// 把vector《Point》转为二维数组int[][]
		if(shape.vecPoints.size() > 1) {
			points = new int[2][shape.vecPoints.size()];
			for(int i1 = 0; i1 < shape.vecPoints.size(); i1++) {
				points[0][i1] = shape.vecPoints.get(i1).x;
				points[1][i1] = shape.vecPoints.get(i1).y;
			}
		}
		// 根据具体类似，绘制图形
		switch(shape.type) {
			case 0: // 绘制标准的图形，如线段，矩形，圆角矩形，椭圆
				g2.draw(shape.shape);
				break;
			case 1:// 实现画笔，即一系列点阵
				if(shape.vecPoints.size() > 1)
					g2.drawPolyline(points[0], points[1], shape.vecPoints.size());
				break;
			case 2:// 实现喷枪，即一系列点阵
				if(shape.vecPoints.size() > 1) {
					for(int i = 0; i < shape.vecPoints.size(); i++)// 将点换为点旁边直径为stroke的点
					{

						points[0][i] = (int) (points[0][i] + Math.pow(-1, i)
								* 3
								* Math.sin(Math.PI * (100 * i - 180) / 180));
						points[1][i] = (int) (points[1][i] + Math.pow(-1, i)
								* 3
								* Math.cos(Math.PI * (100 * i - 180) / 180));
					}
					for(int i1 = 0; i1 < shape.vecPoints.size(); i1++)
						// 将点画出来
						g2.drawLine(points[0][i1], points[1][i1],
								points[0][i1], points[1][i1]);
				}
				break;

			case 3:// 实现橡皮，也是点阵，但颜色为背景色
				if(shape.vecPoints.size() > 1) {
					Color old = g2.getColor();
					g2.setColor(backColor);

					for(int i = 0; i < shape.vecPoints.size(); i++)
						g2.fillRect(points[0][i], points[1][i],
								shape.eraserLength, shape.eraserLength);
					g2.setColor(old);
				}
				break;

			case 4: // 绘制菱形
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
			case 5:// 绘制字符串
				g2.drawString(shape.text, shape.pointOld.x, shape.pointOld.y);
				break;
		}

	}

	// 根据主窗口中传递的图形类行以及点，构造相应的图形
	public void drawShape(String shapeType, Point pOld, Point pNew,
			boolean isFinish) throws ClassNotFoundException, IOException {
		switch(shapeType) {
			case "LINE":// 图形为直线
				tempShape.shape = new Line2D.Double(pOld, pNew);
				tempShape.type = 0;
				break;
			case "RECT":// 图形为矩形
				tempShape.shape = new Rectangle2D.Double(Math.min(pOld.x,
						pNew.x), Math.min(pOld.y, pNew.y), Math.abs(pOld.x
								- pNew.x), Math.abs(pOld.y - pNew.y));
				tempShape.type = 0;
				break;
			case "ECLI":// 图形为椭圆
				tempShape.shape = new Ellipse2D.Double(
						Math.min(pOld.x, pNew.x), Math.min(pOld.y, pNew.y),
						Math.abs(pOld.x - pNew.x), Math.abs(pOld.y - pNew.y));
				tempShape.type = 0;
				break;
			case "RRECT":// 图形为圆角矩形
				tempShape.shape = new RoundRectangle2D.Double(Math.min(pOld.x,
						pNew.x), Math.min(pOld.y, pNew.y), Math.abs(pOld.x
								- pNew.x), Math.abs(pOld.y - pNew.y), 45, 45);
				tempShape.type = 0;
				break;
			case "PEN":// 图形为画笔，要实现点阵
				tempShape.vecPoints.add(pNew);
				tempShape.type = 1;
				break;
			case "SPEN":// 图形为喷枪，要实现点阵
				tempShape.vecPoints.add(pNew);
				tempShape.type = 2;
				break;
			case "EARSER":// 图形为橡皮擦，要实现点阵
				tempShape.vecPoints.add(pNew);
				tempShape.type = 3;
				break;
			case "DIAMOND":// 图形为菱形，要实现起点和终点
				tempShape.pointOld = (Point) pOld.clone();
				tempShape.pointNew = (Point) pNew.clone();
				tempShape.type = 4;
				break;
			case "TEXT":// 图形为字符创，要实现起点
				tempShape.text = JOptionPane.showInputDialog("请输入文字：", "添加文字");
				tempShape.pointOld = (Point) pOld.clone();
				tempShape.type = 5;
				break;
		}
		// 若鼠标释放，则认为绘图完成，储存图形，并向服务器发送
		if(isFinish == true && tempShape.type <= 4 && tempShape.type >= 0) {
			vecShapes.add((WbGraphics) tempShape.clone());
			if(WbCliDraw.isOnNet == true)
				clientData.sendData((WbGraphics) tempShape.clone());
			tempShape.reset();
		}
		// 若为字符串，储存图形，并向服务器发送
		if(tempShape.type == 5) {
			vecShapes.add((WbGraphics) tempShape.clone());
			if(WbCliDraw.isOnNet == true)
				clientData.sendData((WbGraphics) tempShape.clone());
			tempShape.reset();
		}
		repaint();
	}

	// 获得服务器传递的数据
	@SuppressWarnings("unchecked")
	public void getDataAndRepaint(WbGraphics shape) {
		// TODO 自动生成的方法存根
		//获取在线列表
		clientData.vecOnlineList = (Vector<String>) shape.vecOnlineList.clone();
		WbCliDraw.jtonline.setText("");
		WbCliDraw.jtonline.append("Total："+String.valueOf(clientData.vecOnlineList.size()));
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

	// 建立连接
	public void connect(InetAddress address, int port, boolean just)
			throws IOException {
		// TODO 自动生成的方法存根
		if(just == true) {
			clientData = new WbCliLink(address, port);
			clientData.start();
		}else {

			WbCliDraw.drawPanel.sendMessage(" 您的好友:"+WbCliDraw.userName
					+ "已下线!");
			clientData.close();
			WbCliDraw.isOnNet = false;
		}

	}
	// 当背景色或者前景色改变时调用：
	public void Repaint(Color cB, Color cF) {
		// TODO 自动生成的方法存根

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
		// TODO 自动生成的方法存根
		WbGraphics shape = new WbGraphics();
		shape.message = str;
		shape.type = 6;
		if(WbCliDraw.isOnNet == true)
			clientData.sendData((WbGraphics) shape.clone());

	}
	public void getMessage(String str) {
		// TODO 自动生成的方法存根
		String[] sttr = str.split("@");
		if(sttr.length < 2)
			WbCliDraw.jta.append(str + "\n");
		else
		{
			if(sttr[1].equals(WbCliDraw.userName))
				WbCliDraw.jta.append("(悄悄话)" + sttr[0] + sttr[2] + "\n");
			else
				return;
		}

	}
}
