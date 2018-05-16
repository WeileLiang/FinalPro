import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import constant.Info;
import constant.Info.TimeLine;
import main.MyFrame;
import views.TimeLineItem;
import views.TransparentPanel;

public class Test extends JFrame {

	static int i = 0;

	public static void main(String[] args) {
		 Test frame = new Test();
		 frame.setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
		 frame.setLocationRelativeTo(null);
		 frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 frame.setVisible(true);
		
	}

	float alpha = 1.f;

	public Test() {
		setLayout(null);
		JLabel label=new JLabel();
		ImageIcon icon=new ImageIcon(".\\images\\logo.jpg");
		label.setIcon(icon);
		label.setBounds(10, 10, icon.getIconWidth()	, icon.getIconHeight());
		add(label);
	}

}
