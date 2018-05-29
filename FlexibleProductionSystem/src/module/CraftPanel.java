package module;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import adapter.OnItemClickListener;
import adapter.OnNotifyListener;
import constant.AnimationUtil;
import main.MyFrame;
import model.Factory;
import model.Product;
import model.Product.Part;
import model.Product.Procedure;
import model.Factory.Jobshop;
import model.Factory.Machine;
import panels.GridPanel;
import panels.JobshopInfoPanel;
import panels.LeftSidePanel;
import panels.PartDetailPanel;
import panels.ProductStructionPanel;
import views.ShadePanel;
import views.VerticalScrollBarUI;

/**
 * 工艺配置模块
 * 
 * @author Will
 *
 */
public class CraftPanel extends JPanel {

	private LeftSidePanel leftSidePanel;
	private List<Factory> factories;

	List<GridPanel> gridPanels;
	private GridPanel curGridPanel;
	private JScrollPane scrollPane;

	// 产品
	private GridPanel productGridPanel;
	// 工件
	private GridPanel partGridPanel;

	private List<Product> products;
	private List<Part> parts;

	public CraftPanel() {

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
				// remove(curGridPanel);
				// curGridPanel = gridPanels.get(position);
				// curGridPanel.setBounds(MyFrame.WIDTH / 4, 0, curGridPanel.getWidth(),
				// curGridPanel.getHeight());
				// add(curGridPanel);
				// repaint();

				if (position == 0) {
					if (curGridPanel != productGridPanel) {
						remove(scrollPane);
						add(productGridPanel);
						curGridPanel = productGridPanel;
						repaint();
					}
				} else {
					if (curGridPanel != partGridPanel) {
						remove(curGridPanel);
						add(scrollPane);
						JScrollBar bar=scrollPane.getVerticalScrollBar();
						bar.setValue(1);
						curGridPanel = partGridPanel;
						repaint();
					}

				}

			}
		});

		productGridPanel.setGridItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				AnimationUtil.doShrinkAnima(productGridPanel.getKthItem(position), new OnNotifyListener() {

					@Override
					public void notifyParent(int singal) {
						// TODO Auto-generated method stub
						Object[][] productDetails = new Object[products.size() + 1][];
						productDetails[0] = new String[] { "工件名称", "数量" };
						Product product = products.get(position);
						for (int i = 0; i < products.size(); i++) {
							productDetails[i + 1] = new String[] { product.parts.get(i), product.counts.get(i) + "" };
						}

						MyFrame.globalFrame.addFurtherPanel(new ProductStructionPanel(productDetails), 1);
					}
				});
			}
		});

		partGridPanel.setGridItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub

				AnimationUtil.doShrinkAnima(partGridPanel.getKthItem(position), new OnNotifyListener() {

					@Override
					public void notifyParent(int singal) {
						// TODO Auto-generated method stub
						List<Procedure> procedures = parts.get(position).procedures;

						Object[][] datas = new Object[procedures.size() + 1][];
						int machineCount = procedures.get(0).times.size();

						datas[0] = new Object[machineCount + 1];
						datas[0][0] = "工序";
						for (int i = 1; i < datas[0].length; i++) {
							datas[0][i] = "M" + i;
						}

						for (int i = 0; i < procedures.size(); i++) {
							datas[i + 1] = new Object[machineCount + 1];
							datas[i + 1][0] = procedures.get(i).name;
							for (int j = 0; j < machineCount; j++) {
								datas[i + 1][j + 1] = procedures.get(i).times.get(j);
							}
						}

						MyFrame.globalFrame.addFurtherPanel(new PartDetailPanel(datas), 1);
					}
				});

			}
		});

		// for (GridPanel gridPanel : gridPanels)
		// gridPanel.setGridItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(int position) {
		// // TODO Auto-generated method stub
		// Jobshop curJobshop =
		// factories.get(leftSidePanel.getCurClickPos()).jobshops.get(position);
		// String title = factories.get(leftSidePanel.getCurClickPos()).name + " "
		// + curGridPanel.getKthItem(position).getLabelText();
		// MyFrame.globalFrame.addFurtherPanel(new
		// JobshopInfoPanel(curJobshop.getMchinedatas(), title), 1);
		// }
		// });
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		// gridPanels.get(0).setBounds(MyFrame.WIDTH / 4, 0,
		// gridPanels.get(0).getWidth(), gridPanels.get(0).getHeight());

		add(leftSidePanel);

		productGridPanel.setBounds(MyFrame.WIDTH / 4, 0, productGridPanel.getWidth(), productGridPanel.getHeight());
		productGridPanel.setAlpha(0.f);
		add(productGridPanel);
		curGridPanel = productGridPanel;

		partGridPanel.setBounds(MyFrame.WIDTH / 4, 0, partGridPanel.getWidth(), partGridPanel.getHeight());
		// add(gridPanels.get(0));
		// curGridPanel = gridPanels.get(0);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setBackground(null);
		setOpaque(false);

		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		// List<String> factotyNames = new ArrayList<>();
		// for (Factory factory : factories)
		// factotyNames.add(factory.name);
		leftSidePanel = new LeftSidePanel("工艺配置", Arrays.asList("产品详情", "工件详情"));

		// 默认显示第一间工厂的车间名称
		// gridPanels = new ArrayList<>();
		// for (int i = 0; i < factories.size(); i++) {
		// gridPanels.add(new GridPanel(factories.get(i).getJobshopNames()));
		// }

		// 产品列表
		List<String> productNames = new ArrayList<>();
		for (Product product : products)
			productNames.add(product.name);
		productGridPanel = new GridPanel(productNames,true);

		List<String> partNames = new ArrayList<>();
		for (Part part : parts)
			partNames.add(part.name);
		partGridPanel = new GridPanel(partNames,true,true);
//		ShadePanel shadePanel=new ShadePanel();
//		shadePanel.setPreferredSize(partGridPanel.getPreferredSize());
//		partGridPanel.setSize(partGridPanel.getPreferredSize().width,partGridPanel.getPreferredSize().height);
//		shadePanel.add(partGridPanel);
		scrollPane=new JScrollPane(partGridPanel);
		scrollPane.setBackground(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(MyFrame.WIDTH/4, 0, MyFrame.WIDTH*3/4-5, MyFrame.HEIGHT);
		scrollPane.getVerticalScrollBar().setUI(new VerticalScrollBarUI());
	}

	private void readDatas() {
		BufferedReader reader = null;
		try {
			// 读取产品信息
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("products.txt")));
			int productCount = Integer.parseInt(reader.readLine());
			products = new ArrayList<>();
			while (productCount-- > 0) {
				// 空行
				reader.readLine();
				String[] nameAndCount = reader.readLine().split(" ");
				int partCount = Integer.parseInt(nameAndCount[1]);
				List<String> partNames = new ArrayList<>();
				List<Integer> counts = new ArrayList<>();
				while (partCount-- > 0) {
					String[] partNameAndCount = reader.readLine().split(" ");
					partNames.add(partNameAndCount[0]);
					counts.add(Integer.parseInt(partNameAndCount[1]));
				}

				products.add(new Product(nameAndCount[0], partNames, counts));
			}

			// 读取工件列表
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("parts.txt")));
			parts = new ArrayList<>();
			String partName = null;
			while ((partName = reader.readLine()) != null) {// 工件的文件名称
				BufferedReader tempReader = new BufferedReader(
						new InputStreamReader(new FileInputStream("parts_detail\\" + partName + ".txt")));
				List<Procedure> procedures = new ArrayList<>();
				String nameAndTimeList = null;
				while ((nameAndTimeList = tempReader.readLine()) != null) {
					String[] nameAndTimeArray = nameAndTimeList.split(" ");
					List<String> times = new ArrayList<>();
					for (int i = 1; i < nameAndTimeArray.length; i++) {
						times.add(nameAndTimeArray[i]);
					}

					procedures.add(new Procedure(nameAndTimeArray[0], times));
				}
				parts.add(new Part(partName, procedures));
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
