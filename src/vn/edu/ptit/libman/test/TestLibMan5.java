package vn.edu.ptit.libman.test;

import java.util.Date;
import java.util.List;
import vn.edu.ptit.libman.dao.*;
import vn.edu.ptit.libman.model.*;
import vn.edu.ptit.libman.util.DateUtil5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Bộ kiểm thử tự động TestLibMan5 cho cả 2 Module.
 * Kiểm chứng tính đúng đắn và so khớp 100% với kịch bản các pha trước.
 * Hậu tố 5 - Sinh viên: Đỗ Đức Tuấn Anh - B23DCCN015.
 */
public class TestLibMan5 {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("   BỘ KIỂM THỬ TỰ ĐỘNG HỆ THỐNG LIBMAN - ĐỀ 01 - SINH VIÊN B23DCCN015   ");
        System.out.println("   SO KHỚP TOÀN DIỆN VỚI KỊCH BẢN USE CASE VÀ SƠ ĐỒ THIẾT KẾ PHA TRƯỚC   ");
        System.out.println("================================================================================\n");

        CategoryDAO5 categoryDAO = new CategoryDAO5();
        DocumentDAO5 documentDAO = new DocumentDAO5();
        DocumentCopyDAO5 copyDAO = new DocumentCopyDAO5();
        ReaderDAO5 readerDAO = new ReaderDAO5();
        BorrowSlipDAO5 slipDAO = new BorrowSlipDAO5();

        System.out.println("--- [TEST GROUP 1: MODULE 1 - BẠN ĐỌC TÌM THÔNG TIN TÀI LIỆU] ---");

        // Test 1: Nạp danh mục thể loại
        runTest("TC1.1 - Lấy toàn bộ danh sách thể loại nạp ComboBox", () -> {
            List<Category5> cats = categoryDAO.getAllCategories5();
            assertCondition(cats != null && !cats.isEmpty(), "Danh sách thể loại không được rỗng");
            assertCondition(cats.size() >= 5, "Số lượng thể loại tối thiểu là 5");
        });

        // Test 2: Tìm kiếm theo từ khóa
        runTest("TC1.2 - Tìm kiếm tài liệu theo từ khóa 'PTTKHT'", () -> {
            List<Document5> docs = documentDAO.searchDocument5("PTTKHT", 0);
            assertCondition(docs != null && !docs.isEmpty(), "Phải tìm thấy ít nhất 1 tài liệu");
            assertCondition(docs.get(0).getDocCode().contains("PTTKHT"), "Mã tài liệu phải chứa PTTKHT");
        });

        // Test 3: Tìm kiếm kết hợp theo thể loại
        runTest("TC1.3 - Tìm kiếm tài liệu theo thể loại 'Kỹ thuật Phần mềm' (CAT ID = 1)", () -> {
            List<Document5> docs = documentDAO.searchDocument5("", 1);
            assertCondition(docs != null && !docs.isEmpty(), "Phải tìm thấy tài liệu thuộc chuyên mục");
            for (Document5 d : docs) {
                assertCondition(d.getCategory5() != null && d.getCategory5().getId() == 1, 
                    "Tài liệu phải thuộc thể loại ID = 1");
            }
        });

        // Test 4: Tìm kiếm không có kết quả (Ngoại lệ A1 Module 1)
        runTest("TC1.4 - Tìm kiếm từ khóa không tồn tại -> Danh sách rỗng (Ngoại lệ A1)", () -> {
            List<Document5> docs = documentDAO.searchDocument5("TuKhoaKhongTonTai_XYZ_999", 0);
            assertCondition(docs != null && docs.isEmpty(), "Kết quả tìm kiếm phải rỗng");
        });

        // Test 5: Xem chi tiết tài liệu và danh sách bản sao vật lý
        runTest("TC1.5 - Lấy chi tiết tài liệu và các bản sao vật lý (DocDetailFrm5)", () -> {
            Document5 doc = documentDAO.getDocumentById5(1);
            assertCondition(doc != null, "Không tìm thấy tài liệu ID = 1");
            assertCondition(doc.getTitle() != null && !doc.getTitle().isEmpty(), "Tiêu đề không được để trống");
            List<DocumentCopy5> copies = copyDAO.getCopiesByDocumentId5(1);
            assertCondition(copies != null && !copies.isEmpty(), "Tài liệu phải có ít nhất 1 bản sao vật lý");
            assertCondition(copies.size() >= 3, "Cuốn PTTKHT phải có ít nhất 3 bản sao trong kho");
        });

        System.out.println("\n--- [TEST GROUP 2: MODULE 2 - NHÂN VIÊN CHO MƯỢN TÀI LIỆU] ---");

        // Test 6: Quét thẻ độc giả hợp lệ
        runTest("TC2.1 - Quét mã thẻ bạn đọc hợp lệ 'CARD-015' (Sinh viên B23DCCN015)", () -> {
            Reader5 reader = readerDAO.getReaderByCardBarcode5("CARD-015");
            assertCondition(reader != null, "Phải tìm thấy độc giả");
            assertCondition("B23DCCN015".equalsIgnoreCase(reader.getReaderCode()), "Mã sinh viên phải là B23DCCN015");
            assertCondition(reader.isActive(), "Tài khoản bạn đọc phải ở trạng thái Active");
            assertCondition(reader.getCard5() != null && reader.getCard5().isValid(), "Thẻ thư viện phải còn hạn");
        });

        // Test 7: Quét thẻ không tồn tại (Ngoại lệ A1 Module 2)
        runTest("TC2.2 - Quét mã thẻ không tồn tại 'CARD-INVALID-999' -> Trả về null", () -> {
            Reader5 reader = readerDAO.getReaderByCardBarcode5("CARD-INVALID-999");
            assertCondition(reader == null, "Phải trả về null khi mã thẻ không tồn tại");
        });

        // Test 8: Xác thực bản sao 'Có sẵn' để cho mượn
        runTest("TC2.3 - Kiểm tra bản sao khả dụng để mượn (BC-PTTKHT-001)", () -> {
            DocumentCopy5 copy = copyDAO.getAvailableCopyByBarcode5("BC-PTTKHT-001");
            assertCondition(copy != null, "Bản sao BC-PTTKHT-001 phải tồn tại");
            assertCondition(copy.isAvailable(), "Bản sao phải ở trạng thái 'Có sẵn'");
        });

        // Test 9: Bản sao đang mượn không thể cho mượn tiếp (Ngoại lệ A3 Module 2)
        runTest("TC2.4 - Bản sao 'Đang mượn' (BC-PTTKHT-003) -> Không khả dụng", () -> {
            DocumentCopy5 copy = copyDAO.getAvailableCopyByBarcode5("BC-PTTKHT-003");
            assertCondition(copy == null, "Bản sao đang mượn không được phép cho mượn tiếp");
        });

        // Test 10: Kiểm tra giới hạn mượn sách (< 5 cuốn)
        runTest("TC2.5 - Kiểm tra số sách đang giữ của bạn đọc", () -> {
            int currentCount = readerDAO.getCurrentBorrowedCount5(1);
            assertCondition(currentCount >= 0 && currentCount < 5, "Số sách mượn hợp lệ phải < 5");
        });

        // Test 11: Lưu giao dịch phiếu mượn (Transaction)
        runTest("TC2.6 - Thực thi giao dịch Lập phiếu mượn (Transaction SaveBorrowSlip5)", () -> {
            Reader5 reader = readerDAO.getReaderByCardBarcode5("CARD-015");
            Staff5 staff = MockDatabase5.getInstance().staffs.get(0);
            DocumentCopy5 copyToBorrow = copyDAO.getAvailableCopyByBarcode5("BC-JAVA-001");

            assertCondition(reader != null, "Độc giả phải tồn tại");
            assertCondition(copyToBorrow != null, "Bản sao mượn phải tồn tại");

            BorrowSlip5 slip = new BorrowSlip5();
            slip.setSlipCode("TEST-SLIP-" + System.currentTimeMillis() % 100000);
            slip.setReader5(reader);
            slip.setStaff5(staff);
            slip.setBorrowDate(new Date());
            slip.setDueDate(DateUtil5.addDays(new Date(), 14));
            slip.setStatus("Đang mượn");
            slip.setNote("Kiểm thử giao dịch mượn tự động");

            BorrowDetail5 detail = new BorrowDetail5();
            detail.setDocumentCopy5(copyToBorrow);
            detail.setBorrowStatus("Đang mượn");
            slip.addBorrowDetail(detail);

            boolean success = slipDAO.saveBorrowSlip5(slip);
            assertCondition(success, "Lưu phiếu mượn qua transaction phải thành công");
            assertCondition("Đang mượn".equalsIgnoreCase(copyToBorrow.getStatus()), 
                "Trạng thái bản sao sau khi mượn phải cập nhật thành 'Đang mượn'");
        });

        // Summary
        System.out.println("\n================================================================================");
        System.out.println("                    KẾT QUẢ TỔNG HỢP KIỂM THỬ (TEST SUMMARY)                    ");
        System.out.println("================================================================================");
        System.out.println("• Tổng số kịch bản kiểm thử: " + totalTests);
        System.out.println("• Số ca kiểm thử THÀNH CÔNG (PASSED): " + passedTests);
        System.out.println("• Số ca kiểm thử THẤT BẠI (FAILED)  : " + failedTests);
        if (failedTests == 0) {
            System.out.println("\n🎉 CHÚC MỪNG: 100% TEST CASES ĐỀU ĐẠT! SOURCE CODE SO KHỚP TUYỆT ĐỐI VỚI CÁC PHA TRƯỚC!");
        } else {
            System.err.println("\n⚠️ CÓ " + failedTests + " TEST CASE CHƯA ĐẠT.");
        }
        System.out.println("================================================================================\n");
    }

    private static void runTest(String testName, Runnable testLogic) {
        totalTests++;
        try {
            testLogic.run();
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } catch (Throwable t) {
            failedTests++;
            System.err.println("  [FAIL] " + testName + " => " + t.getMessage());
        }
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}

