package nhu.phan.Battleship;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * ClientHandler class manage the connection between two clients during a battleship game and the communication in the chat room
 * between the two clients.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class ClientHandler implements Runnable, PropertyChangeListener {	
	private Socket clientSocket1;
	private Socket clientSocket2;
	
	// Output Stream for Client1
	private OutputStream outStream1;
	private ObjectOutputStream objOutStream1;
	
	// Output Stream for CLient 2
	private OutputStream outStream2;
	private ObjectOutputStream objOutStream2;

	/**
	 *  Constructor assigns values for clientSocket1 and clientSocket2, and create two InputListener objects
	 *  to the corresponding client socket
	 * @param socket1 Client 1 socket connection
	 * @param socket2 Client 2 socket connection
	 */
	public ClientHandler(Socket socket1, Socket socket2) {
		this.clientSocket1 = socket1;
		this.clientSocket2 = socket2;

		// create InputListener1 to handle the input stream 
		// coming from Client 1
		InputListener client1InputListener = new InputListener(socket1, this, 1);
		new Thread(client1InputListener).start();
		
		// create InputListener2 to handle the input stream 
		// coming from Client 2
		InputListener client2InputListener = new InputListener(socket2, this, 2);
		new Thread(client2InputListener).start();
	}
	
	/**
	 * Direct incoming message, game move or if one client left mid-game to the other player 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {	
		// get the id of the InputListener 
		int id = ((InputListener) evt.getSource()).getId();
		
		try {
			// if message is from CLient 1
			if (id == 1) {
				// send the message to Client 2
				objOutStream2.writeObject(evt.getNewValue());
			}
			
			// if message is from Client 2f
			if (id == 2) {
				// send the message to Client 1
				objOutStream1.writeObject(evt.getNewValue());
			}
		} catch (SocketException e) {
			
		}
		catch (IOException e) {
		}
		

	}
	
	/**
	 * Create output stream and object output stream for the two clients and determine which client goes turn in the first round
	 */
	@Override
	public void run() {
		try {
			// Output Stream for Client1
			outStream1 = clientSocket1.getOutputStream();
			objOutStream1 = new ObjectOutputStream(outStream1);
			
			// Output Stream for Client 2
			outStream2 = clientSocket2.getOutputStream();
			objOutStream2 = new ObjectOutputStream(outStream2);	
			
			int count = 0;
			
			// determine who goes first and let the players knows a battleship started
			if(count == 0) {
				int number = (int) (Math.random() * 2 + 1);
				
				if(number == 1) {
					objOutStream1.writeObject("start");
					objOutStream2.writeObject("wait");
				}
				
				if(number == 2) {
					objOutStream1.writeObject("wait");
					objOutStream2.writeObject("start");
				}

			}
			
			count++;
		} 
		catch(IOException e) {}
		
	}
	
}