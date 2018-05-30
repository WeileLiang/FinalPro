package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.MyFrame;
import panels.InfoTypePanel;
import views.ShadePanel;

/**
 * 信息管理模块
 * 
 * @author Will
 *
 */
public class InfoManagePanel extends JPanel {
	// 选择信息类型
	private InfoTypePanel infoTypePanel;

	// 位于选择信息类型界面
	private static final int IN_INFO_TYPE = 0;
	// 位于特定的信息管理界面
	private static final int IN_INFO = 1;
	// 记录当前处于哪个界面
	private int state = IN_INFO_TYPE;

	private JLabel returnLabel;

	public InfoManagePanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void setListeners() {
		returnLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				MyFrame.globalFrame.back2LastPanel();
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setLayout(null);
		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		setBackground(null);
		setOpaque(false);

		returnLabel = new JLabel("< 返回");
		Font font = new Font("黑体", Font.PLAIN, 18);
		returnLabel.setFont(font);
		returnLabel.setForeground(Color.WHITE);
		infoTypePanel = new InfoTypePanel();

	}

	private void measureAndLayout() {
		returnLabel.setBounds(MyFrame.RETURN_LEFT_MARGIN, MyFrame.RETURN_TOP_MARGIN, 100, 50);
		
		infoTypePanel.setBounds(0, 0, infoTypePanel.getWidth(), infoTypePanel.getHeight());
		add(returnLabel);
		add(infoTypePanel);
	}

	public void back2InfoTypePanel() {
		infoTypePanel.backHere();
	}
	
}
