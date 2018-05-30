package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import adapter.OnNotifyListener;
import main.MyFrame;
import views.PressedButton;

public class ParameterSettingPanel extends JPanel {

	int width = MyFrame.WIDTH / 3;
	int height = MyFrame.HEIGHT * 12 / 13;

	String[] parameters = { "蚂蚁数量m", "迭代次数Nc", "忽略信息素影响比例", "信息素权重系数α", "能见度权重系数β", "每轮信息素释放总量L", "信息素初始浓度τ0",
			"信息度最小值τ(min)", "信息度最小值τ(max)", "信息素挥发因子" };
	String[] values = { "20", "50", "0.1", "2", "2", "10", "1", "0.1", "8", "0.2" };

	Font font = new Font("黑体", Font.PLAIN, 15);
	Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
	Font btnFont = new Font("黑体", Font.PLAIN, 17);
	Border btnBorder = BorderFactory.createLineBorder(Color.WHITE, 2);

	OnNotifyListener onNotifyListener;
	
	public ParameterSettingPanel() {
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

		for (int i = 0; i < parameters.length; i++) {
			JLabel label = new JLabel(parameters[i], JLabel.CENTER);
			JTextField field = new JTextField(values[i], JTextField.CENTER);
			label.setFont(font);
			label.setForeground(Color.WHITE);
			label.setBorder(border);
			field.setBorder(border);

			label.setBounds(curX, curY, 4 * gap, 33);
			curX += label.getWidth() + gap;
			field.setBounds(curX, curY, 2 * gap, 33);
			field.setFont(font);

			add(label);
			add(field);

			curX = gap;
			curY += verticalGap + label.getHeight();
		}

		JLabel sureBtn = new JLabel("确定", JLabel.CENTER);
		JLabel cancelBtn = new JLabel("取消", JLabel.CENTER);

		curY+=10;
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
				if(onNotifyListener!=null) onNotifyListener.notifyParent(0);
			}
		});
	}

	public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
		this.onNotifyListener = onNotifyListener;
	}
	
}
