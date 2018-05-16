package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import adapter.OnItemClickListener;
import main.MyFrame;
import views.HorizontalScrollBarUI;
import views.PressedButton;
import views.VerticalScrollBarUI;

/**
 * 展示用户信息
 * 
 * @author Will
 *
 */
public class MaterialInfoPanel extends JPanel {

	public static final int MARGIN_TOP = MyFrame.HEIGHT / 6;
	// 不放置图片
	public static final int PHOTO_SPACE_WIDTH = 0;

	public static final int TABLE_WIDTH = MyFrame.WIDTH * 2 / 3;
	public static final int TABLE_HEIGHT = MyFrame.HEIGHT / 2;

//	private PressedButton changePhotoBtn;

	private String[] btnTexts = { "修改", "添加", "删除", "取消", };
	private PressedButton[] OperBtns;

	// 用于修改选中行的数据
	private JTextField[] dataFields;
	private JLabel[] dataLabels;

//	private String[] rights = { "", "系统管理员", "子系统管理员", "工艺管理员" };
//	private JComboBox comboBox;

	// 用户图片
//	private JLabel photo;
//	private ImageIcon icon;
	private TablePanel tablePanel;

	//表格数据
//	private Object[][] datas;
	
	private JLabel returnLabel;
	
	public MaterialInfoPanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		tablePanel.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				for(int i=0;i<dataFields.length;i++) {
					dataFields[i].setText(" "+tablePanel.getTableRow(position)[i]);
				}
				
//				for(int i=1;i<rights.length;i++) {
//					if(tablePanel.getTableRow(position)[3].toString().equals(rights[i])) {
//						comboBox.setSelectedIndex(i);
//						break;
//					}
//						
//				}
			}
		});
		
		returnLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				MyFrame.globalFrame.back2LastPanel();
			}
		});
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		returnLabel.setBounds(MyFrame.RETURN_LEFT_MARGIN, MyFrame.RETURN_TOP_MARGIN, 100, 50);
		add(returnLabel);
//		photo.setBounds((PHOTO_SPACE_WIDTH - icon.getIconWidth()) / 2, MARGIN_TOP, icon.getIconWidth(),
//				icon.getIconHeight());
		int btnWidth = MyFrame.WIDTH / 10;
		int btnHeight = MyFrame.HEIGHT / 20;

//		changePhotoBtn.setBounds((PHOTO_SPACE_WIDTH - btnWidth) / 2, MARGIN_TOP + photo.getHeight() + 20, btnWidth,
//				btnHeight);
//		changePhotoBtn.setForeground(Color.WHITE);
		JPanel grayPanel=new JPanel();
		grayPanel.setBackground(Color.GRAY);
		grayPanel.setLayout(null);
		tablePanel.setBounds(0, 0, tablePanel.getPreferredSize().width, tablePanel.getPreferredSize().height);
		grayPanel.add(tablePanel);
		grayPanel.setPreferredSize(new Dimension(tablePanel.getPreferredSize().width, tablePanel.getPreferredSize().height));;
		JScrollPane scrollPane = new JScrollPane(grayPanel);
//		scrollPane.getViewport().setBackground(Color.GRAY);
		scrollPane.setBounds(PHOTO_SPACE_WIDTH + (MyFrame.WIDTH - PHOTO_SPACE_WIDTH - TABLE_WIDTH) / 2, MARGIN_TOP,
				TABLE_WIDTH, TABLE_HEIGHT);
		scrollPane.getHorizontalScrollBar().setUI(new HorizontalScrollBarUI());
		scrollPane.getVerticalScrollBar().setUI(new VerticalScrollBarUI());
		add(scrollPane);
		
		// 放置显示选中行信息的label和field
		int itemWidth = MyFrame.WIDTH * 3 / 4 / dataLabels.length;
		int labelWidth = itemWidth / 3;
		int fieldWidth = itemWidth * 2 / 3;
		int labelHeight = labelWidth / 3;

		int curX = MyFrame.WIDTH / 4 / 2;
		int curY = MARGIN_TOP + scrollPane.getHeight() + 40;

		for (int i = 0; i < dataLabels.length; i++) {
			dataLabels[i].setBounds(curX, curY, labelWidth, labelHeight);
			curX += labelWidth;
			dataFields[i].setBounds(curX, curY, fieldWidth, labelHeight);
			curX += fieldWidth;

			add(dataLabels[i]);
			add(dataFields[i]);
		}

		// 设置权限的下拉选项
//		dataLabels[dataFields.length - 1].setBounds(curX, curY, labelWidth, labelHeight);
//		add(dataLabels[dataLabels.length - 1]);
//		dataLabels[dataFields.length - 1].setText("权限：");
//		curX += labelWidth;
////		comboBox.setBounds(curX, curY, fieldWidth, labelHeight);
////		add(comboBox);

		// 添加四个操作按钮
		itemWidth = MyFrame.WIDTH * 2 / 3 / btnTexts.length;
		curX = MyFrame.WIDTH / 3 / 2;
		curY += labelHeight+40;
		int margin = (itemWidth - btnWidth) / 2;
		for (int i = 0; i < btnTexts.length; i++) {
			OperBtns[i].setBounds(curX + margin, curY, btnWidth, btnHeight);
			curX += itemWidth;
			add(OperBtns[i]);
		}

//		add(photo);
//		add(changePhotoBtn);
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setBackground(null);
		setOpaque(false);

		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		returnLabel = new JLabel("< 返回");
		Font returnfont = new Font("黑体", Font.PLAIN, 18);
		returnLabel.setFont(returnfont);
		returnLabel.setForeground(Color.WHITE);
		// 头像
//		photo = new JLabel();
//		icon = new ImageIcon(".\\image\\photo.jpg");
//		photo.setIcon(icon);

		// 更改头像
//		changePhotoBtn = new PressedButton("更改头像");
		Font btnFont = new Font("黑体", Font.PLAIN, 17);
		Border btnborder = BorderFactory.createLineBorder(Color.WHITE, 1);
//		changePhotoBtn.setFont(btnFont);
//		changePhotoBtn.setBorder(btnborder);

		// 表格
		tablePanel = new TablePanel(readDatas());

		// 选中行的显示和修改
		Object[] tableHead = tablePanel.getTableRow(0);
		dataLabels = new JLabel[tableHead.length];
		dataFields = new JTextField[tableHead.length];

		Font labelFont = new Font("黑体", Font.PLAIN, 16);
		Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
		for (int i = 0; i < tableHead.length; i++) {
			dataLabels[i] = new JLabel(tableHead[i].toString() + "：", JLabel.RIGHT);
			dataFields[i] = new JTextField();

			dataLabels[i].setFont(labelFont);
			dataLabels[i].setForeground(Color.GRAY);
			dataFields[i].setFont(labelFont);
			dataFields[i].setBorder(border);
		}

		// 权限的下拉选择框
//		comboBox = new JComboBox<>(rights);
//		comboBox.setBorder(border);
//		comboBox.setFont(labelFont);

		// 四个操作按钮
		OperBtns = new PressedButton[btnTexts.length];
		for (int i = 0; i < btnTexts.length; i++) {
			OperBtns[i] = new PressedButton(btnTexts[i]);
			OperBtns[i].setFont(btnFont);
			OperBtns[i].setForeground(Color.WHITE);
			OperBtns[i].setBorder(btnborder);
		}

		OperBtns[2].setBackground(Color.RED);
		OperBtns[2].setOriginColor(Color.RED);
	}

	//表格
	private Object[][] readDatas() {
		BufferedReader reader = null;
		Object[][] datas = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("material_info.txt")));
			int rowCount = Integer.parseInt(reader.readLine());
			datas = new Object[rowCount][];
			for (int i = 0; i < rowCount; i++) {
				datas[i] = reader.readLine().split(" ");
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
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return datas;
	}

}
