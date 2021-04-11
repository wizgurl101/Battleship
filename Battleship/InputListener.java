package nhu.phan.Battleship;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * InputListener class handle any incoming message from the server to direct to the client
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class InputListener implements Runnable{
	
	private int id;
	private Socket clientSocket;
	private InputStream inStream;
	private ObjectInputStream objInStream;
	private PropertyChangeListener observer;
	
	/**
	 * Constructor assigns values for id, clientsocket and observer.
	 * @param socket Client socket
	 * @param observer GameClient class that is observing for any change for the message property
	 * @param id Unique id to help determine which client send the message
	 */
	public InputListener(Socket socket, PropertyChangeListener observer, int id) {
		this.id = id;
		this.clientSocket = socket;
		this.observer = observer;
	}
	
	/**
	 * Return the value of id.
	 * @return id Unique id to help determine which client send the message
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Create the input and object input stream for the client socket and notify any observer(s) of incoming message from 
	 * the server
	 */
	@Override
	public void run() {
		try {
				inStream = clientSocket.getInputStream();
				objInStream = new ObjectInputStream(inStream);
			
				while(true) {	
					notifyObservers(objInStream.readObject());
				}
				
		} catch(SocketException e) {
			
		} catch(EOFException e) {
			
		} catch(IOException | ClassNotFoundException | NullPointerException e) {
		
		} finally {
			try {
				objInStream.close();
			} catch (IOException | NullPointerException e) {}
		}
	}
	
	/**
	 * Retrieve the new value of the message send from the other client and pass it to the observer propertChange method
	 * @param newValue The new value of the message from the other client
	 */
	private void notifyObservers(Object newValue) {
		observer.propertyChange(new PropertyChangeEvent(this, null, null, newValue));
	}

}
