package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import adapter.OnItemClickListener;
import main.MyFrame;
import views.PartTableHead;
import views.TableRow;

public class TablePanel extends JPanel {

	public static final int WIDTH = MyFrame.WIDTH * 2 / 3;
	private int height;

	private Object[][] datas;
	private int[] scales;

	List<TableRow> tableRows;

	private int lastSelectedId = 1;
	private boolean isClicked;

	private OnItemClickListener onItemClickListener;

	// 该表格是否用于展示工件的机器选择
	private boolean isPartDetail;

	public TablePanel(Object[][] datas) {
		this.datas = datas;
		// 表格的高度=表头高度+各个数据行的高度
		
			height = TableRow.largerHeight + (datas.length - 1) * TableRow.normalHeight;

		scales = new int[datas[0].length];
		Arrays.fill(scales, 1);

		initViews();
		measureAndLayout();
		setListeners();
	}

	public TablePanel(Object[][] datas, boolean isPartDetail) {
		this.isPartDetail = isPartDetail;
		this.datas = datas;
		// 表格的高度=表头高度+各个数据行的高度
		height = PartTableHead.HEAD_HEIGHT+(datas.length -1) * TableRow.normalHeight;

		scales = new int[datas[0].length];
		Arrays.fill(scales, 1);

		initViews();
		measureAndLayout();
		setListeners();
	}

	public TablePanel(Object[][] datas, int[] scales) {
		this.datas = datas;
		this.scales = scales;
		// 表格的高度=表头高度+各个数据行的高度
		height = TableRow.largerHeight + (datas.length - 1) * TableRow.normalHeight;

		initViews();
		measureAndLayout();
		setListeners();

	}

	private void setListeners() {
		// TODO Auto-generated method stub
		for (int i = 1; i < tableRows.size(); i++) {

			TableRow row = tableRows.get(i);
			row.setFocusable(true);
			final int temp = i;
			row.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					row.setBackground(Color.LIGHT_GRAY);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					if (!(isClicked && temp == lastSelectedId))
						row.setBackground(Color.GRAY);
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					isClicked = true;
					row.setBackground(Color.LIGHT_GRAY);
					tableRows.get(lastSelectedId).setBackground(Color.GRAY);
					lastSelectedId = temp;
					if (onItemClickListener != null)
						onItemClickListener.onItemClick(temp);
				}
			});
		}
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		int curY = 0;
		for (TableRow tableRow : tableRows) {
			tableRow.setBounds(0, curY, tableRow.getWidth(), tableRow.getHeight());
			add(tableRow);

			curY += tableRow.getHeight();
		}
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setPreferredSize(new Dimension(WIDTH, height));
		setLayout(null);

		tableRows = new ArrayList<>();

		for (int i = 0; i < datas.length; i++) {
			if (i == 0) {
				if (isPartDetail) {
					tableRows.add(new PartTableHead(i, datas[i], scales, true, false));
				} else
					tableRows.add(new TableRow(i, datas[i], scales, true, false));
			} else if (i == datas.length - 1) {
				tableRows.add(new TableRow(i, datas[i], scales, false, true));
			} else {
				tableRows.add(new TableRow(i, datas[i], scales));
			}
		}
	}

	// 返回数据行
	public Object[] getTableRow(int id) {
		return datas[id];
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
}
