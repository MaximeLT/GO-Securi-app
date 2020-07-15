package App;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Dashboard {
    private static JFrame frame;
    /**
     * Create the application.
     * @throws IOException
     */
    public Dashboard() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * @throws IOException
     */
    static private void initialize() throws IOException {

        frame = new JFrame();
        frame.setBounds(100, 100, 705, 533);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JButton btnIdentify = new JButton("S'identifier");
        btnIdentify.setBackground(new Color(55, 158, 193));
        btnIdentify.setOpaque(true);
        btnIdentify.setBounds(10, 11, 133, 45);
        panel.add(btnIdentify);
        btnIdentify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frame.dispose();
                new Application().setVisible(true);
            }
        });



        JLabel lblNewLabel = new JLabel(new ImageIcon("D:\\database_opencv\\me.png"));
        lblNewLabel.setBackground(new Color(55, 158, 193));
        lblNewLabel.setOpaque(true);
        lblNewLabel.setBounds(479, 11, 200, 202);
        panel.add(lblNewLabel);

        JCheckBox chckbxMousqueton = new JCheckBox("Mousqueton");
        chckbxMousqueton.setBounds(223, 281, 97, 23);
        panel.add(chckbxMousqueton);

        JCheckBox chckbxGant = new JCheckBox("Gant d'intervention");
        chckbxGant.setBounds(223, 307, 172, 23);
        panel.add(chckbxGant);

        JCheckBox chckbxCeinture = new JCheckBox("Ceinture de s\u00E9curit\u00E9 tactique");
        chckbxCeinture.setBounds(223, 333, 307, 23);
        panel.add(chckbxCeinture);

        JCheckBox chckbxDetecteur = new JCheckBox("D\u00E9tecteur de m\u00E9taux");
        chckbxDetecteur.setBounds(223, 359, 185, 23);
        panel.add(chckbxDetecteur);

        JCheckBox chckbxBrassard = new JCheckBox("Brassard de s\u00E9curit\u00E9");
        chckbxBrassard.setBounds(223, 385, 212, 23);
        panel.add(chckbxBrassard);

        JCheckBox chckbxLampe = new JCheckBox("Lampe torche");
        chckbxLampe.setBounds(223, 411, 185, 23);
        panel.add(chckbxLampe);

        JCheckBox chckbxGilet = new JCheckBox("Gilet pare balle");
        chckbxGilet.setBounds(223, 437, 185, 23);
        panel.add(chckbxGilet);

        frame.setVisible(true);

    }
}