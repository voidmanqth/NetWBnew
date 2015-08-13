package nwbt;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class WbGraphics implements Serializable, Cloneable {
	public int type = -1; // ͼ�ε�����
	public Shape shape = null; // ��ʾ��Բ���߶Σ����ε�һ��ͼ��
	public Color fontColor = Color.black; // ǰ��ɫ
	public Color backColor = Color.white; // ����ɫ
	public Font font = null; // �����ʽ
	public Vector<Point> vecPoints = new Vector<Point>(); // ��ʾ�ɵ��󹹳ɵ�ͼ�εĵ㣬�� ����
	public Point pointOld = null; // �������κ���ʾ�ַ���ʹ��
	public Point pointNew = null; // �������κ���ʾ�ַ���ʹ��
	public String text = null; // ��Ҫ��ʾ���ַ���
	public int penLength = 15; // ���ʿ��
	public int eraserLength = 20; // ��Ƥ���
	public String message = null;  //��Ϣ
	public Vector<String> vecOnlineList = new Vector<String>();
	// Ĭ�Ϲ��캯������Ҫ�Ƿֱ��ռ�
	public WbGraphics() {
		
	}

	// ���ø�������
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

	// ��дclone��������ʵ�ֶ���֮��ֵ�ÿ���
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
