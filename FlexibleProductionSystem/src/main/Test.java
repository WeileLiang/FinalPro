package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import module.InfoManagePanel;
import panels.TablePanel;
import views.HorizontalScrollBarUI;
import views.TableRow;
import views.VerticalScrollBarUI;

public class Test extends JFrame {

	public Test() {
		setLayout(null);
		
		BufferedReader reader=null;
		Object[][] datas=null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream("user_info.txt")));
			int rowCount=Integer.parseInt(reader.readLine());
			datas=new Object[rowCount][];
			for(int i=0;i<rowCount;i++) {
				datas[i]=reader.readLine().split(" ");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TablePanel tablePanel=new TablePanel(datas, new int[] {1,1,1,1});
		JScrollPane scrollPane=new JScrollPane(tablePanel);
		scrollPane.setBounds(0,0,MyFrame.WIDTH*2/3,MyFrame.HEIGHT/2);
		add(scrollPane);
//		getContentPane().setBackground(Color.GRAY);
//		TableRow row = new TableRow(new Object[] { "梁慰乐", 26, "男", "系统管理" }, new int[] { 1, 1, 1, 1 },true,false);
//		row.setBounds(50, 50, row.getWidth(), row.getHeight());
//		TableRow row2 = new TableRow(new Object[] { "梁慰乐", 26, "男", "系统管理" }, new int[] { 1, 1, 1, 1 });
//		row2.setBounds(50, 50+row.getHeight(), row.getWidth(), row.getHeight());
//		add(row);
//		add(row2);
	}

	public static void main(String[] args) {
		Test jf = new Test();
		jf.setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setVisible(true);

	}

	// public static void main(String[] args) {
	// JFrame jf = new JFrame("���Դ���");
	// jf.setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
	// jf.setLocationRelativeTo(null);
	// jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//
	// JLayeredPane layeredPane = new JLayeredPane();
	//
	// JTextField textField=new JTextField("111");
	// textField.setBounds(0, 0, 100, 100);
	//
	// layeredPane.add(textField, new Integer(1));
	//
	// JButton btn=new JButton("113");
	// btn.setBounds(200, 200, 100, 100);
	// layeredPane.add(btn, new Integer(1));
	//
	// btn.addActionListener(new ActionListener() {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// // TODO Auto-generated method stub
	// layeredPane.remove(0);
	// System.out.println(111);
	// layeredPane.invalidate();
	// }
	// });
	//
	// // ����: 100
	//// JPanel panel_100_1 = new ShadePanel();
	//// panel_100_1.setSize(MyFrame.WIDTH,MyFrame.HEIGHT);
	//// layeredPane.add(panel_100_1, 100);
	////
	//// // ����: 200, ����λ��: 0�����ڶ�����
	//// JPanel panel_200_0 = new LoginPanel();
	//// layeredPane.add(panel_200_0, new Integer(200));
	//
	//// // ����: 200, ����λ��: 1
	//// JPanel panel_200_1 = createPanel(Color.CYAN, "L=200, P=1", 110, 110, 100,
	// 100);
	//// layeredPane.add(panel_200_1, new Integer(200), 1);
	////
	//// // ����: 300
	//// JPanel panel_300 = createPanel(Color.YELLOW, "L=300", 150, 150, 100, 100);
	//// layeredPane.add(panel_300, new Integer(300));
	//
	// jf.setContentPane(layeredPane);
	// jf.setVisible(true);
	// }

	/**
	 * ����һ����������������ڰ���һ��ˮƽ�������, ��ֱ���򶥲�����ı�ǩ��
	 *
	 * @param bg
	 *            ��������
	 * @param text
	 *            �����ڱ�ǩ��ʾ���ı�
	 * @param x
	 *            �����ĺ�������
	 * @param y
	 *            ������������
	 * @param width
	 *            �����Ŀ��
	 * @param height
	 *            �����ĸ߶�
	 * @return
	 */
	private static JPanel createPanel(Color bg, String text, int x, int y, int width, int height) {
		// ����һ�� JPanel, ʹ�� 1 �� 1 �е����񲼾�
		JPanel panel = new JPanel(new GridLayout(1, 1));

		// ����������λ�úͿ��
		panel.setBounds(x, y, width, height);

		// ���� panel �ı���
		panel.setOpaque(true);
		panel.setBackground(bg);

		// ������ǩ��������Ӧ����
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);

		// ��ӱ�ǩ������
		panel.add(label);

		return panel;
	}

}
