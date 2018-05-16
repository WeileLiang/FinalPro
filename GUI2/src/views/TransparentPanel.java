package views;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 可设置透明度
 * @author ASUS
 *
 */
public class TransparentPanel extends JPanel {
	private AlphaComposite cmp = AlphaComposite.getInstance(
			AlphaComposite.SRC_OVER, 1);
	private float alpha;

	public TransparentPanel(float alpha){
		this.alpha=alpha;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
//		if (isVisible())
//			paintImmediately(getBounds());
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(cmp.derive(alpha));
		super.paintComponent(g2d);
	}
}
