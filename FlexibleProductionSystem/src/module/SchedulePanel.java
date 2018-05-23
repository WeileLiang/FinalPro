package module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import adapter.OnItemClickListener;
import adapter.OnNotifyListener;
import main.MyFrame;
import model.Factory;
import model.Factory.Jobshop;
import model.Factory.Machine;
import panels.FaultSettingPanel;
import panels.GridPanel;
import panels.LeftSidePanel;
import panels.ParameterSettingPanel;
import panels.TimeLinePanel;
import views.HorizontalScrollBarUI;
import views.LineSeparator;
import views.VerticalLineSeparator;
import views.VerticalScrollBarUI;

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
	private GridPanel resoourceGridPanel;

	private boolean hasAddEnsureAndCancel;
	private JLabel ensureLabel = new JLabel("确定", JLabel.CENTER);
	private JLabel cancelLabel = new JLabel("取消", JLabel.CENTER);
	private List<JLabel> operLabels = Arrays.asList(taskLabel, resourceLabel, parameterLabel, faultLabel, startLabel,
			ensureLabel, cancelLabel);

	private JPanel curPanel;

	//显示调度结果
	private JLabel infoLabel;
	
	private List<String> names;
	private List<List<double[]>> chipLists;
	private List<List<String>> procedureLists;
	
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

		resourceLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				addEnsureAndCancel();
				addResourcePanel();
			}
		});
	
		parameterLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				JDialog dialog = new JDialog();
				ParameterSettingPanel panel = new ParameterSettingPanel();
				dialog.setLayout(null);
				dialog.setSize(new Dimension(panel.getWidth(), panel.getHeight()));
				panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
				
				panel.setOnNotifyListener(new OnNotifyListener() {
					
					@Override
					public void notifyParent(int singal) {
						// TODO Auto-generated method stub
						dialog.dispose();
					}
				});
				
				dialog.add(panel);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setLocationRelativeTo(null);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});
		
		faultLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				JDialog dialog = new JDialog();
				FaultSettingPanel panel = new FaultSettingPanel();
				dialog.setLayout(null);
				dialog.setSize(new Dimension(panel.getWidth(), panel.getHeight()));
				panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
				
				panel.setOnNotifyListener(new OnNotifyListener() {
					
					@Override
					public void notifyParent(int singal) {
						// TODO Auto-generated method stub
						dialog.dispose();
					}
				});
				
				dialog.add(panel);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setLocationRelativeTo(null);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});
	
		startLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				addInfoLabel();
				readResult();
				addTimeLinePanel();
				repaint();
			}
		});
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		add(leftSidePanel);

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

		leftSidePanel = new LeftSidePanel("生产调度", Arrays.asList("任务配置", "工厂一", "工厂二"),true);

		// 操作按钮
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 16);
		for (JLabel label : operLabels) {
			label.setBorder(border);
			label.setFont(font);
			label.setForeground(Color.WHITE);
		}

	}

	private void addTimeLinePanel() {

		LineSeparator mSeparator=new LineSeparator(MyFrame.WIDTH*3/4, 2, Color.WHITE);
		mSeparator.setBounds(MyFrame.WIDTH/4, MyFrame.HEIGHT*2/13, mSeparator.getWidth(), mSeparator.getHeight());
		add(mSeparator);
		
		TimeLinePanel timeLinePanel=new TimeLinePanel(names, chipLists, procedureLists);
		JScrollPane scrollPane=new JScrollPane(timeLinePanel);
		scrollPane.setBounds(MyFrame.WIDTH/4,mSeparator.getHeight()+MyFrame.HEIGHT*2/13,MyFrame.WIDTH*3/4-8,MyFrame.HEIGHT*11/13);
		scrollPane.getVerticalScrollBar().setUI(new VerticalScrollBarUI());
		add(scrollPane);
		JScrollBar bar=scrollPane.getVerticalScrollBar();
		bar.setValue(1);
	}
	
	private void addInfoLabel() {
		infoLabel=new JLabel("最优解时间:52   计算时间5.123s");
		infoLabel.setFont(new Font("黑体", Font.PLAIN, 23));
		infoLabel.setForeground(Color.WHITE);
		
		int curX=MyFrame.WIDTH/4+MARGIN_LEFT;
		int curY=MARGIN_TOP+LABEL_HEIGHT;
		infoLabel.setBounds(curX,curY , MyFrame.WIDTH*3/4, MyFrame.HEIGHT*2/13-curY);
		
		add(infoLabel);
	}
	
	private void addEnsureAndCancel() {
		if (hasAddEnsureAndCancel)
			return;

		hasAddEnsureAndCancel = true;

		int curX = MyFrame.WIDTH / 4 + MARGIN_LEFT;
		int curY = MARGIN_TOP + LABEL_HEIGHT + MARGIN_TOP;

		ensureLabel.setBounds(curX, curY, LABEL_WIDTH, LABEL_HEIGHT);
		curX += LABEL_WIDTH + MARGIN;
		cancelLabel.setBounds(curX, curY, LABEL_WIDTH, LABEL_HEIGHT);

		add(ensureLabel);
		add(cancelLabel);

		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				removeEnsureAndCancel();
				remove(curPanel);
				curPanel = null;
				repaint();
			}
		};

		ensureLabel.addMouseListener(adapter);
		cancelLabel.addMouseListener(adapter);
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
		taskGridPanel = new GridPanel(productNames);
		taskGridPanel.setBounds(curX, curY - GridPanel.marginTB, taskGridPanel.getWidth(), taskGridPanel.getHeight());
		add(taskGridPanel);

		curPanel = taskGridPanel;
		taskGridPanel.setGridItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				taskGridPanel.setChosenState(position);
			}
		});

		repaint();

	}

	//读取运行结果
	private void readResult() {
		BufferedReader reader=null;
		names=new ArrayList<>();
		chipLists=new ArrayList<>();
		procedureLists=new ArrayList<>();
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream("results\\result1.txt")));
			int machineCount=Integer.parseInt(reader.readLine());
			
			while (machineCount-->0) {
				reader.readLine();
				String[] machineNameAndChipCount=reader.readLine().split(" ");
				names.add(machineNameAndChipCount[0]);
				
				int chipCount=Integer.parseInt(machineNameAndChipCount[1]);
				List<double[]> chips=new ArrayList<>();
				List<String> procedures=new ArrayList<>();
				while (chipCount-->0) {
					String[] chipAndProcedure=reader.readLine().split(" ");
					chips.add(new double[] {Double.parseDouble(chipAndProcedure[0]),Double.parseDouble(chipAndProcedure[1])});
					procedures.add(chipAndProcedure[2]);
				}
				chipLists.add(chips);
				procedureLists.add(procedures);
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
	}
	
	private void addResourcePanel() {
		int curX = MyFrame.WIDTH / 4;
		int curY = MARGIN_TOP * 3 + LABEL_HEIGHT * 2;
			resoourceGridPanel = new GridPanel(factoryNames);
			resoourceGridPanel.setBounds(curX, curY - GridPanel.marginTB, resoourceGridPanel.getWidth(),
					resoourceGridPanel.getHeight());
			add(resoourceGridPanel);

			curPanel = resoourceGridPanel;
			resoourceGridPanel.setGridItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(int position) {
					// TODO Auto-generated method stub
					resoourceGridPanel.setChosenState(position);
				}
			});

			repaint();
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
