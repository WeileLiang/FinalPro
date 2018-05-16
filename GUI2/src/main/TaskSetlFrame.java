package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import constant.Info;
import views.PressedButton;
import views.ShadePanel;

public class TaskSetlFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		TaskSetlFrame frame = new TaskSetlFrame();
		frame.setTitle("任务配置");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	private JScrollPane scrollPane;

	JTable table;
	// 定义二维数组作为表格数据
	Object[][] tableData = { new Object[] { "工厂一" } };

	// 定义一维数据作为列标题
	Object[] columnTitle = { "调度工厂" };

	public TaskSetlFrame() {
		init();
	}

	// 灰色渐变背景
	private ShadePanel shadePanel = new ShadePanel();

	private void init() {
		setLayout(null);
		shadePanel.setBounds(0, 0, WIDTH, HEIGHT);
		JLabel producer = new JLabel();
		ImageIcon icon = new ImageIcon(".\\images\\logo.jpg");
		producer.setIcon(icon);
		producer.setBounds(10, 5, icon.getIconWidth(), icon.getIconHeight());

		int curY = HEIGHT / 6;
		table = new JTable(tableData, columnTitle);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(WIDTH / 4, curY, WIDTH / 2, HEIGHT / 8);

		curY += scrollPane.getHeight() + 20;

		int curX = WIDTH / 4;
		PressedButton addBtn = new PressedButton("添加");
		PressedButton deleteBtn = new PressedButton("删除");
		int marginLR = WIDTH / 12;
		addBtn.setBounds(curX + marginLR, curY, 100, 30);
		deleteBtn.setBounds(WIDTH / 4 * 3 - marginLR-100, curY, 100, 30);
		addBtn.setForeground(Color.WHITE);
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setBackground(Color.RED);

		curY += 30+100;
		
		Object[][] tableData2 = { new Object[] { "工件p1",1 },new Object[] { "工件p2",1 },new Object[] { "工件p3",1 } };

		// 定义一维数据作为列标题
		Object[] columnTitle2 = { "调度工件","数量" };
		
		JTable table2 = new JTable(tableData2, columnTitle2);
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(WIDTH / 4, curY, WIDTH / 2, HEIGHT / 4);

		curY += scrollPane2.getHeight() + 20;

		curX = WIDTH / 4;
		PressedButton addBtn2 = new PressedButton("添加");
		PressedButton deleteBtn2 = new PressedButton("删除");
		
		addBtn2.setBounds(curX + marginLR, curY, 100, 30);
		deleteBtn2.setBounds(WIDTH / 4 * 3 - marginLR-100, curY, 100, 30);
		addBtn2.setForeground(Color.WHITE);
		deleteBtn2.setForeground(Color.WHITE);
		deleteBtn2.setBackground(Color.RED);
		
		PressedButton sureBtn = new PressedButton("确定");
		PressedButton returnBtn = new PressedButton("返回");
		
		curY+=60;
		sureBtn.setBounds(curX + marginLR*2, curY, 100, 30);
		returnBtn.setBounds(WIDTH / 4 * 3 - marginLR*2-100, curY, 100, 30);
		sureBtn.setForeground(Color.WHITE);
		returnBtn.setForeground(Color.WHITE);
		
		JLayeredPane layeredPane = getLayeredPane();
		layeredPane.add(shadePanel, 0);
		layeredPane.add(producer, 0);
		layeredPane.add(scrollPane, 0);
		layeredPane.add(addBtn, 0);
		layeredPane.add(deleteBtn, 0);
		layeredPane.add(scrollPane2, 0);
		layeredPane.add(addBtn2, 0);
		layeredPane.add(deleteBtn2, 0);
		layeredPane.add(sureBtn, 0);
		layeredPane.add(returnBtn, 0);

	}

}
