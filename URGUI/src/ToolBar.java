import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.PlainDocument;
import javax.xml.bind.Marshaller.Listener;

public class ToolBar extends JPanel implements ActionListener {

	private JButton testReadButton;
	private JButton freeDriveButton;
	private JButton shutdownButton;
	private JButton allOnButton;
	private JButton tcpButton;

	private boolean freedriveOn = false;
	private CommandListener listener;

	public ToolBar() {
		/* Mode Buttons */
		testReadButton = new JButton("Test Read");
		freeDriveButton = new JButton("freedrive");
		shutdownButton = new JButton("shutdown");
		allOnButton = new JButton("on");
		tcpButton = new JButton("get tcp");

		testReadButton.addActionListener(this);
		freeDriveButton.addActionListener(this);
		shutdownButton.addActionListener(this);
		allOnButton.addActionListener(this);
		tcpButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(allOnButton);
		add(shutdownButton);
		add(freeDriveButton);
		add(testReadButton);
		add(tcpButton);

		allOnButton.setEnabled(true);
		freeDriveButton.setEnabled(false);
		shutdownButton.setEnabled(false);
		testReadButton.setEnabled(false);
		tcpButton.setEnabled(false);
	}

	public void setCommandListener(CommandListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("a buttons was pressed");
		JButton clicked = (JButton) e.getSource();
		if (listener != null) {
			if (clicked.equals(testReadButton)) {
				listener.sendCommand(0);
			} else if (clicked.equals(freeDriveButton)) {

				shutdownButton.setEnabled(true);
				allOnButton.setEnabled(false);
				testReadButton.setEnabled(true);
				if (freedriveOn == false) {
					listener.sendCommand(3);// freedrive on
					freedriveOn = true;
				} else if (freedriveOn == true) {
					listener.sendCommand(5); // freedrive off
					freedriveOn = false;

				}
			} else if (clicked.equals(shutdownButton)) {

				listener.sendCommand(168);
				allOnButton.setEnabled(true);
				freeDriveButton.setEnabled(false);
				testReadButton.setEnabled(false);
				shutdownButton.setEnabled(false);

			} else if (clicked.equals(allOnButton)) {

				listener.sendCommand(7);
				freeDriveButton.setEnabled(true);
				shutdownButton.setEnabled(true);
				testReadButton.setEnabled(true);
				allOnButton.setEnabled(false);
				
			} else if(clicked.equals(tcpButton)) {
				listener.sendCommand(9);
				freeDriveButton.setEnabled(true);
				shutdownButton.setEnabled(true);
				testReadButton.setEnabled(true);
				allOnButton.setEnabled(false);
			}
		}
	}

}
