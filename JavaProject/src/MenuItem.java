import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuItem extends JPanel {
	
	JFrame frameStore;

	/**
	 * Create the panel.
	 */
	public MenuItem() {
		initialize();
	}
	private void initialize() {
		frameStore = new JFrame();
		frameStore.setBounds(100, 100, 651, 346);
		frameStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
