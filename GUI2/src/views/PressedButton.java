package views;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import adapter.ClickedListener;
import constant.Constants;

public class PressedButton extends JLabel{
	public Color originColor=Color.gray;
	public Color pressedColor=new Color(Constants.LIGHT_GRAY);
	
	private ClickedListener listener;
	
	public PressedButton(String text) {
		super(text,JLabel.CENTER);
		setOpaque(true);
		setBackground(originColor);
		setFocusable(true);
		addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(originColor);
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(pressedColor);
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(listener!=null) listener.onClick(PressedButton.this);
			}
		});
	}
	
	public void setOnClickedListener(ClickedListener listener) {
		this.listener=listener;
	}
}
