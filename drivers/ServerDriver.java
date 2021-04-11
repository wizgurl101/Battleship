package nhu.phan.drivers;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import nhu.phan.Battleship.Server;

/**
 * ServerDriver class start up the server GUI 
 * @author Nhu Phan
 * @version October 6, 2020
 *
 */
public class ServerDriver {

	public static void main(String[] args) {
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Server();
			}
		});
	
	}

}
