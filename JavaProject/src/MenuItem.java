import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class MenuItem extends JPanel {
	
	JFrame frameStore;
	private JTextField textField;
	BufferedReader objReader;
	String strCurrentLine;
	HashMap<String, String> map = new HashMap<String, String>();
	JPanel panel = new JPanel();
	String value;
	JScrollPane scrollPane;
	JTextArea textArea;
	
	FileWriter fw = null; 
	BufferedWriter bw = null;
	PrintWriter pw = null;

	//www.java67.com/2015/07/how-to-append-text-to-existing-file-in-java-example.html#ixzz6R9kr41HQ
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public MenuItem() throws IOException {
		initialize();
		getData();
	}
	private void initialize() {
		frameStore = new JFrame();
		frameStore.setBounds(100, 100, 651, 346);
		frameStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frameStore.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Datas :");
		lblNewLabel.setBounds(20, 11, 89, 14);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveData();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(536, 273, 89, 23);
		panel.add(btnNewButton);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(30, 46, 258, 159);
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		

	}
	
	public void getData() throws IOException {
		 try {
	            // Read some text from the resource file to display in
	            // the JTextArea.
	            textArea.read(new InputStreamReader(
	                    getClass().getResourceAsStream("/img/data.txt")),
	        null);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		/*String line;
	       BufferedReader reader = new BufferedReader(new FileReader(file));
	       while ((line = reader.readLine()) != null)
	       {
	           String[] parts = line.split(":", 2);
	           if (parts.length >= 2)
	           {
	               String key = parts[0];
	               String value = parts[1];
	               map.put(key, value);
	           } else {
	               System.out.println("ignoring line: " + line);
	           }
	       }

	       for (String key : map.keySet())
	       {
	    	
	    	System.out.println(key + ":" + map.get(key));
	    	value = key + ":" + map.get(key);
	    
	       }*/
	       
	       JTextField textPane = new JTextField();
	   	   textPane.setBounds(103, 67, 116, 78);
	   	   panel.add(textPane);  
	      // reader.close();
	}
	
	public void saveData () throws IOException {
		 String contentsArea = textArea.getText();
		 System.out.println(contentsArea);
		 String test = "test";
		 try { 
			 fw = new FileWriter("/img/data.txt", true);
		 bw = new BufferedWriter(fw); 
		 pw = new PrintWriter(bw);
		 pw.println("Shane"); 
		 System.out.println("Data Successfully appended into file"); 
		 pw.flush();
		 }
		 
		 finally { try { pw.close(); bw.close(); fw.close(); } catch (IOException io) { } }

	}
	
	public void onReload() {
		// Lorsque j'ouvre cette frame je reload le fichier
	}
}
