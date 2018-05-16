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

public class MaterialFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		MaterialFrame frame = new MaterialFrame();
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
	Object[][] tableData = { new Object[] { "冷凝器", "XH007", 10 }, new Object[] { "压缩机", "YXH006", 13 },
			new Object[] { "四通阀", "SXH004", 17 }, new Object[] { "高低压管", "YGXH2134", 23 },
			new Object[] { "高低压阀", "YFXH2514", 28 }, new Object[] { "风扇", "FSXH55541", 29 },
			new Object[] { "电机", "DJXH2154", 16 }, new Object[] { "叶片", "YPXH0121", 22 },
			new Object[] { "电控板", "DKBXH1021", 30 } };

	// 定义一维数据作为列标题
	Object[] columnTitle = { "物料名称", "型号规格", "数量" };

	public MaterialFrame() {
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
		JLabel name = new JLabel("物料名称：", JLabel.CENTER);
		JLabel age = new JLabel("型号规格：", JLabel.CENTER);
		JLabel sex = new JLabel("数量：", JLabel.CENTER);
		JTextField nameF = new JTextField();
		JTextField ageF = new JTextField();
		JTextField sexF = new JTextField();

		Font font = new Font("黑体", Font.PLAIN, 13);
		Font font2 = new Font("黑体", Font.PLAIN, 15);

		name.setFont(font);
		age.setFont(font);
		sex.setFont(font);

		int curX = WIDTH / 4;
		name.setBounds(curX, curY, WIDTH / 15 , 30);
		nameF.setBounds(curX + name.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;
		age.setBounds(curX, curY, WIDTH / 15, 30);
		ageF.setBounds(curX + age.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;
		sex.setBounds(curX, curY, WIDTH / 15, 30);
		sexF.setBounds(curX + sex.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;
		

		PressedButton editBtn = new PressedButton("修改");
		PressedButton addBtn = new PressedButton("添加");
		PressedButton deleteBtn = new PressedButton("删除");
		PressedButton returnBtn = new PressedButton("返回");

		curY += 80;
		int gap = WIDTH / 34;
		curX = WIDTH / 4 + gap;
		editBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap * 4;
		addBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap * 4;
		deleteBtn.setBounds(curX, curY, gap * 3, 30);
		curX += gap * 4;
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
		layeredPane.add(editBtn, 0);
		layeredPane.add(addBtn, 0);
		layeredPane.add(deleteBtn, 0);
		layeredPane.add(returnBtn, 0);

	}

}
