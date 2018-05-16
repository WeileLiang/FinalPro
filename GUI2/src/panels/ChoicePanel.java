package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import adapter.ItemClickListener;
import adapter.NotifyListener;
import constant.Constants;
import main.MyFrame;
import utils.AnimationUtil;
import views.TransparentLabel;

public class ChoicePanel extends JPanel {

	// public static String

	public static String[] OPERATION_NAMES = { "产品配置", "资源配置", "工艺配置", "调度开始" };

	// 淡入动画
	public static int ANIM_IN = 0;
	// 淡出动画
	public static int ANIM_OUT = 1;

	// 点击某个操作选项后，切换到对应的操作详情页
	public static int CHOIC_OVER_SIGNAL = 0;

	private float initAlpha = .0f;
	// 淡入淡出动画已经执行的时间
	private int curTime = 0;

	private int width = MyFrame.WIDTH;
	private int height = MyFrame.HEIGHT;

	// 按钮宽度、高度以及按钮间的间隔
	private int itemWidth = width / 6;
	private int itemHeight = itemWidth / 2;
	private int marginLR = width / 40;
	private int gapHorizontal = width / 40;

	// 点击的操作序号
	private int itemPosition;

	private TransparentLabel productLabel = new TransparentLabel("信息管理", initAlpha);
	private TransparentLabel resourceLabel = new TransparentLabel("工厂资源配置", initAlpha);
	private TransparentLabel craftLabel = new TransparentLabel("工艺配置", initAlpha);
	private TransparentLabel dispatchLabel = new TransparentLabel("车间调度", initAlpha);
	private TransparentLabel helpLabel = new TransparentLabel("使用说明", initAlpha);
	
	List<TransparentLabel> labels = Arrays.asList(productLabel, resourceLabel, craftLabel, dispatchLabel,helpLabel);

	private ItemClickListener itemClickListener;
	private NotifyListener notifyListener;

	public ChoicePanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void measureAndLayout() {
		int y = (height - itemHeight) / 2;
		int curX = marginLR;

		for (TransparentLabel label : labels) {
			label.setBounds(curX, y, itemWidth, itemHeight);
			add(label);
			curX += itemWidth + gapHorizontal;
		}
	}

	private void initViews() {
		setSize(width, height);
		setLayout(null);
		// 背景透明
		setBackground(null);
		setOpaque(false);

		Color bgColor = new Color(Constants.WINE_RED);
		Font font = new Font("黑体", Font.PLAIN, 100);

		for (TransparentLabel label : labels) {
			label.setForeground(Color.WHITE);
			label.setBackground(bgColor);
			// label.setFont(font);
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
					// 先执行按钮缩小放大动画再执行淡出动画
					AnimationUtil.doShrinkAnima(labels.get(position));

					itemPosition = position;
					doAlphaAnim(ANIM_OUT);
					// itemClickListener.onItemClick(position);

				}
			});
		}
	}

	/**
	 * 淡入淡出动画
	 */
	public void doAlphaAnim(int animType) {
		curTime = 0;
		initAlpha = productLabel.getAlpha();
		final float targetAlpha = animType == ANIM_IN ? 1.0f - initAlpha : .0f - initAlpha;
		int delayTime = animType == ANIM_IN ? 0 : AnimationUtil.SHRINK_TIME * 2;

		final int lastTime = LoginPanel.LAST_TIME * 4 / 3;
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				float curAlpha = (float) (initAlpha + (1 - Math.cos(Math.PI / 2 * curTime / lastTime)) * targetAlpha);

				for (TransparentLabel label : labels) {
					label.setAlpha(curAlpha);
					repaint();
				}

				curTime += LoginPanel.PERIOD;
				if (curTime > lastTime) {
					timer.cancel();

					if (animType == ANIM_OUT && itemClickListener != null)
						itemClickListener.onItemClick(itemPosition);

				}
			}
		}, delayTime, LoginPanel.PERIOD);
	}

	// 重新加载该Panel时重置各个按钮的透明度
	public void refreshUI() {
		for (TransparentLabel label : labels) {
			label.setAlpha(1.f);
		}

		repaint();
	}

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public void setNotifyListener(NotifyListener notifyListener) {
		this.notifyListener = notifyListener;
	}

}
