package main;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import adapter.OnNotifyListener;
import module.ChoicePanel;
import module.InfoManagePanel;
import module.LoginPanel;
import views.ShadePanel;

public class MyFrame extends JFrame {
	public static MyFrame globalFrame;
	public static final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	// 返回按钮的左上边距
	public static final int RETURN_LEFT_MARGIN = WIDTH / 50;;
	public static final int RETURN_TOP_MARGIN = HEIGHT / 38;;
	
//	static {
//		UIManager.put("ComboBox.arrowButton.background", Color.DARK_GRAY);  
//	}
	
	// 保存添加的面板
	private Stack<JPanel> stack = new Stack<>();
	private ShadePanel shadePanel = new ShadePanel();

	private JLayeredPane jLayeredPane;

	private LoginPanel loginPanel;
	private ChoicePanel choicePanel = new ChoicePanel();

	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setTitle("");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setEsc();
		
		globalFrame=frame;
	}

	public MyFrame() {
		// TODO Auto-generated constructor stub
		measureAndLayout();
		setListeners();
	}

	private void measureAndLayout() {
		setLayout(null);
		shadePanel.setBounds(0, 0, WIDTH, HEIGHT);

		loginPanel = new LoginPanel();
		loginPanel.setBounds((WIDTH - loginPanel.getWidth()) / 2, (HEIGHT - loginPanel.getHeight()) / 2,
				loginPanel.getWidth(), loginPanel.getHeight());

		jLayeredPane = new JLayeredPane();

		jLayeredPane.add(shadePanel, new Integer(0));
		stack.push(shadePanel);
		jLayeredPane.add(loginPanel, new Integer(1));
		stack.push(loginPanel);

		setContentPane(jLayeredPane);
	}

	private void setListeners() {
		loginPanel.setOnNotifyListener(new OnNotifyListener() {

			@Override
			public void notifyParent(int singal) {
				// TODO Auto-generated method stub
				if (choicePanel == null)
					choicePanel = new ChoicePanel();
				
				jLayeredPane.remove(loginPanel);
				jLayeredPane.add(choicePanel, new Integer(1));
				stack.push(choicePanel);
				repaint();
			}
		});

	}

	/**
	 * 在当前面板操作后添加子面板
	 * 
	 * @param panel
	 * @param layer
	 */
	public void addFurtherPanel(JPanel panel, int layer) {
		jLayeredPane.remove(stack.peek());
		jLayeredPane.add(panel, new Integer(layer));
		stack.push(panel);
		repaint();
	}

	/**
	 * 移除当前的面板，添加上一个面板
	 * @param panel
	 */
	public void back2LastPanel() {
		jLayeredPane.remove(stack.pop());
		jLayeredPane.add(stack.peek(), new Integer(1));
		repaint();
	}

	private void setEsc() {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					KeyEvent evt = (KeyEvent) e;
					if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
						System.out.println(123);
						dispose();
						System.exit(0);
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
	}

}
