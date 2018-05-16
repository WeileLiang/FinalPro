package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import adapter.OnItemClickListener;
import constant.Constants;
import main.MyFrame;
import views.LineSeparator;

public class LeftSidePanel extends JPanel {

	public static final int REBOUND_WIDTH = MyFrame.WIDTH / 30;
	private int width = MyFrame.WIDTH / 4 + REBOUND_WIDTH;
	private int height = MyFrame.HEIGHT;
	private int topMargin = width / 18;
	private int marginLRl = REBOUND_WIDTH + width / 10;
	private int marginLRs = REBOUND_WIDTH + width / 12;
	private int labelHeight = width / 8;
	private int tagHeight = (MyFrame.WIDTH / 4) / 3;

	private String tag;
	// �ָ��߸߶�
	private int separatorHeight = 1;

	private JLabel returnLabel = new JLabel("< 返回");

	private JLabel tagLabel;

	protected List<String> items;
	private List<JLabel> labels = new ArrayList<>();

	private OnItemClickListener itemClickListener;

	//当前被点击的item
	private int curClickPos=0;
	public LeftSidePanel(String tag, List<String> items) {
		this.tag = tag;
		this.items = items;

		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		setLayout(null);
		setSize(width, height);
		setBackground(Color.GRAY);
		
		returnLabel = new JLabel("< 返回");
		Font font = new Font("黑体", Font.PLAIN, 18);
		returnLabel.setFont(font);
		returnLabel.setForeground(Color.WHITE);

		Font tagFont = new Font("黑体", Font.PLAIN, 33);
		tagLabel = new JLabel("  "+tag);
		tagLabel.setForeground(Color.WHITE);
		tagLabel.setFont(tagFont);

	}

	private void measureAndLayout() {
		returnLabel.setBounds(REBOUND_WIDTH+MyFrame.RETURN_LEFT_MARGIN, MyFrame.RETURN_TOP_MARGIN, 100, 50);

		int curY = MyFrame.RETURN_TOP_MARGIN + returnLabel.getHeight();

		tagLabel.setBounds(REBOUND_WIDTH, curY, getVisibleWidth(), tagHeight);
//		tagLabel.setOpaque(true);
//		tagLabel.setBackground(Color.RED);
		add(returnLabel);
		add(tagLabel);
		curY += tagHeight;

		labels = new ArrayList<>();
		Font contentFont = new Font("黑体", Font.PLAIN, 18);
		for (String item : items) {
			JLabel label = new JLabel("         " + item);
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
			// repaint();
			labels.add(label);
		}

		setSelectedItem(0);
	}

	public int getVisibleWidth() {
		return getWidth() - REBOUND_WIDTH;
	}

	private void setListeners() {
		returnLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				MyFrame.globalFrame.back2LastPanel();
			}
		});

		for (int i = 0; i < labels.size(); i++) {
			final JLabel label = labels.get(i);
			final int position = i;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);

					setSelectedItem(position);
					if (itemClickListener != null)
						itemClickListener.onItemClick(position);
				}
			});
		}

	}

	private void setSelectedItem(int position) {
		clearClickedState();
		labels.get(position).setBackground(new Color(Constants.LIGHT_GRAY));
		curClickPos=position;
	}

	private void clearClickedState() {
		for (JLabel label : labels)
			label.setBackground(null);
	}

	public int getCurClickPos() {
		return curClickPos;
	}
	
	public void setItemClickListener(OnItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

}
