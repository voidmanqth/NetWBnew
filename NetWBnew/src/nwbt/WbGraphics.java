package nwbt;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class WbGraphics implements Serializable, Cloneable {
	public int type = -1; // 图形的类型
	public Shape shape = null; // 表示椭圆，线段，矩形等一类图形
	public Color fontColor = Color.black; // 前景色
	public Color backColor = Color.white; // 背景色
	public Font font = null; // 字体格式
	public Vector<Point> vecPoints = new Vector<Point>(); // 表示由点阵构成的图形的点，如 画笔
	public Point pointOld = null; // 绘制菱形和显示字符串使用
	public Point pointNew = null; // 绘制菱形和显示字符串使用
	public String text = null; // 所要显示的字符串
	public int penLength = 15; // 画笔宽带
	public int eraserLength = 20; // 橡皮宽度
	public String message = null;  //消息
	public Vector<String> vecOnlineList = new Vector<String>();
	// 默认构造函数，主要是分贝空间
	public WbGraphics() {
		
	}

	// 重置各个参数
	public void reset() {
		type = -1;
		shape = null;
		vecPoints.clear();
		vecOnlineList.clear();
		pointOld = null;
		pointNew = null;
		text = null;
		message = null;
	}

	// 重写clone函数，以实现对象之间值得拷贝
	@SuppressWarnings("unchecked")
	public Object clone() {
		WbGraphics newShape = new WbGraphics();
		newShape.shape = this.shape;
		newShape.type = this.type;
		newShape.backColor = this.backColor;
		newShape.fontColor = this.fontColor;
		newShape.font = this.font;
		newShape.vecPoints = (Vector<Point>) this.vecPoints.clone();
		newShape.text = this.text;
		newShape.pointOld = this.pointOld;
		newShape.pointNew = this.pointNew;
		newShape.penLength = this.penLength;
		newShape.eraserLength = this.eraserLength;
		newShape.message = this.message;
		return newShape;
	}
}
