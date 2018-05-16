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

public class InfoFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		InfoFrame frame = new InfoFrame();
		frame.setTitle("柔性生产调度系统");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	private JScrollPane scrollPane;

	JTable table;
	// 定义二维数组作为表格数据
	Object[][] tableData = { new Object[] { "系统管理员", 25, "男", "系统管理" }, new Object[] { "工厂1系统管理员", 25, "男", "子系统管理" },
			new Object[] { "工厂2系统管理员", 25, "男", "子系统管理" }, new Object[] { "工艺管理员", 25, "男", "工艺管理" } };

	// 定义一维数据作为列标题
	Object[] columnTitle = { "姓名", "年龄", "性别", "权限级别" };

	public InfoFrame() {
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

		int curY = HEIGHT / 4;
		table = new JTable(tableData, columnTitle);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(WIDTH / 4, curY, WIDTH / 2, HEIGHT / 3);

		curY += scrollPane.getHeight() + 20;
		JLabel name = new JLabel("姓名：", JLabel.CENTER);
		JLabel age = new JLabel("年龄：", JLabel.CENTER);
		JLabel sex = new JLabel("性别：", JLabel.CENTER);
		JLabel power = new JLabel("权限：", JLabel.CENTER);
		JTextField nameF = new JTextField();
		JTextField ageF = new JTextField();
		JTextField sexF = new JTextField();

		Font font = new Font("黑体", Font.PLAIN, 13);
		Font font2 = new Font("黑体", Font.PLAIN, 15);

		name.setFont(font);
		age.setFont(font);
		sex.setFont(font);

		int curX = WIDTH / 4;
		name.setBounds(curX, curY, WIDTH / 16 / 2, 30);
		nameF.setBounds(curX + name.getWidth(), curY, WIDTH / 16, 30);
		curX += WIDTH / 8;
		age.setBounds(curX, curY, WIDTH / 16 / 2, 30);
		ageF.setBounds(curX + age.getWidth(), curY, WIDTH / 16, 30);
		curX += WIDTH / 8;
		sex.setBounds(curX, curY, WIDTH / 16, 30);
		sexF.setBounds(curX + sex.getWidth(), curY, WIDTH / 16, 30);
		curX += WIDTH / 8;
		power.setBounds(curX, curY, WIDTH / 16, 30);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("工艺管理员");
		comboBox.addItem("系统管理员");
		comboBox.addItem("子系统管理员");
		comboBox.setBounds(curX + sex.getWidth(), curY, WIDTH / 16, 30);

		PressedButton editBtn = new PressedButton("修改");
		PressedButton addBtn = new PressedButton("添加");
		PressedButton deleteBtn = new PressedButton("删除");
		PressedButton returnBtn = new PressedButton("返回");

		curY += 80;
		int gap = WIDTH / 34;
		curX = WIDTH / 4 + gap;
		editBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap*4;
		addBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap*4;
		deleteBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap*4;
		returnBtn.setBounds(curX, curY, gap * 3, 30);
		returnBtn.setForeground(Color.WHITE);
		editBtn.setForeground(Color.WHITE);
		addBtn.setForeground(Color.WHITE);
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setBackground(Color.RED);
		
		JLayeredPane layeredPane = getLayeredPane();
		layeredPane.add(shadePanel, 0);
		layeredPane.add(producer, 0);
		layeredPane.add(scrollPane, 0);
		layeredPane.add(name, 0);
		layeredPane.add(nameF, 0);
		layeredPane.add(age, 0);
		layeredPane.add(ageF, 0);
		layeredPane.add(sex, 0);
		layeredPane.add(sexF, 0);
		layeredPane.add(power, 0);
		layeredPane.add(comboBox, 0);
		layeredPane.add(editBtn, 0);
		layeredPane.add(addBtn, 0);
		layeredPane.add(deleteBtn, 0);
		layeredPane.add(returnBtn, 0);

	}

}
