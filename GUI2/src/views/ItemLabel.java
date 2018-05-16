package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sun.javafx.sg.prism.web.NGWebView;

import constant.Constants;
import main.MyFrame;

public class ItemLabel extends JPanel {

	private String text;
	// 边长
	private int side = MyFrame.WIDTH / 10;
	// 内边距
	private int padding = side / 15;

	private TransparentLabel label;

	// 点击之后边框变白色，表示已选择状态
	private boolean chosenState = false;
	private Border chosenBorder;

	private int fontSize = 18;

	public ItemLabel(String text) {
		this(text, 1.f);
	}

	public ItemLabel(String text, float alpha, int side, int fontSize,int borderHeight) {
		this.side = side;
		padding = side / 15;
		this.fontSize = fontSize;
		this.text = text;
		setBackground(null);
		setOpaque(false);
		label = new TransparentLabel(text, alpha, new Font("黑体", Font.BOLD, fontSize));
		label.setBackground(new Color(Constants.WINE_RED));
		chosenBorder = BorderFactory.createLineBorder(new Color(Constants.WINE_RED), borderHeight);
		measureAndLayout();
	}

	public ItemLabel(String text, float alpha) {
		this.text = text;
		setBackground(null);
		setOpaque(false);
		label = new TransparentLabel(text, alpha, new Font("黑体", Font.PLAIN, fontSize));
		label.setBackground(new Color(Constants.WINE_RED));
		
		chosenBorder = BorderFactory.createLineBorder(new Color(Constants.WINE_RED), 3);
		measureAndLayout();
		// setListeners();
	}

	private void measureAndLayout() {
		setLayout(null);
		setBackground(null);
		setOpaque(false);
		setSize(side, side);
		int innerSide = side - padding * 2;

		label.setBounds(padding, padding, innerSide, innerSide);
		label.setForeground(Color.WHITE);
		add(label);
	}

	private void setListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				chosenState = !chosenState;
				setChosenBorder(chosenState);
			}
		});
	}

	public float getAlpha() {
		return label.getAlpha();
	}

	public void setAlpha(float alpha) {
		label.setAlpha(alpha);
	}

	public void setChosenBorder(boolean chosen) {
		chosenState = chosen;
		setBorder(chosen ? chosenBorder : null);
	}

	public boolean getChosenState() {
		return chosenState;
	}

	public TransparentLabel getInnerLabel() {
		return label;
	}
}
