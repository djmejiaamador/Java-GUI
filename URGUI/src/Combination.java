import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;

public class Combination {

	private final int serverPort;
	private final int clientPort;
	private final String hostName;
	private int serverMode = 1;
	private String command;

	Combination(int serverPort, int clientPort, String hostName) {
		this.serverPort = serverPort;
		this.clientPort = clientPort;
		this.hostName = hostName;
	}

	/**
	 * Establishes a connection to the Universal Robot. Dash board connection
	 * allows user to enter Dash board commands Server connections allows user
	 * to enter program commands
	 */
	public void connect() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

			if (serverMode == 0) {
				// connect_server(in);
			} else if (serverMode == 1) {

			}

			in.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with the buffered reader");
			e.printStackTrace();
		}

		System.out.println("ending all connections.");

	}

	/**
	 * Connects to the Dash board Client of the Universal Robot
	 * 
	 * @param in
	 *            The BufferedReader for console input;
	 */
	public void connect_client() {
		System.out.println("Client mode started");
		System.out.println("Trying to connect to Universal Robot Dashboard");
		try(Socket dashClient = new Socket(hostName,clientPort)){
			if(dashClient.isConnected()){
				System.out.println("successfully connected to client");
			}
			System.out.println("dash connected: " + dashClient.isConnected());
				send_and_recieve(dashClient);
			
		}

		 catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// "(0.0, 0, 0.001, 0, 0, 0)"

	/**
	 * Connected to program client
	 * 
	 * @param in
	 *            Buffered reader to get input form command line
	 * @throws InterruptedException 
	 */
	public void connect_server() throws InterruptedException {

		System.out.println("Server mode started. listening for connection...");
		try (ServerSocket server = new ServerSocket(serverPort);
				Socket serverClient = server.accept();) {
			System.out.println("Server connected to robotClient: " + serverClient.isConnected());
			//String input, recieved = null;
			while (!serverClient.isClosed()) {
				System.out.println("server is a bout to go into send_recieve");
				send_and_recieve(serverClient);
			}
		} catch (UnknownHostException e) {
			System.out.println("Cant access that host" + e.toString());
		} catch (IOException e) {
			System.out.println("Something went wrong with the IO" + e.toString());
			e.printStackTrace();
		}
	}

	public void getcommand(String com) {
		System.out.println("In get command");
		command = com;
	}
	
	
	public void send_and_recieve(Socket connection) throws IOException, InterruptedException {
		try (PrintWriter out = new PrintWriter(connection.getOutputStream());
				BufferedReader clientReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
			System.out.println("in Send_recieve");
			String recieved;
//			if( (recieved = clientReader.readLine()) != null){
//				
//				System.out.println("DASHPORT SAYS: " + recieved);
//				
//			}
			System.out.println("ABOUT TO ENTER WHILE LOOP IN SEND COMMAND");
			while(!connection.isClosed()){
				if(command == null){
					continue;
				} 
				
				if( command.equalsIgnoreCase("shutdown")){ // shutdown everything : quite server open client close that
					command = null;
					break;
				}
				
				System.out.println("send is: " + command);
				TextPanel.append("Command: " + command);
				out.println(command);
				out.flush();
				recieved = clientReader.readLine();
				System.out.println("DASHPORT SAYS: " + recieved);
				TextPanel.append("DASHPORT SAYS: " + recieved);
				if(command.equalsIgnoreCase("play")){ // this is causing it to close the server
					command = null;
					break;
					
				}
				command = null;
				//Thread.sleep(100);
				
			}
				
			/*closing the print writer or the BufferedReader closed the socket connection */
			System.out.println("client in closed: " + connection.isClosed());
			connection.close();
			out.close();
			clientReader.close();
			
		}
	}

}
