package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class LineSeparator extends JLabel {

	private int width;
	private int height;
	private Color color;

	public LineSeparator(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
		setSize(width, height);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // g «Graphics∂‘œÛ
		g2.setStroke(new BasicStroke(width));

		g2.setColor(color);

		g2.drawLine(0, 0, width, height);
	}
}
