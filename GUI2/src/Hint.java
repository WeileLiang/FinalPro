import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Hint extends JFrame implements MouseListener
{
    private static final long serialVersionUID = 1L;
    private static final String TIP = "QQ∫≈¬Î/ ÷ª˙/” œ‰";
 
    public Hint ()
    {
        setTitle ("test");
        setLayout (new BorderLayout ());
        final JTextField tf = new JTextField (TIP);
        tf.setName ("tf");
        tf.setForeground (Color.gray);
        tf.setLocation (0, 0);
        tf.setSize (100, 30);
        tf.addMouseListener (this);
        JPanel panel = new JPanel ();
        panel.setName ("panel");
        panel.addMouseListener (this);
        panel.setLayout (null);
        panel.add (tf);
        panel.setFocusable (true);
        add (panel);
        setSize (150, 60);
        setLocationRelativeTo (null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible (true);
    }
 
    public static void main ( String[] args )
    {
        new Hint ();
    }
 
    @Override
    public void mouseClicked ( MouseEvent e )
    {
        Component component = e.getComponent ();
        String name = component.getName ();
        if (e.getButton () == MouseEvent.BUTTON1)
        {
            if ("tf".equals (name))
            {
                JTextField tf = (JTextField) component;
                if (TIP.equals (tf.getText ()))
                {
                    tf.setText ("");
                }
            }
            else if ("panel".equals (name))
            {
                JTextField tf = (JTextField) ( (JPanel) component ).getComponents ()[0];
                if ("".equals (tf.getText ()))
                {
                    tf.setText (TIP);
                }
            }
        }
    }
 
    @Override
    public void mousePressed ( MouseEvent e )
    {}
 
    @Override
    public void mouseReleased ( MouseEvent e )
    {}
 
    @Override
    public void mouseEntered ( MouseEvent e )
    {}
 
    @Override
    public void mouseExited ( MouseEvent e )
    {}
}