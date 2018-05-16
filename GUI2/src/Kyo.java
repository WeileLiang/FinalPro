import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Kyo extends JFrame {
	public Kyo() {
		setSize(100, 100);
		setLayout(new FlowLayout());
		final JTextField textField = new JTextField(11);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				textField.setText(" ‰»Îƒ⁄»›");
			}
		});
		add(textField);
		add(new JButton("dd"));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Kyo();
	}
}