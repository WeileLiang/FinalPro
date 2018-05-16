package panels;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import adapter.ItemClickListener;
import adapter.NotifyListener;
import constant.Info;
import main.MyFrame;
import utils.AnimationUtil;
import utils.FileUtil;

public class CraftOperationPanel extends OperationPanel {

	public static final String JOBS_PATH = ".\\jobs";

	private static String[] leftItems = { "��������", "������" };

	// ��������Ƽ���
	private List<String> itemNames;
	// // ����ļ���·������
	// private List<String> itemPaths;

	public CraftOperationPanel(String tag) {
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
		// TODO Auto-generated method stub
		initRightItems();
		gridPanel = new GridPanel(itemNames);

		firstLabeltext = "����";
	}

	private void showCreateDialog() {

		// TODO Auto-generated method stub
		JDialog dialog = new JDialog();

		JTable table;
		// �����ά������Ϊ�������
		Object[][] tableData = { new Object[] { "o1-1", 2, 3, 2.5 }, new Object[] { "o1-2", 1, 1, 0.5 },
				new Object[] { "o1-3", 3, 1.5, 0.5 }, };
		// ����һά������Ϊ�б���
		Object[] columnTitle = { "����", "m1��Сʱ��", "m2��Сʱ��", "m3��Сʱ��" };
		table = new JTable(tableData, columnTitle);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, MyFrame.WIDTH / 3, MyFrame.HEIGHT / 4);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(MyFrame.WIDTH / 3, MyFrame.HEIGHT / 4);
		panel.add(scrollPane);

		// ResourceCreatePanel panel = new ResourceCreatePanel(Info.getMachineNames());
		// dialog.setLayout(null);
		dialog.setSize(new Dimension(panel.getWidth(), panel.getHeight()));
		panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		// panel.setNotifyListener(new NotifyListener() {
		//
		// @Override
		// public void notifyParent(int signalType) {
		//// createNewProductFile(panel.getNewProduct());
		// createNewJobshopFile(panel.getNewJobshop());
		// dialog.dispose();
		// }
		// });
		dialog.add(panel);
		dialog.setTitle("����p1");
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		gridPanel.setGridItemClickListener(new ItemClickListener() {

			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if (inManageState) {
					gridPanel.setChosenState(position);

				} else {
					AnimationUtil.doShrinkAnima(gridPanel.getKthItem(position));
					// FileUtil.openFile(Info.JOBS_PATH+File.separator+itemNames.get(position)+".txt");
					showCreateDialog();
				}

			}
		});

		gridPanel.setManageItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:// ����

					break;
				case 1:// ɾ��
					break;
				case 2:// ȫѡ
					gridPanel.handleAllStates(true);
					break;
				case 3:// ȫ��ѡ
					gridPanel.handleAllStates(false);
					break;
				case 4:// ��ѡ
					gridPanel.inverseSelect();
					break;
				default:
					break;
				}
			}
		});

		leftSidePanel.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub

				if (position == 1)
					inManageState = true;
				else {
					inManageState = false;
					gridPanel.clearChosenStates();
					gridPanel.hideManagePanel();
					repaint();
				}
				switch (position) {
				case 0:// �������

					break;
				case 1:
					gridPanel.showManagePanel(firstLabeltext);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * ��ȡ��ǰ������ļ����ƺ��ļ�·��
	 */
	private void initRightItems() {
		// itemNames=Info.getJobNames();
		itemNames = Arrays.asList("����p1", "����p2", "����p3", "����p4", "����p5", "����p6");
	}

}
