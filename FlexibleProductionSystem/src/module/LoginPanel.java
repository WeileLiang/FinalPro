package module;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import adapter.OnClickedListener;
import adapter.OnNotifyListener;
import constant.AnimationUtil;
import main.MyFrame;
import views.PressedButton;

public class LoginPanel extends JPanel {

	public static final int LOGIN_EXIT_SIGNAL = 0;

	private JLabel userNameLabel = new JLabel("用户名:", JLabel.CENTER);
	private JLabel passwordLabel = new JLabel("密码:", JLabel.CENTER);
	private JTextField userNameField = new JTextField();
	private JTextField passwordField = new JTextField();

	private PressedButton loginBtn = new PressedButton("登录");
	private PressedButton registerBtn = new PressedButton("注册");

	private OnNotifyListener onNotifyListener;

	int width = MyFrame.WIDTH / 4;
	int height = MyFrame.HEIGHT / 4;

	int itemHeight = height / 5;

	int verticalGap = 8;

	public static int PERIOD = 5;
	public static int LAST_TIME = 500;
	private int curTime = 0;

	public LoginPanel() {

		initViews();
		measureAndLayout();
		setListeners();
	}

	private void initViews() {
		Font font = new Font("黑体", Font.PLAIN, 15);
		Font font2 = new Font("黑体", Font.PLAIN, 17);
		userNameLabel.setFont(font);
		passwordLabel.setFont(font);
		loginBtn.setFont(font2);
		registerBtn.setFont(font2);

		loginBtn.setBackground(Color.GRAY);
		loginBtn.setForeground(Color.WHITE);
		registerBtn.setBackground(Color.GRAY);
		registerBtn.setForeground(Color.WHITE);

		// 背景透明
		setBackground(null);
		setOpaque(false);

	}

	private void measureAndLayout() {
		setSize(width, height);
		setLayout(null);
		int curY = 0;
		int labelWidth = width / 3;
		int fieldWidth = width * 2 / 3;
		userNameLabel.setBounds(0, curY, labelWidth, itemHeight);
		userNameField.setBounds(userNameLabel.getWidth(), curY, fieldWidth, itemHeight);
		userNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		curY += itemHeight + verticalGap;
		passwordLabel.setBounds(0, curY, labelWidth, itemHeight);
		passwordField.setBounds(passwordLabel.getWidth(), curY, fieldWidth, itemHeight);
		passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		curY += itemHeight + 5 * verticalGap;
		int marginHorizontal = width / 8;
		int btnWidth = width / 3;
		loginBtn.setBounds(marginHorizontal, curY, btnWidth, itemHeight);
		registerBtn.setBounds(width - btnWidth - marginHorizontal, curY, width / 3, itemHeight);

		add(userNameLabel);
		add(userNameField);
		add(passwordLabel);
		add(passwordField);
		add(loginBtn);
		add(registerBtn);
	}

	private void setListeners() {
		OnClickedListener listener = new OnClickedListener() {

			@Override
			public void onClick(JComponent component) {
				AnimationUtil.doSlideAima(LoginPanel.this, (MyFrame.WIDTH - width) / 2, (MyFrame.WIDTH - width) / 2,
						(MyFrame.HEIGHT - height) / 2, MyFrame.HEIGHT, 500, 0, new OnNotifyListener() {

							@Override
							public void notifyParent(int singal) {
								// TODO Auto-generated method stub
								MyFrame.globalFrame.addFurtherPanel(new ChoicePanel(), 1);
							}
						});
			}
		};

		loginBtn.setOnClickedListener(listener);
		registerBtn.setOnClickedListener(listener);
	}

	public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
		this.onNotifyListener = onNotifyListener;
	}

}
