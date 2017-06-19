import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class HelloApp {

	public static void main(String[] args) {

		//need to have a thread running in. Don't understand why but..
		MainFrame mainFrame = new MainFrame(); //create the MainFrame object
        Thread thread  = new Thread(mainFrame); //run in a new thread
        thread.start(); //start the thread
		
		
	}

}
