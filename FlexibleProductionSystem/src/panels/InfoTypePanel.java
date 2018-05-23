package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.JPanel;

import adapter.OnNotifyListener;
import constant.AnimationUtil;
import constant.Constants;
import main.MyFrame;
import module.ChoicePanel;
import module.CraftPanel;
import module.InfoManagePanel;
import module.LoginPanel;
import module.ResourcePanel;
import module.SchedulePanel;
import views.TransparentLabel;

/**
 * 选择信息的种类面板
 * 
 * @author Will
 *
 */
public class InfoTypePanel extends JPanel {

	private TransparentLabel userInfoLabel;
	private TransparentLabel materialInfoLabel;

	List<TransparentLabel> items;
	public InfoTypePanel() {
		initViews();
		measureAndLayout();
		setListeners();
		doAlphaAnim(ANIM_IN, null);
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
										MyFrame.globalFrame.addFurtherPanel(new UserInfoPanel(), 1);
										break;
									case 1:
										MyFrame.globalFrame.addFurtherPanel(new MaterialInfoPanel(), 1);
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

	private void initViews() {
		setLayout(null);
		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		setBackground(null);
		setOpaque(false);

		Font font = new Font("黑体", Font.PLAIN, 30);
		userInfoLabel = new TransparentLabel("用户信息", 0.f, font);
		materialInfoLabel = new TransparentLabel("物料信息", 0.f, font);

		Color bgColor = new Color(Constants.WINE_RED);
		userInfoLabel.setForeground(Color.WHITE);
		userInfoLabel.setBackground(bgColor);
		materialInfoLabel.setForeground(Color.WHITE);
		materialInfoLabel.setBackground(bgColor);
		
		items=Arrays.asList(userInfoLabel,materialInfoLabel);
	}

	private void measureAndLayout() {
		int curX = (MyFrame.WIDTH - ChoicePanel.ITEM_WIDTH) / 2;
		int curY = (MyFrame.HEIGHT - ChoicePanel.ITEM_HEIGHT * 2 - ChoicePanel.MARGIN_TB) / 2;

		userInfoLabel.setBounds(curX, curY, ChoicePanel.ITEM_WIDTH, ChoicePanel.ITEM_HEIGHT);
		add(userInfoLabel);
		curY += ChoicePanel.ITEM_HEIGHT + ChoicePanel.MARGIN_TB;

		materialInfoLabel.setBounds(curX, curY, ChoicePanel.ITEM_WIDTH, ChoicePanel.ITEM_HEIGHT);
		add(materialInfoLabel);
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
}
