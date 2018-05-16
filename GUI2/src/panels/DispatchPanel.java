package panels;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import adapter.ItemClickListener;
import adapter.NotifyListener;
import constant.Info;
import main.MyFrame;
import utils.AnimationUtil;
import views.TransparentPanel;
import views.VerticalLineSeparator;

public class DispatchPanel extends JPanel {

	private int width = MyFrame.WIDTH;
	private int height = MyFrame.HEIGHT;

	private LeftSidePanel leftSidePanel;
	private TimePanel timePanel;
	private TimeLinePanel timeLinePanel;

	private TimeLinePanel[] panels;
	private VerticalLineSeparator separator;

	private TransparentPanel alphaPanel;

	private NotifyListener notifyListener;

	private Timer invalidateTimer;
	// 剩余的调度时间
	private int leftTime = 0;

	public DispatchPanel() {
		setSize(width, height);
		setLayout(null);
		setBackground(Color.GRAY);
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		leftSidePanel = new LeftSidePanel("调度情况", Info.getJobshopNames(false));
		timeLinePanel = new TimeLinePanel(Info.getTimeLinesOfJobshop().get(Info.getJobshopNames(false).get(0)));
		timePanel = new TimePanel();
		timePanel.setTotalLabelText(Info.totalTime);
		timePanel.setRestLabelText(Info.totalTime);
		separator = new VerticalLineSeparator(6, height, Color.WHITE);

		panels = new TimeLinePanel[Info.getJobNames().size()];

		alphaPanel = new TransparentPanel(1.f);
		alphaPanel.setSize(MyFrame.WIDTH * 3 / 4, MyFrame.HEIGHT);
		alphaPanel.setBackground(Color.GRAY);
	}

	private void measureAndLayout() {
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		separator.setBounds(leftSidePanel.getVisibleWidth(), 0, separator.getWidth(), separator.getHeight());

		timePanel.setBounds(separator.getWidth() + leftSidePanel.getVisibleWidth(), 0, timePanel.getWidth(),
				timePanel.getHeight());
		timeLinePanel.setBounds(separator.getWidth() + leftSidePanel.getVisibleWidth(), timePanel.getHeight(),
				timeLinePanel.getWidth(), timeLinePanel.getHeight());

		alphaPanel.setBounds(leftSidePanel.getVisibleWidth(), 0, alphaPanel.getWidth(), alphaPanel.getHeight());
		add(leftSidePanel);
		add(alphaPanel);
		add(separator);
		add(timePanel);
		add(timeLinePanel);

		doReboundSlideInAnim();
		doAlphaOutAnim();
	}

	private int curPos = 0;

	public void setListeners() {
		leftSidePanel.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				if (curPos == position)
					return;
				curPos = position;
				timeLinePanel.reLayout(Info.getJobshopNames(false).get(position));
			}
		});

	}

	private int curTime = 0;

	public void doReboundSlideInAnim() {
		AnimationUtil.doSlideAima(leftSidePanel, -leftSidePanel.getWidth(), 0, 0, 0, AnimationUtil.SLIDE_TIME, 0);
		AnimationUtil.doSlideAima(leftSidePanel, 0, -LeftSidePanel.REBOUND_WIDTH, 0, 0, AnimationUtil.REBOUND_TIME,
				AnimationUtil.SLIDE_TIME);
	}

	public void doAlphaOutAnim() {
		doRightSideAlphaAnima(1.f, .0f, 650, AnimationUtil.SLIDE_TIME + AnimationUtil.REBOUND_TIME);
	}

	private void doRightSideAlphaAnima(Float initAlpha, float targetAlpha, int duration, int delay) {
		curTime = 0;
		float distance = targetAlpha - initAlpha;
		int period = 5;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				float curAlpha = (float) (initAlpha + (1 - Math.cos(Math.PI / 2 * curTime / duration)) * distance);
				alphaPanel.setAlpha(curAlpha);

				curTime += period;
				if (curTime > duration) {
					alphaPanel.setAlpha(targetAlpha);
					timer.cancel();
					startTimer();
				}

				repaint();
			}
		}, delay, period);
	}

	private void startTimer() {
//		timeLinePanel.addItemMouseListeners(true);
		leftTime = Info.totalTime;
		invalidateTimer = new Timer(true);
		invalidateTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Info.timeLinesCountDown();
				timePanel.setRestLabelText(--leftTime);
				repaint();
				
				if(leftTime<=0) invalidateTimer.cancel();
			}
		}, AnimationUtil.SLIDE_TIME + AnimationUtil.REBOUND_TIME+2000, 1000);
	}

	public void closeMyself() {
		if (notifyListener != null) {
			notifyListener.notifyParent(0);
			reset();
		}
	}

	/**
	 * 左边菜单移除，以及透明面板置为不透明
	 */
	public void reset() {
		leftSidePanel.setBounds(-LeftSidePanel.REBOUND_WIDTH, 0, leftSidePanel.getWidth(), leftSidePanel.getHeight());
		alphaPanel.setAlpha(1.f);
		repaint();
	}

	public void setNotifyListener(NotifyListener notifyListener) {
		this.notifyListener = notifyListener;
	}
}
