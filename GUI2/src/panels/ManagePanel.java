package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import adapter.ItemClickListener;
import main.MyFrame;
import utils.AnimationUtil;

public class ManagePanel extends JPanel {

	private int width = MyFrame.WIDTH / 2;
	private int height = MyFrame.HEIGHT / 20;

	private int labelWidth = height * 26 / 10;

	private int gapHor = labelWidth / 5;

	JLabel importLabel = new JLabel("导入", JLabel.CENTER);
	JLabel deleteLabel = new JLabel("删除", JLabel.CENTER);
	JLabel allLabel = new JLabel("全选", JLabel.CENTER);
	JLabel allNotLabel = new JLabel("全不选", JLabel.CENTER);
	JLabel inverseLabel = new JLabel("反选", JLabel.CENTER);

	List<JLabel> labels = Arrays.asList(importLabel, deleteLabel, allLabel, allNotLabel, inverseLabel);

	private ItemClickListener itemClickListener;

	public ManagePanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setOpaque(false);

		setSize(width, height);

		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("黑体", Font.PLAIN, 16);

		for (JLabel label : labels) {
//			label.setOpaque(true);
//			label.setBackground(Color.LIGHT_GRAY);
			label.setForeground(Color.WHITE);
			label.setFont(font);
			label.setBorder(border);
		}

		deleteLabel.setOpaque(true);
		deleteLabel.setBackground(Color.LIGHT_GRAY);
		deleteLabel.setBackground(Color.RED);

	}

	private void measureAndLayout() {
		int curX = 0;
		for (JLabel label : labels) {
			label.setSize(labelWidth, height);
			label.setBounds(curX, 0, label.getWidth(), label.getHeight());
			curX += gapHor + labelWidth;

			add(label);
		}
	}

	private void setListeners() {
		for (int i = 0; i < labels.size(); i++) {
			final int position = i;
			labels.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					AnimationUtil.doShrinkAnima(labels.get(position));
					if (itemClickListener != null)
						itemClickListener.onItemClick(position);

				}
			});
		}
	}

	/**
	 * 根据不同的类别（产品、工艺、资源）改变第一个按钮的内容
	 */
	public void setFirstLabelText(String text) {
		importLabel.setText(text);
	}
	
	public void setImportLabelText(String text) {
		importLabel.setText(text);
	}

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}


}
