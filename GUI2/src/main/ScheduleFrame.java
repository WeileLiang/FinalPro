package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import panels.LeftSidePanel;
import views.ShadePanel;

public class ScheduleFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		ScheduleFrame frame = new ScheduleFrame();
		frame.setTitle("柔性生产调度系统");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	public ScheduleFrame() {
		init();
	}

	private void init() {
		setLayout(null);

		LeftSidePanel leftSidePanel = new LeftSidePanel("生产调度", Arrays.asList("工厂一", "工厂二"));
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, WIDTH / 4 + LeftSidePanel.REBOUND_WIDTH, HEIGHT);

		 ShadePanel shadePanel = new ShadePanel();
		 shadePanel.setBounds(0, 0, WIDTH, HEIGHT);
		JLayeredPane layeredPane = getLayeredPane();


		JLabel taskLable = new JLabel("任务配置", JLabel.CENTER);
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 16);
		taskLable.setBorder(border);
		taskLable.setFont(font);
		JLabel setLable = new JLabel("参数设置", JLabel.CENTER);
		setLable.setBorder(border);
		setLable.setFont(font);
		JLabel startLable = new JLabel("启动", JLabel.CENTER);
		startLable.setBorder(border);
		startLable.setFont(font);

		int labelHeight = MyFrame.HEIGHT / 20;

		int labelWidth = labelHeight * 26 / 10;

		taskLable.setBounds(WIDTH / 4 + 30, 15, labelWidth, labelHeight);
		taskLable.setForeground(Color.WHITE);
		setLable.setBounds(WIDTH / 4 + 30 + labelWidth + 30, 15, labelWidth, labelHeight);
		setLable.setForeground(Color.WHITE);
		startLable.setBounds(WIDTH / 4 + 30 + labelWidth + 30+ labelWidth + 30, 15, labelWidth, labelHeight);
		startLable.setForeground(Color.WHITE);
		
		JTextArea area=new JTextArea();
		area.setText("123");
		area.setFont(new Font("黑体", Font.PLAIN, 18));
		JScrollPane scrollPane=new JScrollPane(area);
		int margin=WIDTH/4/5;
		scrollPane.setBounds(WIDTH/4+margin, HEIGHT/8, WIDTH/4*3-margin*2, HEIGHT/4*3);
		
		layeredPane.add(shadePanel, 1);
		layeredPane.add(leftSidePanel, 0);
		layeredPane.add(taskLable,0);
		layeredPane.add(setLable, 0);
		layeredPane.add(startLable, 0);
		layeredPane.add(scrollPane, 0);
	}
}
