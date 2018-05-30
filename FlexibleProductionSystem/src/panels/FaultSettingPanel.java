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
import javax.swing.JTextField;
import javax.swing.border.Border;

import adapter.OnNotifyListener;
import main.MyFrame;

public class FaultSettingPanel extends JPanel {

	private int width = MyFrame.WIDTH / 3;
	private int height = MyFrame.HEIGHT *5/ 12;

	private String[] parameters = { "故障触发时间段", "触发概率" };
	private String[] values = { "故障触发时间段", "0.5" };

	Font font = new Font("黑体", Font.PLAIN, 15);
	Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
	Font btnFont = new Font("黑体", Font.PLAIN, 17);

	OnNotifyListener onNotifyListener;

	public FaultSettingPanel() {
		setLayout(null);
		setSize(width, height);
		setBackground(Color.GRAY);

		measureAndLayout();
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		int gap = width / 9;
		int verticalGap = 23;
		int curY = 30;
		int curX = gap;
		int labelWidth = 4 * gap;
		int fieldWidth = 2 * gap;
		int labelHeight = 33;

		JLabel faultOpenLabel = new JLabel("已开启故障测试模式", JLabel.CENTER);
		faultOpenLabel.setForeground(Color.WHITE);
		faultOpenLabel.setBorder(border);
		faultOpenLabel.setFont(font);
		faultOpenLabel.setBounds(curX, curY, width - gap * 2, labelHeight);

		add(faultOpenLabel);
		curY += labelHeight + verticalGap;

		for (int i = 0; i < parameters.length; i++) {
			JLabel label = new JLabel(parameters[i], JLabel.CENTER);
			label.setFont(font);
			label.setForeground(Color.WHITE);
			label.setBorder(border);

			label.setBounds(curX, curY, labelWidth, labelHeight);
			add(label);
			curX += labelWidth + gap;
			if (i == 0) {
				List<JTextField> timeFields = Arrays.asList(new JTextField("30"), new JTextField("38"));
				for (JTextField field : timeFields) {
					field.setBorder(border);
					field.setFont(font);
					add(field);
				}

				timeFields.get(0).setBounds(curX, curY, fieldWidth / 3, labelHeight);
				curX += timeFields.get(0).getWidth();
				JLabel lineLabel = new JLabel("-", JLabel.CENTER);
				lineLabel.setFont(font);
				lineLabel.setForeground(Color.WHITE);
				lineLabel.setBounds(curX, curY, fieldWidth / 3, labelHeight);
				curX += lineLabel.getWidth();
				timeFields.get(1).setBounds(curX, curY, fieldWidth / 3, labelHeight);

				add(lineLabel);
			} else {
				JTextField field = new JTextField(values[i]);
				field.setBorder(border);
				field.setFont(font);
				field.setBounds(curX, curY, fieldWidth, labelHeight);
				add(field);
			}

			curX = gap;
			curY += labelHeight + verticalGap;
		}

		JLabel sureBtn = new JLabel("确定", JLabel.CENTER);
		JLabel cancelBtn = new JLabel("取消", JLabel.CENTER);

		curY += 10;
		int btnWidth = 100;
		curX = (width - btnWidth * 2 - gap) / 2;
		sureBtn.setBounds(curX, curY, btnWidth, 36);
		sureBtn.setForeground(Color.WHITE);
		sureBtn.setBorder(border);
		sureBtn.setFont(btnFont);
		curX += btnWidth + gap;
		cancelBtn.setBounds(curX, curY, btnWidth, 36);
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBorder(border);
		cancelBtn.setFont(btnFont);

		add(sureBtn);
		add(cancelBtn);

		sureBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if (onNotifyListener != null)
					onNotifyListener.notifyParent(0);
			}
		});
	}

	public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
		this.onNotifyListener = onNotifyListener;
	}
}
