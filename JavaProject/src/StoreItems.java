import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.text.html.ImageView;
import javafx.scene.image.*;
import javafx.*;
import javax.imageio.*;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class StoreItems {

	private JFrame frame;
	private JTextField textFieldId;
	private JTextField textFieldMdp;
	static JOptionPane errorMsg;
	private JPanel panel_1;
	private Mat matrix = null;
	private JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoreItems window = new StoreItems();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public StoreItems() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 651, 346);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldId = new JTextField("admin");
		textFieldId.setBounds(417, 180, 86, 20);
		panel.add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldMdp = new JTextField("admin");
		textFieldMdp.setColumns(10);
		textFieldMdp.setBounds(516, 180, 86, 20);
		panel.add(textFieldMdp);
		
		JButton btnNewButtonLogin = new JButton("Login");
		btnNewButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getLogin();
			}
		});
		btnNewButtonLogin.setBounds(516, 227, 89, 23);
		btnNewButtonLogin.setBackground(Color.decode("#379EC1"));
		panel.add(btnNewButtonLogin);
		
		String urlImg = "C:/Users/Shadow/eclipse-workspace/JavaProject/src/img/Capture.PNG";
		ImageIcon image = new ImageIcon(urlImg);
		
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(371, 11, 254, 135);
		panel.add(lblNewLabel);
	
		
		getCamera();
	}
	
	public void getLogin () {
		
		String login = textFieldId.getText();
		String mdp = textFieldMdp.getText();
		
		if (login.equals("admin") && mdp.equals("admin")) {
			try {
				MenuItem menuItem = new MenuItem();
				menuItem.frameStore.setVisible(true);
				frame.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else {
			JOptionPane.showMessageDialog(null, "Erreur de login", "Attention", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void getCamera() {

	}
	
	
	
	public void saveImage() {
	      // Saving the Image
	      String file = "C:/Users/Shadow/Desktop/facedetected.jpg";
	      // Instantiating the imagecodecs class
	      Imgcodecs imageCodecs = new Imgcodecs();

	      imageCodecs.imwrite(file, matrix);
	   }
}
