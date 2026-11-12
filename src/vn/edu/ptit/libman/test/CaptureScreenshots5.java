package vn.edu.ptit.libman.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import vn.edu.ptit.libman.Main5;
import vn.edu.ptit.libman.model.*;
import vn.edu.ptit.libman.presentation.reader_ui.*;
import vn.edu.ptit.libman.presentation.staff_ui.*;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Tiện ích chụp ảnh màn hình giao diện thực tế của toàn bộ các Form trong 2 Module.
 * Render trực tiếp từ Java Swing sang file PNG độ phân giải cao phục vụ Báo cáo.
 */
public class CaptureScreenshots5 {

    private static final String OUTPUT_DIR = "f:/PTTKHT/BÁO CÁO(full)/screenshots/";

    public static void main(String[] args) {
        System.out.println("Đang chụp ảnh các giao diện thực tế của hệ thống LibMan...");
        new File(OUTPUT_DIR).mkdirs();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        try {
            // 1. Main Launcher
            Main5 mainFrm = new Main5();
            captureFrame(mainFrm, "screen_01_main_launcher_5.png", 780, 520);

            // 2. Main Reader Screen
            MainReaderFrm5 readerMainFrm = new MainReaderFrm5();
            captureFrame(readerMainFrm, "screen_02_reader_home_5.png", 900, 600);

            // 3. Search Document Screen
            SearchDocFrm5 searchFrm = new SearchDocFrm5();
            captureFrame(searchFrm, "screen_03_search_doc_5.png", 1000, 680);

            // 4. Document Detail Screen
            DocDetailFrm5 detailFrm = new DocDetailFrm5(null, 1);
            captureFrame(detailFrm, "screen_04_doc_detail_5.png", 950, 680);

            // 5. Main Staff Screen
            MainStaffFrm5 staffMainFrm = new MainStaffFrm5();
            captureFrame(staffMainFrm, "screen_05_staff_home_5.png", 920, 600);

            // 6. Borrow Document Screen (Simulated with loaded data)
            BorrowDocFrm5 borrowFrm = new BorrowDocFrm5(null, MockDatabase5.getInstance().staffs.get(0));
            simulateBorrowDoc(borrowFrm);
            captureFrame(borrowFrm, "screen_06_borrow_doc_5.png", 1020, 720);

            // 7. Print Slip Screen
            BorrowSlip5 sampleSlip = MockDatabase5.getInstance().borrowSlips.get(0);
            PrintBorrowSlipFrm5 printFrm = new PrintBorrowSlipFrm5(null, sampleSlip);
            captureFrame(printFrm, "screen_07_print_slip_5.png", 850, 720);

            System.out.println("Đã chụp thành công toàn bộ 7 giao diện thực tế vào: " + OUTPUT_DIR);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void simulateBorrowDoc(BorrowDocFrm5 frm) {
        try {
            java.lang.reflect.Field txtCardField = BorrowDocFrm5.class.getDeclaredField("txtCardCode");
            txtCardField.setAccessible(true);
            JTextField txtCard = (JTextField) txtCardField.get(frm);
            txtCard.setText("CARD-015");

            frm.btnCheckCardActionPerformed(null);

            java.lang.reflect.Field txtCopyField = BorrowDocFrm5.class.getDeclaredField("txtCopyBarcode");
            txtCopyField.setAccessible(true);
            JTextField txtCopy = (JTextField) txtCopyField.get(frm);

            txtCopy.setText("BC-PTTKHT-001");
            frm.btnAddCopyActionPerformed(null);

            txtCopy.setText("BC-JAVA-001");
            frm.btnAddCopyActionPerformed(null);
        } catch (Exception e) {
            System.err.println("Lỗi simulate: " + e.getMessage());
        }
    }

    private static void captureFrame(JFrame frame, String fileName, int width, int height) throws Exception {
        frame.pack();
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(350);

        BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        frame.printAll(g2);
        g2.dispose();
        frame.dispose();

        File outFile = new File(OUTPUT_DIR + fileName);
        ImageIO.write(img, "png", outFile);
        System.out.println("  -> Đã lưu ảnh: " + fileName + " (" + width + "x" + height + ", " + outFile.length() + " bytes)");
    }
}
