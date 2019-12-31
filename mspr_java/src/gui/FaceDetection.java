package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgcodecs.Imgcodecs.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceDetection extends JFrame {

	/**
	 * TODO : trouver un moyen de convertir Rect en Mat 
	 */
	public int i = 0;
	
	private static final long serialVersionUID = 1L;
	public Rect recordImg;
	private JPanel contentPane;
	private DaemonThread myThread = null;
	int count = 0;
	VideoCapture webSource = null;
	Mat frame = new Mat();
	MatOfByte mem = new MatOfByte();

	CascadeClassifier faceDetector = new CascadeClassifier(
			FaceDetection.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));

	CascadeClassifier plaqueDetector = new CascadeClassifier(
			FaceDetection.class.getResource("haarcascade_eye_tree_eyeglasses.xml").getPath().substring(1));

	MatOfRect faceDetections = new MatOfRect();
	static ImageIcon icon = new ImageIcon("mspr_java\\img\\iconapp.png");

///
	class DaemonThread implements Runnable {

		protected volatile boolean runnable = false;

		@Override
		public void run() {
			synchronized (this) {
				while (runnable) {
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Graphics g = contentPane.getGraphics();

							faceDetector.detectMultiScale(frame, faceDetections);
							for (Rect rect : faceDetections.toArray()) {
								// System.out.println("Mama mia");
								Imgproc.rectangle(frame, new Point(rect.x, rect.y),
										new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

								if (rect != null) {
									// System.out.println("face detected");
								}
								recordImg = rect;
							}
							
							plaqueDetector.detectMultiScale(frame, faceDetections);
							for (Rect rect : faceDetections.toArray()) {
								// System.out.println("Mama mia");
								Imgproc.rectangle(frame, new Point(rect.x, rect.y),
										new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

								if (rect != null) {
									// System.out.println("face detected");
								}
								recordImg = rect;
							}


							Imgcodecs.imencode(".bmp", frame, mem);
							Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
							BufferedImage buff = (BufferedImage) im;
							if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(),
									buff.getHeight(), null)) {
								if (runnable == false) {
									System.out.println("Pause.. ");
									JOptionPane.showMessageDialog(null, "Pause..");
									this.wait();
								}
							}
						} catch (Exception ex) {
							System.out.println("Error");
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

///
///Launch the app
///	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FaceDetection frame = new FaceDetection();
					frame.setVisible(true);
					frame.setIconImage(icon.getImage());
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FaceDetection() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 911, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		setTitle("Detecteur de face");
		contentPane.add(panel, BorderLayout.CENTER);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(230).addContainerGap(229, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup().addContainerGap(443, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		// start button
		JButton btnStart = new JButton("Start");
		btnStart.setForeground(SystemColor.textHighlight);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webSource = new VideoCapture(0); // video capture from default cam
				myThread = new DaemonThread(); // create object of threat class
				Thread t = new Thread(myThread);
				t.setDaemon(true);
				myThread.runnable = true;
				t.start(); // start thread
				btnStart.setEnabled(false); // deactivate start button
				btnStart.setEnabled(true); // activate stop button

			}
		});
		contentPane.add(btnStart, BorderLayout.WEST);

		// stop button
		JButton btnStop = new JButton("Stop");
		btnStop.setForeground(Color.RED);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myThread.runnable = false; // stop thread
				btnStop.setEnabled(false); // activate start button
				btnStop.setEnabled(true); // deactivate stop button

				webSource.release(); // stop capturing from camera
			}
		});
		contentPane.add(btnStop, BorderLayout.EAST);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i++;
				/*
				 * Rect to Mat ??? 
				 * Currently save all the frame.
				 * */
				// Mat recordimg = recordImg;
				// Imgcodecs.imwrite("img/imgserver/personne1.png", recordImg);
				System.out.println("save picture x" + i);
				Imgcodecs.imwrite("img/imgserver/myface.png", frame);
				
			}
		});
		contentPane.add(btnLogin, BorderLayout.SOUTH);
	}
}
