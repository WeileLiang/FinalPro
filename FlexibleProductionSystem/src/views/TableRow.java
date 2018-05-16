package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import main.MyFrame;

public class TableRow extends JPanel {

	private int width = MyFrame.WIDTH * 2 / 3;
	// 数据行高度
	public static final int normalHeight = MyFrame.HEIGHT / 25;
	// 表头数据行高度
	public static final int largerHeight = MyFrame.HEIGHT / 18;
	private int height = normalHeight;

	private int id;
	private Object[] datas;
	private int[] scales;

	private Font normalFont = new Font("黑体", Font.PLAIN, 13);
	private Font largerFont = new Font("黑体", Font.BOLD, 16);

	// 是否是第一行或最后一行
	private boolean isFirstRow;
	private boolean isLastRow;

	// 当时最后一行或者第一行时，顶端和底端的横线要加粗
	public static final int NORMAL_LINE_HEITHT = 1;
	public static final int LARGER_LINE_HEITHT = 3;

	private Color bgColor = Color.GRAY;

	public TableRow(int id, Object[] datas, int[] scales) {
		this.id = id;
		this.datas = datas;
		this.scales = scales;

		setSize(width, height);
		setBackground(bgColor);
	}

	public TableRow(int id, Object[] datas, int[] scales, boolean isFirstRow, boolean isLastRow) {
		this(id, datas, scales);
		this.isFirstRow = isFirstRow;
		this.isLastRow = isLastRow;

		if (isFirstRow) {
			height = largerHeight;
			setSize(width, height);
			setBackground(new Color(0x8B6969));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // g是Graphics对象
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.WHITE);

		// 第一行代表是表头，字体要更大
		if (isFirstRow)
			g2.setFont(largerFont);
		else
			g2.setFont(normalFont);

		FontMetrics fm = g2.getFontMetrics();

		int sum = 0;
		for (int scale : scales)
			sum += scale;

		int curX = 0;
		// 数据的纵坐标
		int dataY = (height - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent() + 0;
		for (int i = 0; i < datas.length; i++) {
			int colunmWidth = width * scales[i] / sum;
			int dataWidth = fm.stringWidth(datas[i].toString());
			int dataX = curX + (colunmWidth - dataWidth) / 2;
			g2.drawString(datas[i].toString(), dataX, dataY);

			curX += colunmWidth;
			 // 画分隔列的竖线
			 if (i != datas.length - 1)
			 g2.drawLine(curX, 0, curX, height);
			 
			 //不是最后一行也不是第一行
//			 if(!isFirstRow&&!isLastRow)
				 g2.drawLine(0, height-NORMAL_LINE_HEITHT , width, height-NORMAL_LINE_HEITHT );
				 
		}

		// 在最后一行底端画横线
		g2.setStroke(new BasicStroke(LARGER_LINE_HEITHT));
		if (isLastRow) {
			g2.drawLine(0, height - LARGER_LINE_HEITHT / 2, width, height - LARGER_LINE_HEITHT / 2);
		}

		// 如果是第一行，在顶端画一条粗横线
		if (isFirstRow) {
			g2.drawLine(0, LARGER_LINE_HEITHT / 2, width, LARGER_LINE_HEITHT / 2);
			g2.drawLine(0, height - LARGER_LINE_HEITHT / 2, width, height - LARGER_LINE_HEITHT / 2);
		}
	}

	public int getId() {
		return id;
	}
}
