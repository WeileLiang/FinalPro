package panels;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;

import adapter.ItemClickListener;
import adapter.NotifyListener;
import constant.Info;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import panels.ProductCreatePanel.CreatedProduct;
import utils.AnimationUtil;
import utils.FileUtil;

public class ProductOperationPanel extends OperationPanel {

	public static final String PRODUCT_PATH = ".\\products";

	private static String[] leftItems = { "产品详情", "产品管理" };
	// 产品的名称集合
	private List<String> itemNames;
	// 产品包含的组件集合
	private List<List<String>> jobsItems;

	public ProductOperationPanel(String tag) {
		super(tag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initLeftPanel() {
		// TODO Auto-generated method stub
		leftSidePanel = new LeftSidePanel(tag, Arrays.asList(leftItems));
	}

	@Override
	public void initGridPanel() {
		initGridList();
		gridPanel = new GridPanel(itemNames);
		firstLabeltext = "添加";
	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		leftSidePanel.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub

				if (position == leftItems.length - 1)
					inManageState = true;
				else {
					inManageState = false;
					gridPanel.clearChosenStates();
					gridPanel.hideManagePanel();
				}
				switch (position) {
				case 0:// 产品详情

					break;
				case 1:
					gridPanel.showManagePanel(firstLabeltext);
					break;
				default:
					break;
				}
			}
		});

		gridPanel.setGridItemClickListener(new ItemClickListener() {

			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if (inManageState) {
					gridPanel.setChosenState(position);

				} else {
					AnimationUtil.doShrinkAnima(gridPanel.getKthItem(position));
					showItemInfoDialog(position);
				}

			}
		});

		gridPanel.setManageItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:// 添加
					showCreateDialog();
					break;
				case 1:// 删除
					deleteSelectedFiles();
					break;
				case 2:// 全选
					gridPanel.handleAllStates(true);
					break;
				case 3:// 全不选
					gridPanel.handleAllStates(false);
					break;
				case 4:// 反选
					gridPanel.inverseSelect();
					break;
				default:
					break;
				}
			}
		});

	}

	/**
	 * 读取products文件夹下的产品信息
	 */
	private void initGridList() {
		itemNames = Info.getProductNames(false);
		jobsItems = Info.getJobItemsOfPro();

	}

	
	private void showItemInfoDialog(int position) {
		ItemInfoPanel panel = new ItemInfoPanel(itemNames.get(position), jobsItems.get(position));
		JDialog dialog = new JDialog();
		dialog.setTitle(itemNames.get(position));
		dialog.setSize(panel.getWidth(), panel.getHeight());
		dialog.setLayout(null);
		panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());

		panel.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int index) {
				// TODO Auto-generated method stub
				File file = new File(
						CraftOperationPanel.JOBS_PATH + File.separator + jobsItems.get(position).get(index) + ".txt");
				if (file.exists())
					FileUtil.openFile(file.getAbsolutePath());
			}
		});

		dialog.add(panel);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	private void showCreateDialog() {

		// TODO Auto-generated method stub
		JDialog dialog = new JDialog();
		ProductCreatePanel panel = new ProductCreatePanel(Info.getJobNames());
		dialog.setLayout(null);
		dialog.setSize(new Dimension(panel.getWidth(), panel.getHeight()));
		panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		panel.setNotifyListener(new NotifyListener() {

			@Override
			public void notifyParent(int signalType) {
				createNewProductFile(panel.getNewProduct());
				dialog.dispose();
			}
		});
		dialog.add(panel);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);

	}

	private void deleteSelectedFiles(){
		gridPanel.deleteSelectFiles(Info.PRODUCT_PATH);
		itemNames.clear();
		jobsItems.clear();
		itemNames.addAll(Info.getProductNames(true));
		jobsItems.addAll(Info.getJobItemsOfPro());
		gridPanel.reLayout();
	}
	
	private void createNewProductFile(CreatedProduct product) {
		String fileName = Info.PRODUCT_PATH + File.separator + product.name + ".txt";
		File file = FileUtil.createFile(fileName);
		FileUtil.writeContent2File(file, product.toString());
		
//		initGridList();
		itemNames.clear();
		jobsItems.clear();
		itemNames.addAll(Info.getProductNames(true));
		jobsItems.addAll(Info.getJobItemsOfPro());
		gridPanel.reLayout();
	}

}
