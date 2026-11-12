package vn.edu.ptit.libman.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import vn.edu.ptit.libman.Main5;

public class TestPaint {
    public static void main(String[] args) throws Exception {
        Main5 frame = new Main5();
        frame.pack();
        frame.setSize(780, 520);
        frame.setVisible(true); // make visible so peers are created

        // Wait a bit for layout to settle
        Thread.sleep(300);

        BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        frame.printAll(g2);
        g2.dispose();
        frame.dispose();

        ImageIO.write(img, "png", new File("f:/PTTKHT/BÁO CÁO(full)/screenshots/test_paint.png"));
        System.out.println("Saved test_paint.png");
    }
}

