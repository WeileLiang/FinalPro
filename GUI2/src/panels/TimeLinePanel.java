package panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import adapter.ItemClickListener;
import constant.Info;
import constant.Info.TimeLine;
import main.MyFrame;
import views.TimeLineItem;

public class TimeLinePanel extends JPanel {
	private int width = MyFrame.WIDTH * 3 / 4;
	private int height = MyFrame.HEIGHT * 5 / 6;
	private int marginLR = width / 20;
	private int marginTB = height / 20;
	private int itemHeight = height / 12;

	private Font font = new Font("黑体", Font.PLAIN, 18);
	
	private ItemClickListener itemClickListener;
	
	List<TimeLine> timeLines = new ArrayList<TimeLine>();
	// TimeLineItem[] items;
	List<TimeLineItem> items;

	public TimeLinePanel(List<TimeLine> timeLines) {
		setSize(width, height);
		setLayout(null);
		setBackground(Color.GRAY);
		// setOpaque(false);
		this.timeLines = timeLines;
		initViews();
		measureAndLayout();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		items = new ArrayList<>();
		for (int i = 0; i < timeLines.size(); i++)
			items.add(new TimeLineItem(timeLines.get(i).name, timeLines.get(i).chips));

	}

	private void measureAndLayout() {
		int curY = marginTB;
		for (TimeLineItem item : items) {
			item.setBounds(0, curY, item.getWidth(), item.getHeight());
			curY += item.getHeight();
			add(item);
		}
	}

	public void addItemMouseListeners(boolean needSet) {
		for (int i=0;i<items.size();i++) {
			int position=i;
			items.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					items.get(position).setBackground(Color.LIGHT_GRAY);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					items.get(position).setBackground(null);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					if(itemClickListener!=null) itemClickListener.onItemClick(position);
				}
			});
		}
			
	}

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}
	
	/**
	 * 根据左侧菜单重新载入对应车间设备的时间线
	 * 
	 * @param jobshopName
	 */
	public void reLayout(String jobshopName) {
		removeAll();
		List<TimeLine> mTimeLines = Info.getTimeLinesOfJobshop().get(jobshopName);
		items.clear();

		for (int i = 0; i < mTimeLines.size(); i++)
			items.add(new TimeLineItem(mTimeLines.get(i).name, mTimeLines.get(i).chips));

		int curY = marginTB;
		for (TimeLineItem item : items) {
			item.setBounds(0, curY, item.getWidth(), item.getHeight());
			curY += item.getHeight();
			add(item);
		}

		repaint();
	}

	public void invalidate() {
		for (TimeLineItem item : items)
			item.repaint();

		repaint();
	}
	// @Override
	// protected void paintComponent(Graphics g) {
	// // TODO Auto-generated method stub
	// super.paintComponent(g);
	// Graphics2D g2 = (Graphics2D) g; // g是Graphics对象
	// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	// g2.setStroke(new BasicStroke(1));
	// g2.setColor(Color.WHITE);
	//
	// int curY = marginTB;
	//
	//
	// for (int i = 0; i < chips.size(); i++) {
	// g2.setFont(font);
	// FontMetrics fm = g2.getFontMetrics();
	// String text="Machine"+(i+1)+": ";
	// int textWidth = fm.stringWidth(text);
	// int textX = marginLR;
	// int ascent = fm.getAscent();
	// int descent = fm.getDescent();
	// int textY = (itemHeight - (ascent + descent)) / 2 + ascent + curY;
	// //设备名称
	// g2.drawString(text, textX, textY);
	// //坐标轴
	// int lineX1=textWidth+marginLR;
	// int lineX2=width-marginLR;
	// int lineY=curY+itemHeight/2;
	// g2.drawLine(lineX1, lineY, lineX2, lineY);
	// //箭头
	// int arrowX = width - marginLR - itemHeight / 4;
	// int upArrowY=lineY-itemHeight/8;
	// int downArrowY=lineY+itemHeight/8;
	// g2.drawLine(arrowX, upArrowY, lineX2, lineY);
	// g2.drawLine(arrowX, downArrowY, lineX2, lineY);
	//
	// //时间片
	// //坐标轴的总长度
	// int len=lineX2-lineX1;
	// //坐标轴的最大坐标
	// int max=60;
	// List<int[]> mChips=chips.get(i);
	// for(int[] chip:mChips){
	// int x1=lineX1+len*chip[0]/max;
	// int x2=lineX1+len*chip[1]/max;
	// int y=lineY-itemHeight/4;
	// g2.drawLine(x1, lineY, x1, y);
	// g2.drawLine(x1, y, x2, y);
	// g2.drawLine(x2, y, x2, lineY);
	//
	// //坐标轴坐标
	// String xText1=String.valueOf(chip[0]);
	// String xText2=String.valueOf(chip[1]);
	//
	// g2.setFont(new Font("黑体",Font.PLAIN,15));
	// FontMetrics fm2 = g2.getFontMetrics();
	// String text1=String.valueOf(chip[0]);
	// String text2=String.valueOf(chip[1]);
	// int textWidth1 = fm2.stringWidth(text1);
	// int textWidth2 = fm2.stringWidth(text2);
	// int mx1 = x1-textWidth1/2;
	// int ascent1 = fm2.getAscent();
	// int descent1 = fm2.getDescent();
	// int my = (itemHeight/4 - (ascent + descent)) / 2 + ascent +lineY;
	//
	// int mx2 = x2-textWidth2/2;
	// g2.drawString(text1, mx1, my);
	// g2.drawString(text2, mx2, my);
	// }
	//
	// curY+=itemHeight;
	// }
	//
	// }
}
