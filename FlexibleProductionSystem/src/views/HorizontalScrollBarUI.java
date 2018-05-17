package views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * 自定义滚动条UI
 * @author SongFei
 * @date 2015�?5�?18�?
 */
public class HorizontalScrollBarUI extends BasicScrollBarUI {

	@Override
	protected void configureScrollBarColors() {
		// 把手
//		thumbColor = Color.GRAY;
//		thumbHighlightColor = Color.BLUE;
//		thumbDarkShadowColor = Color.BLACK;
//		thumbLightShadowColor = Color.YELLOW;
		
		// 滑道
		trackColor = Color.WHITE;
//		trackHighlightColor = Color.GREEN;
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		super.paintTrack(g, c, trackBounds);
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		// 把绘制区的x，y点坐标定义为坐标系的原点
		// 这句�?定一定要加上啊，不然拖动就失效了
		g.translate(thumbBounds.x, thumbBounds.y); 
		// 设置把手颜色
		g.setColor(Color.BLACK);
		// 画一个圆角矩�?
		// 这里面前四个参数就不多讲了，坐标和宽�?
		// 后两个参数需要注意一下，是用来控制角落的圆角弧度
		g.drawRoundRect(0, 5, thumbBounds.width-1, 6, 5, 5); 
		// 消除锯齿
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// 半�?�明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		// 设置填充颜色，这里设置了渐变，由下往�?
		// g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
		// 填充圆角矩形
		g2.fillRoundRect(0, 5, thumbBounds.width-1, 6, 5, 5);
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton(produceImage("right.png"));
		button.setBorder(null);
		return button;
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton(produceImage("left.png"));
		button.setBorder(null);
		return button;
	}
	
	/**
	 * 获取图片
	 * @param name 图片名称
	 * @return
	 */
	private ImageIcon produceImage(String name) {
		ImageIcon backImage = new ImageIcon(getClass().getClassLoader().getResource(name));
		return backImage;
	}
	
}