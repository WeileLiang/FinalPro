package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.MyFrame;
import module.SchedulePanel;
import views.TimeLineItem;

public class TimeLinePanel extends JPanel {

	private int width = MyFrame.WIDTH * 3 / 4;
	private int height;

	// public static final int MARGIN_TB=TimeLineItem.height

	private List<String> names;
	private List<List<double[]>> chipLists;
	private List<List<String>> procedureLists;

	private JLabel changeModeLabel = new JLabel("文字模式", JLabel.CENTER);
	// 是否位于甘特图模式
	private boolean inGant = true;

	// 坐标的最大值
	private double max;
	private List<TimeLineItem> items;

	public TimeLinePanel(List<String> names, List<List<double[]>> chipLists, List<List<String>> procedureLists) {
		this.names = names;
		this.chipLists = chipLists;
		this.procedureLists = procedureLists;
		height = names.size() * TimeLineItem.height + SchedulePanel.LABEL_HEIGHT*3/4 + SchedulePanel.MARGIN_TOP * 3;

		setMax();
		initViews();
		measureAndLayout();
	}

	public TimeLinePanel(List<String> names, List<List<double[]>> chipLists, List<List<String>> procedureLists,int max) {
		this.names = names;
		this.chipLists = chipLists;
		this.procedureLists = procedureLists;
		height = names.size() * TimeLineItem.height + SchedulePanel.LABEL_HEIGHT*3/4 + SchedulePanel.MARGIN_TOP * 3;

		this.max=max;
		initViews();
		measureAndLayout();
	}
	
	private void measureAndLayout() {
		// TODO Auto-generated method stub
		int curY = SchedulePanel.MARGIN_TOP;
		changeModeLabel.setBounds(width - SchedulePanel.MARGIN_LEFT*3/2 - SchedulePanel.LABEL_WIDTH*3/4, curY,
				SchedulePanel.LABEL_WIDTH*3/4, SchedulePanel.LABEL_HEIGHT*2/3);
		add(changeModeLabel);

		curY += SchedulePanel.LABEL_HEIGHT + SchedulePanel.MARGIN_TOP;

		for (TimeLineItem item : items) {
			item.setBounds(0, curY, item.getWidth(), item.getHeight());
			add(item);
			curY += item.getHeight();
		}

	}

	private void initViews() {
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		setBackground(Color.GRAY);

		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 13);
		changeModeLabel.setFont(font);
		changeModeLabel.setForeground(Color.WHITE);
		changeModeLabel.setBorder(border);

		items = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			items.add(new TimeLineItem(names.get(i), chipLists.get(i), procedureLists.get(i), max));
		}
	}

	private void setMax() {
		for (List<double[]> chips : chipLists) {
			double temp = chips.get(chips.size() - 1)[1];
			if (temp > max)
				max = temp;
		}

		max += 2;
	}

}
