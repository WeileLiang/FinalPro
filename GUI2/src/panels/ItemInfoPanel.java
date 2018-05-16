package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import adapter.ItemClickListener;
import constant.Constants;
import main.MyFrame;
import views.LineSeparator;

/**
 * 在非管理模式下点击GridPanel的Item弹出的信息界面
 * 
 * @author Robot
 *
 */
public class ItemInfoPanel extends JPanel {

	private int width = MyFrame.WIDTH / 3;
	private int height;
	private int topMargin = MyFrame.WIDTH / 52;
	private int marginLRl = width / 10;
	private int marginLRs = width / 12;
	private int labelHeight = MyFrame.WIDTH / 32;
	private int tagHeight = (MyFrame.WIDTH / 4) / 3;

	private String tag;
	// 分割线高度
	private int separatorHeight = 1;

	private JLabel tagLabel;

	protected List<String> items;
	private List<JLabel> labels = new ArrayList<>();

	private ItemClickListener itemClickListener;

	public ItemInfoPanel(String tag, List<String> items) {
		this.tag = tag;
		this.items = items;

		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		Font tagFont = new Font("黑体", Font.PLAIN, 33);
		tagLabel = new JLabel(tag);
		tagLabel.setForeground(Color.WHITE);
		tagLabel.setFont(tagFont);

		setLayout(null);
		height = topMargin * 3 + items.size() * labelHeight + tagHeight;
		setSize(width, height);
		setBackground(Color.GRAY);
	}

	private void measureAndLayout() {
		int curY = 0;

		tagLabel.setBounds(marginLRs, curY, width, tagHeight);

		add(tagLabel);
		curY += tagHeight * 4 / 5;

		labels = new ArrayList<>();
		Font contentFont = new Font("黑体", Font.PLAIN, 18);
		for (String item : items) {
			JLabel label = new JLabel("     " + item);
			label.setBounds(0, curY, width, labelHeight);
			label.setForeground(Color.WHITE);
			label.setFont(contentFont);
			label.setOpaque(true);
			label.setBackground(null);
			curY += labelHeight;

			LineSeparator line = new LineSeparator(width, separatorHeight, Color.WHITE);
			line.setBounds(0, curY, line.getWidth(), line.getHeight());

			curY += separatorHeight;

			add(label);
			add(line);
			labels.add(label);
		}

	}

	private void setListeners() {
		for (int i = 0; i < labels.size(); i++) {
			final JLabel label = labels.get(i);
			final int position = i;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);

					if (itemClickListener != null)
						itemClickListener.onItemClick(position);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					label.setBackground(Color.LIGHT_GRAY);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					label.setBackground(null);
				}
			});

		}

	}

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

}
