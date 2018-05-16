package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import views.ShadePanel;

public class ArguementFrame extends JFrame {
	public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.7);
	public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);

	public static void main(String[] args) {
		ArguementFrame frame = new ArguementFrame();
		frame.setTitle("参数设置");
		frame.setSize(WIDTH / 3, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	public ArguementFrame() {
		init();
	}


	private void init() {
		int width = WIDTH / 3;
		int height = HEIGHT;

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		panel.setSize(width, height);

		int gap = width / 9;
		int curY = 30;
		int curX = gap;
		int verticalGap=20;
		Font font = new Font("黑体", Font.PLAIN, 15);
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		
		JLabel ant = new JLabel("蚂蚁数量：", JLabel.CENTER);
		JTextField antF = new JTextField();
		ant.setFont(font);
		ant.setForeground(Color.WHITE);
		ant.setBorder(border);
		ant.setBounds(curX, curY, 4 * gap, 30);
		antF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel count = new JLabel("迭代次数：", JLabel.CENTER);
		JTextField countF = new JTextField();
		count.setFont(font);
		count.setForeground(Color.WHITE);
		count.setBorder(border);
		count.setBounds(curX, curY, 4 * gap, 30);
		countF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;

		JLabel ignore = new JLabel("忽略信息素影响比例：", JLabel.CENTER);
		JTextField ignoreF = new JTextField();
		ignore.setFont(font);
		ignore.setForeground(Color.WHITE);
		ignore.setBorder(border);
		ignore.setBounds(curX, curY, 4 * gap, 30);
		ignoreF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel alpha = new JLabel("信息素权重系数：", JLabel.CENTER);
		JTextField alphaF = new JTextField();
		alpha.setFont(font);
		alpha.setForeground(Color.WHITE);
		alpha.setBorder(border);
		alpha.setBounds(curX, curY, 4 * gap, 30);
		alphaF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel beta = new JLabel("能见度权重系数：", JLabel.CENTER);
		JTextField betaF = new JTextField();
		beta.setFont(font);
		beta.setForeground(Color.WHITE);
		beta.setBorder(border);
		beta.setBounds(curX, curY, 4 * gap, 30);
		betaF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel pheCount = new JLabel("每轮信息素释放总量：", JLabel.CENTER);
		JTextField pheCountF = new JTextField();
		pheCount.setFont(font);
		pheCount.setForeground(Color.WHITE);
		pheCount.setBorder(border);
		pheCount.setBounds(curX, curY, 4 * gap, 30);
		pheCountF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel initPhe = new JLabel("信息素初始浓度：", JLabel.CENTER);
		JTextField initPheF = new JTextField();
		initPhe.setFont(font);
		initPhe.setForeground(Color.WHITE);
		initPhe.setBorder(border);
		initPhe.setBounds(curX, curY, 4 * gap, 30);
		initPheF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel minPhe = new JLabel("信息素最低值：", JLabel.CENTER);
		JTextField minPheF = new JTextField();
		minPhe.setFont(font);
		minPhe.setForeground(Color.WHITE);
		minPhe.setBorder(border);
		minPhe.setBounds(curX, curY, 4 * gap, 30);
		minPheF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel maxPhe = new JLabel("信息素最大值：", JLabel.CENTER);
		JTextField maxPheF = new JTextField();
		maxPhe.setFont(font);
		maxPhe.setForeground(Color.WHITE);
		maxPhe.setBorder(border);
		maxPhe.setBounds(curX, curY, 4 * gap, 30);
		maxPheF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel p = new JLabel("信息素挥发因子：", JLabel.CENTER);
		JTextField pF = new JTextField();
		p.setFont(font);
		p.setForeground(Color.WHITE);
		p.setBorder(border);
		p.setBounds(curX, curY, 4 * gap, 30);
		pF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel wrongRatio = new JLabel("设备故障触发概率：", JLabel.CENTER);
		JTextField wrongRatioF = new JTextField();
		wrongRatio.setFont(font);
		wrongRatio.setForeground(Color.WHITE);
		wrongRatio.setBorder(border);
		wrongRatio.setBounds(curX, curY, 4 * gap, 30);
		wrongRatioF.setBounds(curX + ant.getWidth() + gap, curY, 2 * gap, 30);
		curX=gap;
		curY+=30+verticalGap;
		
		JLabel sureBtn = new JLabel("确定", JLabel.CENTER);
		sureBtn.setFont(new Font("黑体", Font.PLAIN, 18));
		sureBtn.setForeground(Color.WHITE);
		sureBtn.setBorder(border);
		sureBtn.setBounds((width-100)/2, curY+30, 100, 38);
		
		
		panel.add(ant);
		panel.add(antF);
		panel.add(count);
		panel.add(countF);
		panel.add(ignore);
		panel.add(ignoreF);
		panel.add(alpha);
		panel.add(alphaF);
		panel.add(beta);
		panel.add(betaF);
		panel.add(pheCount);
		panel.add(pheCountF);
		panel.add(initPhe);
		panel.add(initPheF);
		panel.add(minPhe);
		panel.add(minPheF);
		panel.add(maxPhe);
		panel.add(maxPheF);
		panel.add(p);
		panel.add(pF);
		panel.add(wrongRatio);
		panel.add(wrongRatioF);
		panel.add(sureBtn);
		
		
		setLayout(null);
		panel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		add(panel);
	}

}
