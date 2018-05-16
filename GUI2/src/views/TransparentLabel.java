package views;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel {
	private float alpha = .5f;
	private String text;
	private Font font = new Font("ºÚÌå", Font.PLAIN, 23);

	public TransparentLabel(String label, float alpha) {
		super();
		text = label;
		setOpaque(true);
		this.alpha = alpha;
	}

	public TransparentLabel(String label, float alpha, Font font) {
		this(label, alpha);
		this.font = font;
	}

	// other constructor

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
	}

	public float getAlpha() {
		return alpha;
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(g.getColor());
		super.paintComponent(g2d);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.drawImage(image, 0, 0, null);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int x = (getWidth() - textWidth) / 2;
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int y = (getHeight() - (ascent + descent)) / 2 + ascent;
		g2.drawString(text, x, y);
	}

}