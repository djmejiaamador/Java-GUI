import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

public class MovePanel extends JPanel  implements ActionListener, MouseListener {
	/* Move Buttons */
	private JButton z_plus;
	private JButton z_minus;
	private JButton x_plus;
	private JButton x_minus;
	private JButton y_plus;
	private JButton y_minus;

	/* Button Icons */
	private ImageIcon z_plusImage;
	private ImageIcon z_minusImage;
	private ImageIcon x_plusImage;
	private ImageIcon x_minusImage;
	private ImageIcon y_plusImage;
	private ImageIcon y_minusImage;
	
	/* listeners */
	private CommandListener listener;
	private MouseAction mouseListener;
	
	boolean isPressed;

	public MovePanel() {

		setLayout(new GridLayout(0, 2));
		
		/* Icons */
		z_plusImage  = new ImageIcon("z_plus.png" , "z-");
		z_minusImage = new ImageIcon("z_minus.png", "z-");
		x_plusImage  = new ImageIcon("x_plus.png" , "x+");
		x_minusImage = new ImageIcon("x_minus.png", "x-");
		y_plusImage  = new ImageIcon("y_plus.png" , "y+");
		y_minusImage = new ImageIcon("y_minus.png", "y-");
		
		/* Movement Buttons */
		z_plus = new JButton(z_plusImage);
		// to remote the spacing between the image and button's borders
		z_plus.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		z_plus.setBorder(null);
		z_plus.addActionListener(this);

		z_minus = new JButton(z_minusImage);
		z_minus.setMargin(new Insets(0, 0, 0, 0));
		z_minus.setBorder(null);
		z_minus.addActionListener(this);

		x_plus = new JButton(x_plusImage);
		x_plus.setMargin(new Insets(0, 0, 0, 0));
		x_plus.setBorder(null);
		x_plus.addActionListener(this);
		
		x_minus = new JButton(x_minusImage);
		x_minus.setMargin(new Insets(0, 0, 0, 0));
		x_minus.setBorder(null);
		x_minus.addActionListener(this);

		y_plus = new JButton(y_plusImage);
		y_plus.setMargin(new Insets(0, 0, 0, 0));
		y_plus.setBorder(null);
		y_plus.addActionListener(this);

		y_minus = new JButton(y_minusImage);
		y_minus.setMargin(new Insets(0, 0, 0, 0));
		y_minus.setBorder(null);
		y_minus.addActionListener(this);
		
		y_minus.addMouseListener(this);

		add(z_plus);
		add(z_minus);
		add(x_plus);
		add(x_minus);
		add(y_plus);
		add(y_minus);
		

	}
	
	public void setCommandListener(CommandListener listener){
		this.listener = listener;
	}
	
	public void setMouseListener(MouseAction mouseListener){
		this.mouseListener = mouseListener;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if(clicked != null){
			if(clicked.equals(z_plus)){
				listener.sendCommand(10);
				
			}else if(clicked.equals(z_minus)){
				listener.sendCommand(11);
			}else if(clicked.equals(x_minus)){
				listener.sendCommand(12);
			}else if(clicked.equals(x_plus)){
				listener.sendCommand(13);
			}else if(clicked.equals(y_plus)){
				listener.sendCommand(14);
			}else if(clicked.equals(y_minus)){
				//listener.sendCommand(15);
			}
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		isPressed = true;
		initThread();
	}
		
			

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouse Released is: " + e.MOUSE_RELEASED);
		isPressed = false;
	}
	
	private void initThread() {
	    if (checkAndMark()) {
	        new Thread() {
	            public void run() {
	                do {
	                	mouseListener.sendCommand(isPressed);;
	                    try {
							Thread.sleep(850);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                } while (isPressed);
	                isRunning = false;
	            }
	        }.start();
	    }
	
	
}
	volatile private boolean isRunning = false;
	private synchronized boolean checkAndMark() {
	    if (isRunning) return false;
	    isRunning = true;
	    return true;
	}
}