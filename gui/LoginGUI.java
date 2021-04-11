package nhu.phan.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * LoginGUI class generate a login Window GUI for client to enter a username and display the game rules to the client
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class LoginGUI extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField usernameField = new JTextField(26);
	private static String username;
	
	/**
	 * Constructor initialize the GUI of the Login Window.
	 */
	public LoginGUI() {
		setTitle("Battleship Game Login");
		
		generateGUI();
		
		// set dialog to modal so that user must enter a username 
		setModal(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	/**
	 * Create the Login Window GUI and add a listener to the login button to get username from client's input
	 */
	private void generateGUI() {
		// main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(Color.WHITE);
		add(mainPanel);
		
		// title label
		JLabel titleLabel = new JLabel("Welcome to Battleship!");
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		titleLabel.setFont(font);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add(titleLabel, BorderLayout.PAGE_START);
		
		// text contain game rules
		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		messagePanel.setBackground(Color.BLACK);
		JTextArea messageArea = new JTextArea(10, 45);
		messageArea.setEditable(false);
		messageArea.setBackground(Color.WHITE);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		
		String message = "\nInstructions\n\n"
							+ "1) You must enter a username and click the login button to play\n\n"
							+ "2) You can move the ship to any location on the fleet map with your mouse\n\n"
							+ "3) You cannot change the ship's orientation\n\n"
							+ "4) Click on the Start Game button to be pair with another player\n\n"
							+ "5) Have fun!\n\n";
		
		messageArea.setText(message);
		messagePanel.add(messageArea);
		mainPanel.add(messagePanel);
		
		JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		usernamePanel.setBackground(Color.WHITE);
		JLabel usernameLabel = new JLabel("Enter a username: ");
		font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		usernameLabel.setFont(font);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		mainPanel.add(usernamePanel);
		
		// login button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		JButton loginButton = new JButton("Login");
		font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		loginButton.setFont(font);
		loginButton.setBackground(Color.WHITE);
		loginButton.setForeground(Color.BLACK);
		
		buttonPanel.add(loginButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		// listener
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
	}
	
	/**
	 * Method get client input for validation and if input is valid then it to assign the input's value 
	 * to the String variable username and set visible of the login window to false.
	 */
	private void login() {
		// get the username input
		String input = usernameField.getText().trim();
		
		if(input.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must enter a username");
		} else {
			setUsername(input);
			setVisible(false);
		}
	}

	/**
	 * Return the client username as a String.
	 * @return username String holding the value of the client's username
	 */
	public static String getUsername() {
		return username;
	}
	
	/**
	 * Assign username the value of the passed parameter.
	 * @param username String holding the value of the client's username
	 */
	public void setUsername(String username) {
		LoginGUI.username = username;
	}


}
