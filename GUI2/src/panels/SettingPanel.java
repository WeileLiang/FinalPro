package panels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.MyFrame;

/**
 * �������ȵ�����ģ��
 * 
 * @author Will
 *
 */
public class SettingPanel extends JPanel {

	// ѡ������
	private JLabel taskLabel=new JLabel("����ѡ��",JLabel.CENTER);
	// ѡ����Դ
	private JLabel resourceLabel=new JLabel("��Դѡ��",JLabel.CENTER);
	// ��������
	private JLabel parameterLabel=new JLabel("��������",JLabel.CENTER);
	// ��������
	private JLabel faultLabel=new JLabel("��������",JLabel.CENTER);
	// ����
	private JLabel startLabel=new JLabel("��������",JLabel.CENTER);

	public static final int LABEL_HEIGHT = MyFrame.HEIGHT / 20;
	public static final int LABEL_WIDTH = LABEL_HEIGHT * 26 / 10;
	public static final int MARGIN_TOP = 20;
	public static final int MARGIN_LEFT = 30;
	public static final int MARGIN = LABEL_WIDTH / 3;

	public SettingPanel() {
		setSize(MyFrame.WIDTH * 3 / 4, MyFrame.HEIGHT);
		setLayout(null);

		setBackground(null);
		setOpaque(false);

		initViews();
		measureAndLayout();
	}

	private void measureAndLayout() {
		// TODO Auto-generated method stub
		int curX=MARGIN_LEFT;
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		Font font = new Font("����", Font.PLAIN, 16);
		
		taskLabel.setBorder(border);
		taskLabel.setFont(font);
		taskLabel.setForeground(Color.WHITE);
		resourceLabel.setBorder(border);
		resourceLabel.setFont(font);
		resourceLabel.setForeground(Color.WHITE);
		parameterLabel.setBorder(border);
		parameterLabel.setFont(font);
		parameterLabel.setForeground(Color.WHITE);
		faultLabel.setBorder(border);
		faultLabel.setFont(font);
		faultLabel.setForeground(Color.WHITE);
		startLabel.setBorder(border);
		startLabel.setFont(font);
		startLabel.setForeground(Color.WHITE);
	}

}
