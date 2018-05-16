import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Hardneedl
 */
class PanelComposite extends JFrame {
	private static final Dimension minSize = new Dimension(300, 200);
	private static final Dimension maxSize = new Dimension(1024, 768);
	private static final Dimension preferredSize = new Dimension(600, 400);

	public Dimension getMaximumSize() {
		return maxSize;
	}

	public Dimension getMinimumSize() {
		return minSize;
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public String getTitle() {
		return "Frame Title";
	}

	private class CompositePanel extends JPanel {
		private AlphaComposite cmp = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 1);
		private float alpha;

		private void setAlpha(float alpha) {
			this.alpha = alpha;
			if (isVisible())
				paintImmediately(getBounds());
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(cmp.derive(alpha));
			super.paintComponent(g2d);
		}
	}

	PanelComposite() throws HeadlessException {
		init();
		doLay();
		attachListeners();
	}

	private void init() {
	}

	private void doLay() {
		Container container = getContentPane();
		container.setLayout(null);
		CompositePanel p = new CompositePanel();
		p.setAlpha(.1f);
		p.setBackground(Color.GREEN);
		p.setBounds(10, 10, 90, 90);
		JButton b = new JButton("JBUTTON");
		b.setBounds(20, 20, 100, 40);
		container.add(p);
		container.add(b);
		pack();
	}

	private void attachListeners() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new PanelComposite().setVisible(true);
	}
}