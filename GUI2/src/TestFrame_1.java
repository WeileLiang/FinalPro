import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class TestFrame_1 extends JFrame {
	/** Creates a new instance of TestFrame */
	public TestFrame_1() {
		Container c = getContentPane();
		UIManager.put("ScrollBar.thumbDarkShadow", Color.darkGray);
		UIManager.put("ScrollBar.thumb", Color.yellow);
		UIManager.put("ScrollBar.thumbShadow", Color.black);
		JScrollBar bar = new JScrollBar(SwingConstants.HORIZONTAL, 0, 10, 0, 100);
		c.add(bar, "South");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		JFrame f = new TestFrame_1();
		f.setSize(640, 480);
		f.show();
	}

}