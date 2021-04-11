package nhu.phan.Battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * Server class create a GUI to start and stop the server, and log server real-time activity to console window.  Server will pair two clients 
 * together to start a game of battleship.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class Server extends JFrame implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// text area to log server activities
	private static JTextArea logActivityArea = new JTextArea(25, 40);
	private JButton startButton = new JButton("Start Server");
	private JButton stopButton = new JButton("Stop Server");
	
	// server port number it be listening for clients
	private static final int PORT_NUMBER = 9090;
	public static ServerSocket serverSocket;
	
	// ArrayList of Client Sockets
	public static ArrayList<Socket> clientList = new ArrayList<>();
	
	/**
	 * Constructor start the server GUI 
	 */
	public Server() {
		generateGUI();
		
		setTitle("Battleship Server");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Generate the server GUI
	 */
	private void generateGUI() {
		// main window
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);
		
		// server activities log area
		logActivityArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logActivityArea);
		mainPanel.add(scrollPane);
		DefaultCaret caret = (DefaultCaret) logActivityArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// start button
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.PAGE_END);
		buttonPanel.add(startButton);
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 18);
		startButton.setFont(font);
		startButton.setBackground(Color.WHITE);
		startButton.setForeground(Color.BLACK);
		
		stopButton.setFont(font);
		stopButton.setBackground(Color.WHITE);
		stopButton.setForeground(Color.BLACK);
		
		// start button listener
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}
		});
		
		// stop button
		stopButton.setEnabled(false);
		buttonPanel.add(stopButton);
		
		// stop button listener
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});
	}
	
	/**
	 * Start up the server
	 */
	private void startServer() {
		// disable start button
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		new Thread(this).start();
	}
	
	/**
	 * Close the server
	 */
	private void stopServer() {
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		
		try {
			serverSocket.close();
		} catch (IOException e) {}
		
		logActivity("Server is no longer running.");
	}
	
	/**
	 * Format server activities with a date and time stamp and log it to the GUI window
	 * @param message The real-time server activity
	 */
	public static void logActivity(String message) {
		// get current date and time
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  h:mm a");
		String dateTimeStamp = dateFormat.format(dateTime);
		
		// log server activity to window
		logActivityArea.append(dateTimeStamp + " > " + message + "\n");
	}
	
	/**
	 * Accept a client connection request and pair up every two clients to start a battleship game
	 */
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			logActivity("Server is running.");
			
			// infinite loop to keep server running while listening for clients connection
			while(true) {
				Socket clientSocket = serverSocket.accept();
				clientList.add(clientSocket);
				logActivity("Accepted a Client connection.");
				
				// create ClientHandler when there are two clients connected to the server
				synchronized(clientList) {
					if(clientList.size() == 2) {
						
						ClientHandler clientHandler = new ClientHandler(clientList.get(0), clientList.get(1));
						new Thread(clientHandler).start();
						
						logActivity("A Battleship Game Started.");
						
						clientList.clear();
					}
				}
							
			}
		} catch (SocketException e) {
			
		} catch (IOException e) {

		}	
	}
	
}
