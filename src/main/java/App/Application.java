package App;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.image.DataBufferByte;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Application extends javax.swing.JFrame {

    Employe employe1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public javax.swing.JPanel jPanel1;
    private JTextField txtLogin;
    private JTextField txtMotDePasse;
    static DaemonThread myThread = null;
    static Rect rectCrop = null;
    int x = 20, y = 25;
    private BufferedImage image_1, image_2;
    private boolean image_1_load_status = false, image_2_load_status = false;
    private JLabel labelCamera = new JLabel("Lancez la webcam");
    private JLabel labelStart = new JLabel("Pour commencer la reconnaissance");
    private static Firebase database;

    Path CascadePath = FileSystems.getDefault().getPath("src", "main", "resources", "haarcascade_frontalface_alt.xml");
    Path ResourcesPath = FileSystems.getDefault().getPath("src", "main", "resources");
    Path ImagesTempPath = FileSystems.getDefault().getPath("src", "main", "resources", "imagesTemp");
    String GoSecuriLogoPath = (ResourcesPath.toString() + "\\LogoSecuri.png");
    String EpsiLogoPath = (ResourcesPath.toString() + "\\LogoEpsi.png");

    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    MatOfRect faceDetections = new MatOfRect();

    static VideoCapture webSource = null;
    CascadeClassifier faceDetector = new CascadeClassifier(CascadePath.toString());

    JLabel labelGoSecuriLogo = new JLabel(new ImageIcon(GoSecuriLogoPath));
    JLabel labelEpsi = new JLabel(new ImageIcon(EpsiLogoPath));

    public Application() {
        initComponents();
        System.out.println(CascadePath.toString());
    }

    public static void main(String args[]) throws IOException {
        nu.pattern.OpenCV.loadLocally();
        database = new Firebase();

        /*
        try {
            db = new App.FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    public void compareImages(BufferedImage img_1, BufferedImage img_2) throws IOException {//Its called by above method compare_image()
        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0),
                new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0),
                new Mat(), hist_2, histSize, ranges);

        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        Double d = new Double(res * 100);

        disp_percen(d.intValue());

    }

    private Mat conv_Mat(BufferedImage img) {
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        Mat mat1 = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2HSV);

        return mat1;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);



        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(labelGoSecuriLogo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 502, Short.MAX_VALUE)
                                .addComponent(labelEpsi))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(labelGoSecuriLogo, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelEpsi))
                                .addContainerGap(450, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        labelCamera.setBounds(250, 230, 400, 30);
        labelCamera.setFont(new Font("Open Sans", Font.BOLD, 30));
        labelCamera.setForeground(Color.black);
        labelCamera.setVisible(true);
        jPanel1.add(labelCamera);

        labelStart.setBounds(210, 270, 400, 20);
        labelStart.setFont(new Font("Open Sans", Font.BOLD, 20));
        labelStart.setForeground(Color.black);
        labelStart.setVisible(true);
        jPanel1.add(labelStart);

        jButton1.setText("D\u00E9marrer la webcam");
        jButton1.setBackground(new Color(55, 158, 193));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Arr\u00EAter la webcam");
        jButton2.setBackground(new Color(55, 158, 193));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtLogin = new JTextField();
        txtLogin.setText("Login");
        txtLogin.setColumns(10);

        txtMotDePasse = new JTextField();
        txtMotDePasse.setText("Mot de passe");
        txtMotDePasse.setColumns(10);

        jButton3.setText("Se connecter");
        jButton3.setBackground(new Color(55, 158, 193));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    AjoutPhotoInFirebase(evt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    ComparaisonPhotos(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton1)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(jButton2)
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(txtMotDePasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(jButton3))
                                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 743, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3)
                                        .addComponent(txtMotDePasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        webSource = new VideoCapture(0);
        myThread = new DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
        jButton1.setEnabled(false);
        jButton2.setEnabled(true);

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        myThread.runnable = false;
        jButton2.setEnabled(false);
        jButton1.setEnabled(true);

        webSource.release();


    }

    private void AjoutPhotoInFirebase(java.awt.event.ActionEvent evt) throws InterruptedException, ExecutionException, IOException {
        Mat crop = new Mat(frame, rectCrop);
        Imgcodecs.imwrite((ImagesTempPath.toString() + "\\image1.png"), crop);
        //database.ajoutImage(1);

    }

    private void ComparaisonPhotos(java.awt.event.ActionEvent evt) throws IOException {
        Mat crop = new Mat(frame, rectCrop);
        //database.getImage(employe1);
        Imgcodecs.imwrite((ImagesTempPath.toString() + "\\image2.png"), crop);

        try {

            image_2 = ImageIO.read(new File(ImagesTempPath.toString() + "\\image2.png"));
            image_2_load_status = true;
            image_2 = img_resize(image_2);//this method created under below as img_resize

        } catch (IOException e1) {

        }

        try {
            image_1 = ImageIO.read(new File(ImagesTempPath.toString() + "\\image1.png"));
            image_1_load_status = true;
            image_1 = img_resize(image_1);//this method created under below as img_resize

        } catch (IOException e1) {

        }

        if (image_1_load_status && image_2_load_status) {

            try {
                compareImages(image_1, image_2);//this method created under below as compare_image
            } catch (IOException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

        }
    }

    private BufferedImage img_resize(BufferedImage img_temp) {  //Its called by above method img_resize()
        BufferedImage dimg = new BufferedImage(180, 180, img_temp.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img_temp, 0, 0, 179, 179, 0, 0, img_temp.getWidth(), img_temp.getHeight(), null);
        g.dispose();
        return dimg;
    }

    void disp_percen(int d) throws IOException {

        if (d >= 97) {

            JOptionPane.showMessageDialog(null, "Welcome Login\n" + "Similarity : " + d + " %");
            Application.myThread.runnable = false;// stop thread
            Application.webSource.release(); // stop caturing fron cam

            Component comp = SwingUtilities.getRoot(this);// dispose or close this Frame
            ((Window) comp).dispose();

            new Dashboard();

        } else {
            JOptionPane.showMessageDialog(null, " Login Failed \n" + "Similarity : " + d + " %");

        }
    }

    public static Firebase getDb() {
        return database;
    }


    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            for (Rect rect : faceDetections.toArray()) {
                                Imgproc.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
                                rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
                            }
                            Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("La caméra à été mise en pause");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Erreur !");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
