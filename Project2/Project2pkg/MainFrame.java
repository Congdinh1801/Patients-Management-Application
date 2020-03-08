/*Author: Dinh Hoang
 * Project 2 - CS3381 OO Java development
 * Front-end for Patient Collection program
 * MainTester: setting up the main frame 
 */
package Project2pkg;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {

	public static void main(String[] args) {
		// GUIFrame frame = new GUIFrame();
		JFrame frame = new JFrame("PatientEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set icon for frame
		frame.setIconImage(new ImageIcon(".\\Project2pkg\\hospital.jpg").getImage());
		frame.setSize(800, 500);
		MainPanel myPanel = new MainPanel();
		frame.getContentPane().add(myPanel);
		frame.pack();

		// here's the part where i center the jframe on screen
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myPanel.doClose();
			}

		});
	}

}
