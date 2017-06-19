import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {
	
	private static JTextArea txtArea;
	
	public TextPanel(){
		setLayout(new BorderLayout());
		txtArea = new JTextArea();
		//JScrollPane is to give scrolls so that things dont run out of room
		add(new JScrollPane(txtArea), BorderLayout.CENTER);
	}
	
	public static void append(String s){
		txtArea.append(s + "\n");
	}

}
