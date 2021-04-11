
package nhu.phan.drivers;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import nhu.phan.Battleship.GameClient;

/**
 * ClientDriver class start up the Game Client GUI
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class ClientDriver {

	public static void main(String[] args) throws NullPointerException {
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameClient();
			}
		});

	}

}
