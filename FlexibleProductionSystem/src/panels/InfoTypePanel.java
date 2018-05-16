package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.JPanel;

import constant.Constants;
import main.MyFrame;
import module.ChoicePanel;
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
	
	public InfoTypePanel() {
		initViews();
		measureAndLayout();
		setListeners();
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		userInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				MyFrame.globalFrame.addFurtherPanel(new UserInfoPanel(), 1);
			}
		});
		
		materialInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				MyFrame.globalFrame.addFurtherPanel(new MaterialInfoPanel(), 1);
			}
		});
		
	}

	private void initViews() {
		setLayout(null);
		setSize(MyFrame.WIDTH, MyFrame.HEIGHT);

		setBackground(null);
		setOpaque(false);

		Font font = new Font("黑体", Font.PLAIN, 30);
		userInfoLabel = new TransparentLabel("用户信息", 1.f, font);
		materialInfoLabel = new TransparentLabel("物料信息", 1.f, font);
		
		Color bgColor=new Color(Constants.WINE_RED);
		userInfoLabel.setForeground(Color.WHITE);
		userInfoLabel.setBackground(bgColor);
		materialInfoLabel.setForeground(Color.WHITE);
		materialInfoLabel.setBackground(bgColor);
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
}
