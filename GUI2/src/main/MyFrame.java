package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.oracle.jrockit.jfr.Producer;

import adapter.ItemClickListener;
import adapter.NotifyListener;
import panels.ChoicePanel;
import panels.CraftOperationPanel;
import panels.DispatchPanel;
import panels.LoginPanel;
import panels.OperationPanel;
import panels.ProductOperationPanel;
import panels.ResourceOperationPanel;
import views.ShadePanel;

public class MyFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setTitle("柔性生产调度系统");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
		frame.setVisible(true);
	}

	private JLayeredPane layeredPane;
	// 灰色渐变背景
	private ShadePanel shadePanel = new ShadePanel();
	// 登录界面
	private LoginPanel loginPanel = new LoginPanel();
	// 操作选择界面
	private ChoicePanel choicePanel = new ChoicePanel();

	private OperationPanel operationPanel;
	private OperationPanel[] operationPanels = new OperationPanel[4];
	private DispatchPanel dispatchPanel;

	public MyFrame() {

		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		// TODO Auto-generated method stub

	}

	private void measureAndLayout() {
		setLayout(null);
		shadePanel.setBounds(0, 0, WIDTH, HEIGHT);

		int[] loginXY = computeXY(WIDTH, HEIGHT, loginPanel.getWidth(), loginPanel.getHeight());
		loginPanel.setBounds(loginXY[0], loginXY[1], loginPanel.getWidth(), loginPanel.getHeight());

		choicePanel.setBounds(0, 0, choicePanel.getWidth(), choicePanel.getHeight());

		layeredPane = getLayeredPane();

		JLabel producer=new JLabel();
		ImageIcon icon=new ImageIcon(".\\images\\logo.jpg");
		producer.setIcon(icon);
		producer.setBounds(10,5,icon.getIconWidth(),icon.getIconHeight());
		
		layeredPane.add(shadePanel, 0);
		layeredPane.add(choicePanel, 0);
		layeredPane.add(loginPanel, 0);
		layeredPane.add(producer, 0);
		// add(loginPanel);
		// add(choicePanel);
		// add(shadePanel);
	}

	private void setListeners() {
		loginPanel.setNotifyListener(new NotifyListener() {

			public void notifyParent(int signalType) {
				// TODO Auto-generated method stub
				switch (signalType) {
				case LoginPanel.LOGIN_EXIT_SIGNAL:

					choicePanel.doAlphaAnim(ChoicePanel.ANIM_IN);
					break;

				default:
					break;
				}
			}
		});

		// 点击其中一个操作选项后就加载对应的操作详情页
		choicePanel.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				layeredPane.remove(choicePanel);
				if (position == 3)
					addDispatchPanel();
				else
					addOperationPanel(position);
			}
		});

	}

	private void addDispatchPanel() {
		if(dispatchPanel==null) {
			dispatchPanel = new DispatchPanel();
			dispatchPanel.setBounds(0, 0, dispatchPanel.getWidth(), dispatchPanel.getHeight());
			layeredPane.add(dispatchPanel, 0);
			dispatchPanel.setNotifyListener(new NotifyListener() {
				
				@Override
				public void notifyParent(int signalType) {
					// TODO Auto-generated method stub
					layeredPane.remove(dispatchPanel);
					readdChoicePanel();
				}
			});
		}else readdDispatchPanel();
	}

	private void addOperationPanel(int position) {
		operationPanel = operationPanels[position];
		if (operationPanels[position] == null) {
			if (position == 0)
				operationPanels[position] = new ProductOperationPanel(ChoicePanel.OPERATION_NAMES[position]);
			else if (position == 1)
				operationPanels[position] = new ResourceOperationPanel(ChoicePanel.OPERATION_NAMES[position]);
			else if (position == 2)
				operationPanels[position] = new CraftOperationPanel(ChoicePanel.OPERATION_NAMES[position]);

			// operationPanel = new
			// CraftOperationPanel(ChoicePanel.OPERATION_NAMES[position]);
			operationPanels[position].setBounds(0, 0, operationPanels[position].getWidth(),
					operationPanels[position].getHeight());
			layeredPane.add(operationPanels[position], 0);

			operationPanels[position].setNotifyListener(new NotifyListener() {

				@Override
				public void notifyParent(int signalType) {
					// TODO Auto-generated method stub
					layeredPane.remove(operationPanels[position]);
					readdChoicePanel();
				}
			});

		} else {
			readdOperationPanel(position);
			// operationPanel.setVisible(true);
		}

	}

	public void readdOperationPanel(int position) {
		layeredPane.add(operationPanels[position], 0);
		operationPanels[position].doReboundSlideInAnim();
		operationPanels[position].doAlpahInAima();
	}
	
	public void readdDispatchPanel() {
		layeredPane.add(dispatchPanel, 0);
		dispatchPanel.doReboundSlideInAnim();
		dispatchPanel.doAlphaOutAnim();
	}

	public void readdChoicePanel() {
		layeredPane.add(choicePanel, 0);
		// choicePanel.setVisible(true);
		choicePanel.doAlphaAnim(ChoicePanel.ANIM_IN);
	}

	public static int[] computeXY(int parentWidth, int parentHeight, int childWidth, int childHeight) {
		int x = (parentWidth - childWidth) / 2;
		int y = (parentHeight - childHeight) / 2;

		return new int[] { x, y };
	}

}
