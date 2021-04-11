package nhu.phan.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ChatGUI class generate the GUI for the chat room.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class ChatGUI {
	
	public static JTextArea messageInputArea = new JTextArea(5, 35);
	public static JTextArea messageBoardArea = new JTextArea(18, 35);
	public static JButton sendButton = new JButton("Send");
	
	/**
	 * Return a JPanel of all the graphical components of the chat room
	 * @return chatPanel JPanel that contains all the graphical components of the chat
	 */
	public static JPanel generateChatPanel() {
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// margin for both message input and chat areas
		Insets marginSetting = new Insets(5, 5, 5, 5);
		
		// title label
		JLabel titleLabel = new JLabel("Chat Room");
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		titleLabel.setFont(font);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		chatPanel.add(titleLabel, BorderLayout.PAGE_START);
		
		// main panel-window
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(Color.WHITE);
		chatPanel.add(mainPanel, BorderLayout.CENTER);
		
		// message area
		JPanel messageLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		messageLabelPanel.setBackground(Color.WHITE);
		JLabel messageLabel = new JLabel("Message to Send:");
		font = new Font(Font.MONOSPACED, Font.PLAIN, 15);
		messageLabel.setFont(font);
		messageLabelPanel.add(messageLabel);
		mainPanel.add(messageLabelPanel);
		
		messageInputArea.setLineWrap(true);
		messageInputArea.setWrapStyleWord(true);
		messageInputArea.setMargin(marginSetting);
		
		// disable message input area when chat GUI first is generated
		messageInputArea.setEditable(false);
		messageInputArea.setBackground(Color.lightGray);
			
		// add a scroll to message area if input is too long
		JScrollPane messageInputScrollPane = new JScrollPane(messageInputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(messageInputScrollPane);
		
		// send button
		JPanel sendButtonPanel = new JPanel();
		sendButtonPanel.setBackground(Color.WHITE);
		// font style for all labels in main screen
		font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		sendButton.setFont(font);
		sendButton.setBackground(Color.WHITE);
		sendButton.setForeground(Color.BLACK);
		sendButtonPanel.add(sendButton);
		mainPanel.add(sendButtonPanel);
		
		// disable send button when not connected to server
		sendButton.setEnabled(false);
		
		// message board
		JPanel messageBoardLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		messageBoardLabelPanel.setBackground(Color.WHITE);
		JLabel messageBoardLabel = new JLabel("Message Board");
		messageBoardLabel.setFont(font);
		messageBoardLabelPanel.add(messageBoardLabel);
		mainPanel.add(messageBoardLabelPanel);
		
		messageBoardArea.setEditable(false);
		messageBoardArea.setLineWrap(true);
		messageBoardArea.setWrapStyleWord(true);
		messageBoardArea.setMargin(marginSetting);
		messageBoardArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JScrollPane messageBoardInputScrollPane = new JScrollPane(messageBoardArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(messageBoardInputScrollPane);
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.BLACK);
		chatPanel.add(emptyPanel, BorderLayout.EAST);
		
		return chatPanel;
	}
}
