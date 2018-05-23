package module;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import adapter.OnItemClickListener;
import adapter.OnNotifyListener;
import constant.AnimationUtil;
import main.MyFrame;
import model.Factory;
import model.Factory.Jobshop;
import model.Factory.Machine;
import panels.GridPanel;
import panels.JobshopInfoPanel;
import panels.LeftSidePanel;

/**
 * 工厂资源模块
 * 
 * @author Will
 *
 */
public class ResourcePanel extends JPanel {

	private LeftSidePanel leftSidePanel;
	private List<Factory> factories;

	List<GridPanel> gridPanels;
	private GridPanel curGridPanel;

	public ResourcePanel() {

		readDatas();
		initViews();
		measureAndLayout();
		setListeners();

		doReboundAnima();
	}

	private void setListeners() {
		leftSidePanel.setItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				remove(curGridPanel);
				curGridPanel = gridPanels.get(position);
				curGridPanel.setBounds(MyFrame.WIDTH / 4, 0, curGridPanel.getWidth(), curGridPanel.getHeight());
				add(curGridPanel);
				repaint();
			}
		});

		for (GridPanel gridPanel : gridPanels)
			gridPanel.setGridItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(int position) {
					// TODO Auto-generated method stub
					AnimationUtil.doShrinkAnima(gridPanel.getKthItem(position), new OnNotifyListener() {

						@Override
						public void notifyParent(int singal) {
							// TODO Auto-generated method stub
							Jobshop curJobshop = factories.get(leftSidePanel.getCurClickPos()).jobshops.get(position);
							String title = factories.get(leftSidePanel.getCurClickPos()).name + " "
									+ curGridPanel.getKthItem(position).getLabelText();
							MyFrame.globalFrame
									.addFurtherPanel(new JobshopInfoPanel(curJobshop.getMchinedatas(), title), 1);
						}
					});
				}
			});
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		gridPanels.get(0).setBounds(MyFrame.WIDTH / 4, 0, gridPanels.get(0).getWidth(), gridPanels.get(0).getHeight());

		add(leftSidePanel);
		gridPanels.get(0).setAlpha(0.f);
		add(gridPanels.get(0));
		curGridPanel = gridPanels.get(0);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setBackground(null);
		setOpaque(false);

		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		List<String> factotyNames = new ArrayList<>();
		for (Factory factory : factories)
			factotyNames.add(factory.name);
		leftSidePanel = new LeftSidePanel("工厂资源配置", factotyNames);

		// 默认显示第一间工厂的车间名称
		gridPanels = new ArrayList<>();
		for (int i = 0; i < factories.size(); i++) {
			gridPanels.add(new GridPanel(factories.get(i).getJobshopNames()));
		}

	}

	private void readDatas() {
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

	}

	private void doReboundAnima() {
		AnimationUtil.doSlideAima(leftSidePanel, -leftSidePanel.getWidth(), 0, 0, 0, AnimationUtil.SLIDE_TIME, 0,
				new OnNotifyListener() {

					@Override
					public void notifyParent(int singal) {
						// TODO Auto-generated method stub
						AnimationUtil.doSlideAima(leftSidePanel, 0, -LeftSidePanel.REBOUND_WIDTH, 0, 0,
								AnimationUtil.REBOUND_TIME, 0, new OnNotifyListener() {

									@Override
									public void notifyParent(int singal) {
										// TODO Auto-generated method stub
										curGridPanel.doAlphaInAnim(0);
									}
								});
					}
				});
	}
}
