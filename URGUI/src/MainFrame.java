import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainFrame extends JFrame implements Runnable  {

	// private JTextArea textArea;
	private JButton button;
	private TextPanel txtPanel;
	private ToolBar toolBar;
	private MovePanel movePanel;
	private Combination connection;
	private boolean isServer; // used to see whether client or server is on.
	private boolean isClosed = true; // used to closed thread
	

	public MainFrame() {
		super("This is the main frame");
		setLayout(new BorderLayout());
		txtPanel = new TextPanel();
		button = new JButton("Click me!");
		toolBar = new ToolBar();
		movePanel = new MovePanel();

		// add(textArea, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		add(txtPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);
		add(movePanel, BorderLayout.WEST);

		// This sets the size of the window ( super small by default);
		setSize(700, 500);

		/*
		 * This makes it so they when you close the app window, the programs
		 * will automatically stop running
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Jframe not visible by default so this need to be called to make it
		 * visible
		 */
		setVisible(true);
		
		isServer = false; // start of at client
		isClosed = false; // 
		connection = new Combination(20000, 29999, "192.168.0.10");
		
		
		
		toolBar.setCommandListener(new CommandListener() {
			@Override
			public void sendCommand(int command) {
				System.out.println("sending commands");
				TextPanel.append(Integer.toString(command));
				switch (command){
					case 0: connection.getcommand("test read");
							break;
					case 3: connection.getcommand("freedrive on");
							break;

					case 5: connection.getcommand("freedrive off");
							break;
					case 7: connection.getcommand("power on");
							sleep(6000);
							connection.getcommand("brake release");
							sleep(5000);
							connection.getcommand("load Dm/tcp_comm_dm_V2.urp");
							sleep(5000);
							connection.getcommand("play");
							isServer = true;
							break;
					case 9: connection.getcommand("get tcp");
							break;

					case 64: connection.getcommand("play");
							isServer = true;
							break;	
					case 168:
							System.out.println("shutdown was pressed");
							connection.getcommand("shutdown");
							isServer = false;
							sleep(1000);
							connection.getcommand("stop");
							sleep(3000);
							connection.getcommand("power off");
							break;
 				}
				System.out.println(command);
			}
		});
		
		movePanel.setCommandListener(new CommandListener(){

			@Override
			public void sendCommand(int command) {
				// TODO Auto-generated method stub
				switch(command){
					case 10 : connection.getcommand("z+"); // move in z+ direction
							  break;
					case 11 : connection.getcommand("z-"); // move in z- direction
							  break;
					case 12 : connection.getcommand("x+"); // move in x+ direction
						      break;
					case 13 : connection.getcommand("x-"); // move in x- direction
					    	  break;
					case 14 : connection.getcommand("y+"); // move in y+ direction
			    	  		  break;
					case 15 : //connection.getcommand("y-"); // move in y- direction
			    	  		  break;
				}
			}
			
		});
		
		movePanel.setMouseListener(new MouseAction() {
			
			@Override
			public void sendCommand(boolean isPressed) {
				connection.getcommand("y-");
				//sleep(3000);
			}
		});

	}
	
	
	@Override
	public void run() {
		System.out.println("Running Thread");
		 while(!isClosed) //run application until the user closes it.
	        {
	            sleep(100); //add a small timeout to free up the processor to do other things.
	            if(isServer) //if server mode (variable is set in the enableServerActionPerformed method, when the enableServer radio button is selected)
	            {
	                try {
						connection.connect_server();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //initialize the server mode
	            }
	            if(!isServer) //if the connect button was pressed, then the application is trying to connect to the server
	            {
	            	System.out.println("Connecting to client");
	                connection.connect_client(); //initialize the client mode
	                //readIncomingMessages(); //read all incoming messages
	            }
	        }
	}
	
	private void sleep(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(Exception e)
        {
            System.exit(1);
        }
    }
}
