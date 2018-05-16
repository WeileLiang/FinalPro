package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import main.MyFrame;

public class TimeLineItem extends JPanel {

	private int width = MyFrame.WIDTH * 3 / 4;
	private int height = MyFrame.HEIGHT * 5 / 6 / 12;
	private int marginLR = width / 20;

	// 设备名称字体
	private Font machineFont = new Font("黑体", Font.PLAIN, 18);
	// 坐标字体
	private Font coorFont = new Font("黑体", Font.PLAIN, 15);

	private List<int[]> chips;
	private String machineName;

	public TimeLineItem(String machineName, List<int[]> chips) {
		this.machineName = machineName;
		this.chips = chips;
		setSize(width, height);

		setBackground(Color.WHITE);
		setOpaque(false);

	}

	int max = 60;

	public TimeLineItem(String machineName, List<int[]> chips, int max) {
		this.machineName = machineName;
		this.chips = chips;
		setSize(width, height);

		setBackground(null);
		this.max = max;
		// setOpaque(false);

	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // g是Graphics对象
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.WHITE);

		int curY = 0;

		g2.setFont(machineFont);
		FontMetrics fm = g2.getFontMetrics();

		String text = machineName + ": ";
		int textWidth = fm.stringWidth(text);
		int textX = marginLR * 8 / 3 - textWidth;
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int textY = (height - (ascent + descent)) / 2 + ascent + curY;
		// 设备名称
		g2.drawString(text, textX, textY);
		// 坐标轴
		int lineX1 = marginLR * 8 / 3;
		int lineX2 = width - marginLR;
		int lineY = curY + height / 2;
		g2.drawLine(lineX1, lineY, lineX2, lineY);
		// 箭头
		int arrowX = width - marginLR - height / 4;
		int upArrowY = lineY - height / 8;
		int downArrowY = lineY + height / 8;
		g2.drawLine(arrowX, upArrowY, lineX2, lineY);
		g2.drawLine(arrowX, downArrowY, lineX2, lineY);

		// 时间片
		// 坐标轴的总长度
		int len = lineX2 - lineX1;
		// 坐标轴的最大坐标
		int max = this.max;
		for (int[] chip : chips) {
			int x1 = lineX1 + len * chip[0] / max;
			int x2 = lineX1 + len * chip[1] / max;
			int y = lineY - height / 4;
			// g2.drawLine(x1, lineY, x1, y);
			// g2.drawLine(x1, y, x2, y);
			// g2.drawLine(x2, y, x2, lineY);

			g2.fillRect(x1, y, x2 - x1, height / 4);
			// String xText1 = String.valueOf(chip[0]);
			// String xText2 = String.valueOf(chip[1]);

			// 坐标轴坐标
			g2.setFont(coorFont);
			FontMetrics fm2 = g2.getFontMetrics();
			String text1 = String.valueOf(chip[0]);
			String text2 = String.valueOf(chip[1]);
			int textWidth1 = fm2.stringWidth(text1);
			int textWidth2 = fm2.stringWidth(text2);
			int mx1 = x1 - textWidth1 / 2;
			int ascent1 = fm2.getAscent();
			int descent1 = fm2.getDescent();
			int my = (height / 4 - (ascent + descent)) / 2 + ascent + lineY;

			int mx2 = x2 - textWidth2 / 2;
			g2.drawString(text1, mx1, my);

			g2.drawString(text2, mx2, my);
		}
	}

}
