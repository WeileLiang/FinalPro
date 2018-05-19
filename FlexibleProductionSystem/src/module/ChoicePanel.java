package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import adapter.OnNotifyListener;
import constant.Constants;
import main.MyFrame;
import panels.InfoTypePanel;
import views.TransparentLabel;

public class ChoicePanel extends JPanel {

	private float initAlpha = 1.f;
	private String[] itemNames = { "信息管理", "工艺配置", "登录管理", "工厂资源配置", "生产调度", "使用帮助", };
	private List<TransparentLabel> items = new ArrayList<>();

	public static final int ITEM_WIDTH = MyFrame.WIDTH / 4;
	public static final int ITEM_HEIGHT = ITEM_WIDTH / 2;
	public static final int MARGIN_LR = MyFrame.WIDTH / 16;
	public static final int MARGIN_TB = MyFrame.WIDTH / 32;

	private OnNotifyListener onNotifyListener;

	public ChoicePanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
		setLayout(null);

		// 背景透明
		setBackground(null);
		setOpaque(false);

		Font font = new Font("黑体", Font.PLAIN, 30);
		Color bgColor = new Color(Constants.WINE_RED);
		for (String name : itemNames) {
			TransparentLabel label = new TransparentLabel(name, initAlpha, font);
			label.setForeground(Color.WHITE);
			label.setBackground(bgColor);
			items.add(label);
		}

	}

	private void measureAndLayout() {
		int curY = (MyFrame.HEIGHT - ITEM_HEIGHT * 2 - MARGIN_TB) / 2;
		int curX = MARGIN_LR;

		for (int i = 0; i < itemNames.length / 2; i++) {
			items.get(i).setBounds(curX, curY, ITEM_WIDTH, ITEM_HEIGHT);
			add(items.get(i));
			curX += ITEM_WIDTH + MARGIN_LR;
		}

		curX = MARGIN_LR;
		curY += ITEM_HEIGHT + MARGIN_TB;

		for (int i = itemNames.length / 2; i < itemNames.length; i++) {
			items.get(i).setBounds(curX, curY, ITEM_WIDTH, ITEM_HEIGHT);
			add(items.get(i));
			curX += ITEM_WIDTH + MARGIN_LR;
		}
	}

	private void setListeners() {
		for (int i = 0; i < items.size(); i++) {
			final int temp=i; 
			items.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					switch (temp) {
					case 0:
						MyFrame.globalFrame.addFurtherPanel(new InfoManagePanel(), 1);
						break;
					case 1:
						MyFrame.globalFrame.addFurtherPanel(new CraftPanel(), 1);
						break;
					case 3:
						MyFrame.globalFrame.addFurtherPanel(new ResourcePanel(), 1);
						break;	
					case 4:
						MyFrame.globalFrame.addFurtherPanel(new SchedulePanel(), 1);
						break;	
					default:
						break;
					}
				
				};

			});
		}
	}

	public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
		this.onNotifyListener = onNotifyListener;
	}
}
