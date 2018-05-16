package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

import panels.GridPanel;
import panels.LeftSidePanel;
import views.ShadePanel;

public class ResourceFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		ResourceFrame frame = new ResourceFrame();
		frame.setTitle("柔性生产调度系统");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	public ResourceFrame() {
		init();
	}

	private void init() {
		setLayout(null);

		LeftSidePanel leftSidePanel = new LeftSidePanel("工厂资源配置", Arrays.asList("工厂一", "工厂二"));
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, WIDTH / 4 + LeftSidePanel.REBOUND_WIDTH, HEIGHT);

		GridPanel gridPanel = new GridPanel(Arrays.asList("车间1","车间2"));
		gridPanel.setBounds(WIDTH / 4, 0, WIDTH / 4 * 3, HEIGHT);
		gridPanel.showManagePanel("添加");
		gridPanel.setChosenState(0);
		ShadePanel shadePanel = new ShadePanel();
		shadePanel.setBounds(0, 0, WIDTH, HEIGHT);
		JLayeredPane layeredPane = getLayeredPane();

		JLabel manageLable = new JLabel("取消", JLabel.CENTER);
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 16);
		manageLable.setBorder(border);
		manageLable.setFont(font);

		int labelHeight = MyFrame.HEIGHT / 20;

		int labelWidth = labelHeight * 26 / 10;

		manageLable.setBounds(WIDTH - labelWidth - 50, 15, labelWidth, labelHeight);
		manageLable.setForeground(Color.WHITE);

		layeredPane.add(shadePanel, 1);
		layeredPane.add(leftSidePanel, 0);
		layeredPane.add(gridPanel, 0);
		layeredPane.add(manageLable, 0);
	}
}
