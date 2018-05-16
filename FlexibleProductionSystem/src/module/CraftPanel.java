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
import main.MyFrame;
import model.Factory;
import model.Product;
import model.Product.Part;
import model.Factory.Jobshop;
import model.Factory.Machine;
import panels.GridPanel;
import panels.JobshopInfoPanel;
import panels.LeftSidePanel;

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
	
	//产品
	private GridPanel productGridPanel;
	//工件
	private GridPanel partGridPanel;
	
	private List<Product> products;
	private List<Part> parts;
	
	public CraftPanel() {

		readDatas();
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void setListeners() {
		leftSidePanel.setItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
//				remove(curGridPanel);
//				curGridPanel = gridPanels.get(position);
//				curGridPanel.setBounds(MyFrame.WIDTH / 4, 0, curGridPanel.getWidth(), curGridPanel.getHeight());
//				add(curGridPanel);
//				repaint();
			}
		});

		productGridPanel.setGridItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		for (GridPanel gridPanel : gridPanels)
//			gridPanel.setGridItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(int position) {
//					// TODO Auto-generated method stub
//					Jobshop curJobshop = factories.get(leftSidePanel.getCurClickPos()).jobshops.get(position);
//					String title = factories.get(leftSidePanel.getCurClickPos()).name + " "
//							+ curGridPanel.getKthItem(position).getLabelText();
//					MyFrame.globalFrame.addFurtherPanel(new JobshopInfoPanel(curJobshop.getMchinedatas(), title), 1);
//				}
//			});
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
//		gridPanels.get(0).setBounds(MyFrame.WIDTH / 4, 0, gridPanels.get(0).getWidth(), gridPanels.get(0).getHeight());

		add(leftSidePanel);
		
		productGridPanel.setBounds(MyFrame.WIDTH/4, 0, productGridPanel.getWidth(), productGridPanel.getHeight());
		add(productGridPanel);
//		add(gridPanels.get(0));
//		curGridPanel = gridPanels.get(0);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setBackground(null);
		setOpaque(false);

		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

//		List<String> factotyNames = new ArrayList<>();
//		for (Factory factory : factories)
//			factotyNames.add(factory.name);
		leftSidePanel = new LeftSidePanel("工艺配置", Arrays.asList("产品详情","工件详情"));

		// 默认显示第一间工厂的车间名称
//		gridPanels = new ArrayList<>();
//		for (int i = 0; i < factories.size(); i++) {
//			gridPanels.add(new GridPanel(factories.get(i).getJobshopNames()));
//		}
		
		//产品列表
		List<String > productNames=new ArrayList<>();
		for(Product product:products) productNames.add(product.name);
		productGridPanel=new GridPanel(productNames);

	}

	private void readDatas() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("products.txt")));
			int productCount = Integer.parseInt(reader.readLine());
			products = new ArrayList<>();
			while (productCount-- > 0) {
				// 空行
				reader.readLine();
				String[] nameAndCount = reader.readLine().split(" ");
				int partCount = Integer.parseInt(nameAndCount[1]);
				List<String> partNames = new ArrayList<>();
				List<Integer> counts=new ArrayList<>();
				while (partCount-- > 0) {
					String[] partNameAndCount=reader.readLine().split(" ");
					partNames.add(partNameAndCount[0]);
					counts.add(Integer.parseInt(partNameAndCount[1]));
				}
				
				products.add(new Product(nameAndCount[0], partNames, counts));
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
}
