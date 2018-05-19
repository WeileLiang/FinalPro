package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PartTableHead extends TableRow {

	public static final int HEAD_HEIGHT=normalHeight * 5/2;
	
	public PartTableHead(int id, Object[] datas, int[] scales, boolean isFirstRow, boolean isLastRow) {
		super(id, datas, scales, isFirstRow, isLastRow);
		// TODO Auto-generated constructor stub
		height = HEAD_HEIGHT;
		setSize(width, height);
		isPartTableHead = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // g是Graphics对象
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.WHITE);

		g2.setFont(new Font("黑体", Font.BOLD, 16));
		FontMetrics fm = g2.getFontMetrics();

		int sum = 0;
		for (int scale : scales)
			sum += scale;

		int curX = 0;

		// 工序这两个字的纵坐标
		int tempY = (height - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent() + 0;
		// 设备名称的纵坐标
		int dataY = tempY+height/4-LARGER_LINE_HEITHT/2;
		for (int i = 0; i < datas.length; i++) {
			int colunmWidth = width * scales[i] / sum;
			int dataWidth = fm.stringWidth(datas[i].toString());
			int dataX = curX + (colunmWidth - dataWidth) / 2;

			if (i == 0)
				g2.drawString(datas[i].toString(), dataX, tempY);
			else
				g2.drawString(datas[i].toString(), dataX, dataY);

			curX += colunmWidth;
			
			if(i==0) {
				String s="工序的机器选择和对应的加工时间";
				int dWidth=fm.stringWidth(s);
				int cWidth=width-curX;
				int dY=tempY-height/4+LARGER_LINE_HEITHT/2;
				g2.drawString(s, curX+(cWidth-dWidth)/2, dY);
			}
			
			// 画分隔列的竖线
			if (i != datas.length - 1) {
				if (i == 0) {
					g2.drawLine(curX, 0, curX, height);
					// 画横线
					g2.drawLine(curX, height / 2, width, height / 2);

				} else
					g2.drawLine(curX, height / 2, curX, height);
			}
		}

		// 如果是第一行，在顶端画一条粗横线
		if (isFirstRow) {
			g2.setStroke(new BasicStroke(LARGER_LINE_HEITHT));
			g2.drawLine(0, LARGER_LINE_HEITHT / 2, width, LARGER_LINE_HEITHT / 2);
			g2.drawLine(0, height - LARGER_LINE_HEITHT / 2, width, height - LARGER_LINE_HEITHT / 2);
		}
	}

}
