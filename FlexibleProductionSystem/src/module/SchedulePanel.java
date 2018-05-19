package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.MyFrame;
import model.Factory;
import model.Factory.Jobshop;
import model.Factory.Machine;
import panels.GridPanel;
import panels.LeftSidePanel;
import views.VerticalLineSeparator;

/**
 * 生产调度模块
 * 
 * @author Will
 *
 */
public class SchedulePanel extends JPanel {

	public static final int LABEL_HEIGHT = MyFrame.HEIGHT / 20;
	public static final int LABEL_WIDTH = LABEL_HEIGHT * 26 / 10;
	public static final int MARGIN_TOP = 20;
	public static final int MARGIN_LEFT = GridPanel.marginLR;
	public static final int MARGIN = LABEL_WIDTH / 3;

	private LeftSidePanel leftSidePanel;

	// 选择任务
	private JLabel taskLabel = new JLabel("任务选择", JLabel.CENTER);
	// 选择资源
	private JLabel resourceLabel = new JLabel("资源选择", JLabel.CENTER);
	// 参数设置
	private JLabel parameterLabel = new JLabel("参数设置", JLabel.CENTER);
	// 故障设置
	private JLabel faultLabel = new JLabel("故障设置", JLabel.CENTER);
	// 启动
	private JLabel startLabel = new JLabel("启动调度", JLabel.CENTER);

	private List<String> productNames;
	private GridPanel taskGridPanel;

	private List<Factory> factories;
	private List<String> factoryNames;

	private boolean hasAddEnsureAndCancel;
	private JLabel ensureLabel = new JLabel("确定", JLabel.CENTER);
	private JLabel cancelLabel = new JLabel("取消", JLabel.CENTER);
	private List<JLabel> operLabels = Arrays.asList(taskLabel, resourceLabel, parameterLabel, faultLabel, startLabel,
			ensureLabel, cancelLabel);

	private JPanel curPanel;

	public SchedulePanel() {

		readProductDatas();
		readFactoryDatas();
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		taskLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				addEnsureAndCancel();
				addTaskPanel();
			}
		});
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		add(leftSidePanel);

		VerticalLineSeparator separator = new VerticalLineSeparator(5, MyFrame.HEIGHT, Color.WHITE);
		separator.setBounds(MyFrame.WIDTH / 4, 0, separator.getWidth(), separator.getHeight());
		add(separator);

		int curX = MyFrame.WIDTH / 4 + MARGIN_LEFT;
		int curY = MARGIN_TOP;

		for (int i = 0; i < 5; i++) {
			JLabel label = operLabels.get(i);
			label.setBounds(curX, curY, LABEL_WIDTH, LABEL_HEIGHT);
			add(label);
			curX += LABEL_WIDTH + MARGIN;
		}

	}

	private void initViews() {
		// TODO Auto-generated method stub
		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
		setLayout(null);
		setBackground(Color.GRAY);

		leftSidePanel = new LeftSidePanel("生产调度", Arrays.asList("任务配置", "工厂一", "工厂二"));

		// 操作按钮
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 16);
		for (JLabel label : operLabels) {
			label.setBorder(border);
			label.setFont(font);
			label.setForeground(Color.WHITE);
		}

	}

	private void addEnsureAndCancel() {
		if (hasAddEnsureAndCancel)
			return;

		hasAddEnsureAndCancel = true;

		int curX = MyFrame.WIDTH/4+MARGIN_LEFT;
		int curY = MARGIN_TOP + LABEL_HEIGHT + MARGIN_TOP;

		ensureLabel.setBounds(curX, curY, LABEL_WIDTH, LABEL_HEIGHT);
		curX += LABEL_WIDTH + MARGIN;
		cancelLabel.setBounds(curX, curY, LABEL_WIDTH, LABEL_HEIGHT);

		add(ensureLabel);
		add(cancelLabel);
	}

	private void removeEnsureAndCancel() {
		if (!hasAddEnsureAndCancel)
			return;

		remove(cancelLabel);
		remove(ensureLabel);
		hasAddEnsureAndCancel = false;
	}

	private void addTaskPanel() {
		int curX = MyFrame.WIDTH / 4;
		int curY = MARGIN_TOP * 3 + LABEL_HEIGHT * 2;
		if (curPanel == null || (curPanel != null && curPanel != taskGridPanel)) {
			taskGridPanel = new GridPanel(productNames);
			taskGridPanel.setBounds(curX, curY-GridPanel.marginTB, taskGridPanel.getWidth(), taskGridPanel.getHeight());
			if (curPanel != null)
				remove(curPanel);
			add(taskGridPanel);
			curPanel = taskGridPanel;
			repaint();
		}
	}

	private void readProductDatas() {
		productNames = new ArrayList<>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("products.txt")));
			int count = Integer.parseInt(reader.readLine());
			while (count-- > 0) {
				reader.readLine();
				String[] nameAndCount = reader.readLine().split(" ");
				productNames.add(nameAndCount[0]);
				int procedureCount = Integer.parseInt(nameAndCount[1]);
				while (procedureCount-- > 0) {
					reader.readLine();
				}
			}

		} catch (FileNotFoundException e) {
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
	}

	private void readFactoryDatas() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("resource.txt")));
			int factoryCount = Integer.parseInt(reader.readLine());
			factories = new ArrayList<>();
			while (factoryCount-- > 0) {
				// 空行
				reader.readLine();
				String[] nameAndCount = reader.readLine().split(" ");
				int jsCount = Integer.parseInt(nameAndCount[1]);
				List<Jobshop> jobshops = new ArrayList<>();
				while (jsCount-- > 0) {
					int machineCount = Integer.parseInt(reader.readLine());
					List<Machine> machines = new ArrayList<>();
					while (machineCount-- > 0) {
						String[] machineInfo = reader.readLine().split(" ");
						machines.add(new Machine(machineInfo[0], machineInfo[1], machineInfo[2], machineInfo[3]));
					}
					jobshops.add(new Jobshop(machines));
				}

				factories.add(new Factory(nameAndCount[0], jobshops));
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

		factoryNames = new ArrayList<>();
		for (Factory factory : factories)
			factoryNames.add(factory.name);
	}
}
