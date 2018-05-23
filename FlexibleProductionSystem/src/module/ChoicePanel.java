package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import adapter.OnNotifyListener;
import constant.AnimationUtil;
import constant.Constants;
import main.MyFrame;
import views.TransparentLabel;

public class ChoicePanel extends JPanel {

	private float initAlpha = 0.f;
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
		doAlphaAnim(ANIM_IN, null);
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
			final int temp = i;
			items.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					AnimationUtil.doShrinkAnima(items.get(temp), new OnNotifyListener() {

						@Override
						public void notifyParent(int singal) {
							// TODO Auto-generated method stub
							doAlphaAnim(ANIM_OUT, new OnNotifyListener() {

								@Override
								public void notifyParent(int singal) {
									// TODO Auto-generated method stub
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
								}
							});
						}
					});

				};

			});
		}
	}

	// 淡入动画
	public static int ANIM_IN = 0;
	// 淡出动画
	public static int ANIM_OUT = 1;

	/**
	 * 淡入淡出动画
	 */
	int curTime = 0;

	public void doAlphaAnim(int animType, OnNotifyListener onNotifyListener) {
		curTime = 0;
		float initAlpha = items.get(0).getAlpha();
		final float targetAlpha = animType == ANIM_IN ? 1.0f - initAlpha : .0f - initAlpha;
		int delayTime = 0;

		final int lastTime = 600;
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				float curAlpha = (float) (initAlpha + (1 - Math.cos(Math.PI / 2 * curTime / lastTime)) * targetAlpha);

				for (TransparentLabel label : items) {
					label.setAlpha(curAlpha);
					repaint();
				}

				curTime += LoginPanel.PERIOD;
				if (curTime > lastTime) {
					timer.cancel();

					if (onNotifyListener != null)
						onNotifyListener.notifyParent(0);
					// if (animType == ANIM_OUT && itemClickListener != null)
					// itemClickListener.onItemClick(itemPosition);

				}
			}
		}, delayTime, LoginPanel.PERIOD);
	}

	public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
		this.onNotifyListener = onNotifyListener;
	}
}
