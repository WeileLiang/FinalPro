package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import main.MyFrame;

public class TimeLineItem extends JPanel{


	private int width = MyFrame.WIDTH * 3 / 4;
	public static final int height = MyFrame.HEIGHT /10;
	private int marginLR = width / 20;

	// 设备名称字体
	private Font machineFont = new Font("黑体", Font.BOLD, 18);
	// 坐标字体
	private Font coorFont = new Font("黑体", Font.PLAIN, 15);
	// 工序名称字体
	private Font procedureFont = new Font("黑体", Font.BOLD, 12);

	private List<double[]> chips;
	private List<String> procedures;
	private String machineName;

	private double max = 60;
	public TimeLineItem(String machineName, List<double[]> chips,List<String> procedures) {
		this.machineName = machineName;
		this.chips = chips;
		this.procedures=procedures;
		setSize(width, height);

		max=chips.get(chips.size()-1)[1]+2;
		
		setBackground(null);
//		setOpaque(false);

	}


	public TimeLineItem(String machineName, List<double[]> chips,List<String> procedures, double max) {
		this.machineName = machineName;
		this.chips = chips;
		this.procedures=procedures;
		setSize(width, height);

		setBackground(Color.GRAY);
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
		int textX = marginLR * 6/5 - textWidth;
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int textY = (height - (ascent + descent)) / 2 + ascent + curY;
		// 设备名称
		g2.drawString(text, textX, textY);
		// 坐标轴
		int lineX1 = marginLR * 6 / 5;
		int lineX2 = width - marginLR;
		int lineY = curY + height / 2;
		g2.drawLine(lineX1, lineY, lineX2, lineY);
		// 箭头
		int arrowX = width - marginLR - 6;
		int upArrowY = lineY - height / 18;
		int downArrowY = lineY + height / 18;
		g2.drawLine(arrowX, upArrowY, lineX2, lineY);
		g2.drawLine(arrowX, downArrowY, lineX2, lineY);

		// 时间片
		// 坐标轴的总长度
		int len = lineX2 - lineX1;
		// 坐标轴的最大坐标
		int max = (int) this.max;
		for (double[] chip : chips) {
			int x1 = (int) (lineX1 + len * chip[0] / max);
			int x2 = (int) (lineX1 + len * chip[1] / max);
			int y = lineY - height / 4;
			// g2.drawLine(x1, lineY, x1, y);
			// g2.drawLine(x1, y, x2, y);
			// g2.drawLine(x2, y, x2, lineY);

			g2.drawRect(x1, y, x2 - x1, height / 4);
//			g2.fillRect(x1, y, x2 - x1, height / 4);
			// String xText1 = String.valueOf(chip[0]);
			// String xText2 = String.valueOf(chip[1]);

			// 坐标轴坐标
			g2.setColor(Color.WHITE);
			g2.setFont(coorFont);
			FontMetrics fm2 = g2.getFontMetrics();
			int temp=(int) chip[0];
			String text1 = chip[0]-temp>0?String.valueOf(chip[0]):String.valueOf(temp);
			temp=(int) chip[1];
			String text2 = chip[1]-temp>0?String.valueOf(chip[1]):String.valueOf(temp);
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
		
//		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(2));
		g2.setFont(procedureFont);
		FontMetrics fm3 = g2.getFontMetrics();
		int ascent3 = fm3.getAscent();
		int descent3 = fm3.getDescent();
		int textY3 = (height/4 - (ascent3 + descent3)) / 2 + ascent3 + height/4;
		for(int i=0;i<chips.size();i++) {
			double[] chip=chips.get(i);
			int x1 = (int) (lineX1 + len * chip[0] / max);
			int x2 = (int) (lineX1 + len * chip[1] / max);
			int y = lineY - height / 4;
			
//			g2.drawLine(x2, height/4, x2, height/2-1);
			String s=procedures.get(i);
			int stringWidth=fm3.stringWidth(s);
			int textX3=x1+(x2-x1-stringWidth)/2;
			g2.drawString(s, textX3, textY3);
		}
	}


}
