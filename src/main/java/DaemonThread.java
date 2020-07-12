import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

class DaemonThread extends Application implements Runnable {

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
                            // System.out.println("ttt");
                            Imgproc.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                    new Scalar(0, 255, 0));
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