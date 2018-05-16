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

public class MachineFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		MachineFrame frame = new MachineFrame();
		frame.setTitle("������������ϵͳ");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	private JScrollPane scrollPane;

	JTable table;
	// �����ά������Ϊ�������
	Object[][] tableData = {/* new Object[] { "���칤λ", "��е��", 1 }, new Object[] { "��װѹ������λ", "��е��", 1 },
			new Object[] { "����չ�λ", "�Զ���ר��", 1 }, new Object[] { "�����λ", "�Զ���ר��", 1 },
			new Object[] { "��⹤λ", "�Զ���ר��", 1 }, new Object[] { "��⹤λ", "��е��", 1 },
			new Object[] { "�ϵ��̹�λ", "��е��", 1 }, new Object[] { "����ǩ��λ", "�Զ���ר��", 1 },
			new Object[] { "���ع�λ", "�Զ���ר��", 1,"����" },*/new Object[] { "m1", "����", 1,"����" },new Object[] { "m2", "����", 1 ,"����"},new Object[] { "m3", "����", 1 ,"����"} };

	// ����һά������Ϊ�б���
	Object[] columnTitle = { "�豸����", "�豸����", "����","����" };

	public MachineFrame() {
		init();
	}

	// ��ɫ���䱳��
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
		scrollPane.setBounds(WIDTH / 3, curY, WIDTH / 2, HEIGHT / 3);

		curY += scrollPane.getHeight() + 20;
		JLabel name = new JLabel("�豸���ƣ�", JLabel.CENTER);
		JLabel age = new JLabel("�豸���ͣ�", JLabel.CENTER);
		JLabel sex = new JLabel("������", JLabel.CENTER);
		JTextField nameF = new JTextField();
		JTextField ageF = new JTextField();
		JTextField sexF = new JTextField();

		Font font = new Font("����", Font.PLAIN, 13);
		Font font2 = new Font("����", Font.PLAIN, 15);

		name.setFont(font);
		age.setFont(font);
		sex.setFont(font);

		int curX = WIDTH / 3;
		name.setBounds(curX, curY, WIDTH / 15, 30);
		nameF.setBounds(curX + name.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;
		age.setBounds(curX, curY, WIDTH / 15, 30);
		ageF.setBounds(curX + age.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;
		sex.setBounds(curX, curY, WIDTH / 15, 30);
		sexF.setBounds(curX + sex.getWidth(), curY, WIDTH / 12, 30);
		curX += WIDTH / 6;

		PressedButton editBtn = new PressedButton("�޸�");
		PressedButton addBtn = new PressedButton("���");
		PressedButton deleteBtn = new PressedButton("ɾ��");
		PressedButton returnBtn = new PressedButton("����");

		curY += 80;
		int gap = WIDTH / 34;
		curX = WIDTH / 3 + gap;
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

		JLabel machineLabel = new JLabel();
		ImageIcon machineIcon = new ImageIcon(".\\images\\pic_shangdipan.png");
		machineLabel.setIcon(machineIcon);

		int x = (WIDTH / 3 - machineIcon.getIconWidth()) / 2;
		int y = HEIGHT / 4 + (HEIGHT / 3 - machineIcon.getIconHeight()) / 2;
		machineLabel.setBounds(x, y, machineIcon.getIconWidth(), machineIcon.getIconHeight());

		JLabel titleLabel=new JLabel("����01",JLabel.CENTER);
		titleLabel.setFont(new Font("����", Font.PLAIN, 40));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds((WIDTH-300)/2, (HEIGHT/4-50)/2, 300, 50);
		
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
		layeredPane.add(machineLabel, 0);
		layeredPane.add(titleLabel, 0);

	}

}
