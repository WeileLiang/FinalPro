package utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;

public class AnimationUtil {
	public static int curTime = 0;

	public static final int PERIOD = 5;

	public static final int SHRINK_TIME = 100;

	public static final int SLIDE_TIME = 350;
	public static final int REBOUND_TIME = 600;

	/**
	 * 滑动动画
	 * @param component 需要滑动的组件
	 * @param startX 
	 * @param targetX
	 * @param startY
	 * @param targetY
	 * @param duration
	 */
	public static void doSlideAima(JComponent component, int startX, int targetX, int startY, int targetY,
			int duration,int delayTime) {
		curTime = 0;
		int disX = targetX - startX;
		int disY = targetY - startY;

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				int curX = (int) (startX + (1 - Math.cos(Math.PI / 2 * curTime / duration)) * disX);
				int curY = (int) (startY + (1 - Math.cos(Math.PI / 2 * curTime / duration)) * disY);
				component.setBounds(curX, curY, component.getWidth(), component.getHeight());

				curTime += PERIOD;

				if (curTime > duration) {
					timer.cancel();
				}
			}
		}, delayTime, PERIOD);
	}
	
	/**
	 * 按钮缩小的动画
	 * 
	 * @param component
	 */
	public static void doShrinkAnima(JComponent component) {
		curTime = 0;

		final int initX = component.getX();
		final int initY = component.getY();
		final int targetX = component.getWidth() / 15;
		final int targetY = component.getHeight() / 15;
		final int initWidth = component.getWidth();
		final int initHeigth = component.getHeight();

		Timer shrinkTimer = new Timer();
		shrinkTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				int curX = (int) (initX + (1 - Math.cos(Math.PI / 2 * curTime / SHRINK_TIME)) * targetX);
				int curY = (int) (initY + (1 - Math.cos(Math.PI / 2 * curTime / SHRINK_TIME)) * targetY);
				int width = initWidth - (curX - initX) * 2;
				int heigth = initHeigth - (curY - initY) * 2;
				component.setBounds(curX, curY, width, heigth);
				
				curTime += PERIOD;

				if (curTime > SHRINK_TIME) {
					shrinkTimer.cancel();
					doEnlargeAfterShrinkAnim(component, component.getX(), initX, component.getY(), initY);
				}
			}
		}, 0, PERIOD);
	}

	private static void doEnlargeAfterShrinkAnim(JComponent component, int initX, int targetX, int initY, int targetY) {
		curTime = 0;
		Timer timer = new Timer();

		final int distanceX = initX - targetX;
		final int distanceY = initY - targetY;
		int initWidth = component.getWidth();
		int initHeight = component.getHeight();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				int curX = (int) (initX - (1 - Math.cos(Math.PI / 2 * curTime / SHRINK_TIME)) * distanceX);
				int curY = (int) (initY - (1 - Math.cos(Math.PI / 2 * curTime / SHRINK_TIME)) * distanceY);
				int curWidth = initWidth + (initX - curX) * 2;
				int curHeight = initHeight + (initY - curY) * 2;

				component.setBounds(curX, curY, curWidth, curHeight);

				curTime += PERIOD;

				if (curTime > SHRINK_TIME)
					timer.cancel();
			}
		}, 0, PERIOD);
	}

}
