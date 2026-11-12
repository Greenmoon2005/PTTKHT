package vn.edu.ptit.libman.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vn.edu.ptit.libman.model.*;

/**
 * Lớp MockDatabase5 - Lưu trữ dữ liệu mô phỏng trong bộ nhớ khớp hoàn toàn với schema_libman_db_5.sql.
 * Cho phép chạy thử nghiệm và kiểm thử 2 module trơn tru ngay cả khi môi trường chưa cài MySQL Server.
 * Hậu tố 5 - Mã SV: B23DCCN015.
 */
public class MockDatabase5 {
    private static MockDatabase5 instance;

    public List<Category5> categories = new ArrayList<>();
    public List<Publisher5> publishers = new ArrayList<>();
    public List<Author5> authors = new ArrayList<>();
    public List<Document5> documents = new ArrayList<>();
    public List<DocumentCopy5> documentCopies = new ArrayList<>();
    public List<Reader5> readers = new ArrayList<>();
    public List<Staff5> staffs = new ArrayList<>();
    public List<BorrowSlip5> borrowSlips = new ArrayList<>();

    private MockDatabase5() {
        initData();
    }

    public static synchronized MockDatabase5 getInstance() {
        if (instance == null) {
            instance = new MockDatabase5();
        }
        return instance;
    }

    private Date parseDate(String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (Exception e) {
            return new Date();
        }
    }

    private void initData() {
        // 1. Categories
        Category5 cat1 = new Category5(1, "CAT-CNPM", "Kỹ thuật Phần mềm & PTTKHT", "Thiết kế kiến trúc phần mềm, UML.");
        Category5 cat2 = new Category5(2, "CAT-JAVA", "Lập trình Java & Web Enterprise", "Java Core, Swing, Spring Boot.");
        Category5 cat3 = new Category5(3, "CAT-CSDL", "Cơ sở Dữ liệu & Hệ phân tán", "CSDL quan hệ 3NF, SQL.");
        Category5 cat4 = new Category5(4, "CAT-AI-ML", "Trí tuệ Nhân tạo & Khoa học Dữ liệu", "Machine Learning, Deep Learning.");
        Category5 cat5 = new Category5(5, "CAT-MANG", "Mạng máy tính & An ninh Thông tin", "Giao thức TCP/IP, Mật mã học.");
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);
        categories.add(cat5);

        // 2. Publishers
        Publisher5 pub1 = new Publisher5(1, "NXB-GD", "NXB Giáo Dục Việt Nam", "Hà Nội", "024-38220801", "gd@nxbgd.vn");
        Publisher5 pub2 = new Publisher5(2, "NXB-TTTT", "NXB Thông Tin và Truyền Thông", "Hà Nội", "024-35772139", "nxb.tttt@mic.gov.vn");
        Publisher5 pub3 = new Publisher5(3, "NXB-KHKT", "NXB Khoa Học và Kỹ Thuật", "Hà Nội", "024-39423171", "khkt@nxbkhkt.vn");
        Publisher5 pub4 = new Publisher5(4, "NXB-OREILLY", "O'Reilly Media", "USA", "+1-707-827-7000", "orders@oreilly.com");
        publishers.add(pub1);
        publishers.add(pub2);
        publishers.add(pub3);
        publishers.add(pub4);

        // 3. Authors
        Author5 auth1 = new Author5(1, "TG-PTIT-01", "Học viện Công nghệ Bưu chính Viễn thông", "Bộ môn CNPM", "Việt Nam");
        Author5 auth2 = new Author5(2, "TG-MARTIN", "Robert C. Martin (Uncle Bob)", "Chuyên gia phần mềm thế giới", "Hoa Kỳ");
        Author5 auth3 = new Author5(3, "TG-HORST", "Cay S. Horstmann", "Giáo sư CS San Jose", "Hoa Kỳ");
        Author5 auth4 = new Author5(4, "TG-DATE", "C. J. Date", "Học giả đại số quan hệ 3NF", "Vương quốc Anh");
        authors.add(auth1);
        authors.add(auth2);
        authors.add(auth3);
        authors.add(auth4);

        // 4. Documents
        Textbook5 doc1 = new Textbook5(1, "DOC-PTTKHT-01", "Bài giảng Phát triển & Thiết kế Hệ thống Thông tin 2 (CNPM)", 
                2020, "Tiếng Việt", 85000.0, "Kệ A1-Khoa CNTT", 
                "Giáo trình cốt lõi giảng dạy các pha Đặc tả, Phân tích hướng đối tượng với UML, Thiết kế kiến trúc 3 lớp MVC, Cài đặt module và Kiểm thử đơn vị.", 
                cat1, pub2, "INT1408", "Phát triển Hệ thống Thông tin 2", "Đại học chính quy", 5);
        doc1.getAuthors5().add(auth1);

        Textbook5 doc2 = new Textbook5(2, "DOC-JAVA-02", "Lập trình Hướng đối tượng với Java & Swing UI", 
                2023, "Tiếng Việt", 120000.0, "Kệ A2-Khoa CNTT", 
                "Cẩm nang toàn diện về xây dựng ứng dụng máy tính để bàn với Java Swing, kiến trúc phân tầng DAO và kết nối JDBC.", 
                cat2, pub1, "INT1340", "Lập trình Hướng đối tượng", "Đại học chính quy", 4);
        doc2.getAuthors5().add(auth3);

        Book5 doc3 = new Book5(3, "DOC-CC-03", "Clean Code: A Handbook of Agile Software Craftsmanship", 
                2021, "Tiếng Anh", 450000.0, "Kệ B1-Khu Quốc tế", 
                "Tác phẩm kinh điển về kỹ thuật viết mã sạch, cấu trúc đặt tên chuẩn mực, nguyên lý SOLID và kiến trúc module trong dự án phần mềm.", 
                cat1, pub4, "978-0132350884", 464, "1st Edition", "Nguyên bản tiếng Anh");
        doc3.getAuthors5().add(auth2);

        Textbook5 doc4 = new Textbook5(4, "DOC-DB-04", "Thiết kế Cơ sở Dữ liệu Quan hệ & Chuẩn hóa 3NF nâng cao", 
                2022, "Tiếng Việt", 95000.0, "Kệ C1-Khoa CNTT", 
                "Lý thuyết đại số quan hệ, thiết kế mô hình thực thể ERD, phân rã quan hệ đa trị, chuyển đổi từ mô hình quan niệm sang CSDL MySQL.", 
                cat3, pub3, "INT1332", "Cơ sở Dữ liệu", "Đại học chính quy", 3);
        doc4.getAuthors5().add(auth4);

        Book5 doc5 = new Book5(5, "DOC-AI-05", "Trí tuệ nhân tạo & Mô hình hóa Hệ thống Thông minh", 
                2024, "Tiếng Việt", 150000.0, "Kệ D1-Khoa KHMT", 
                "Khái niệm nền tảng AI, mạng nơ-ron học sâu, biểu diễn tri thức và ứng dụng tối ưu hóa luồng nghiệp vụ trong các hệ thống thông tin quy mô lớn.", 
                cat4, pub3, "978-604-913-882-1", 380, "Tái bản lần 2", "Không");
        doc5.getAuthors5().add(auth1);

        documents.add(doc1);
        documents.add(doc2);
        documents.add(doc3);
        documents.add(doc4);
        documents.add(doc5);

        // 5. Document Copies
        DocumentCopy5 c1 = new DocumentCopy5(1, "BC-PTTKHT-001", 1, 1, 2021, "Có sẵn", "Mới 98%", "Ngăn 1 - Kệ A1-01", doc1);
        DocumentCopy5 c2 = new DocumentCopy5(2, "BC-PTTKHT-002", 1, 2, 2021, "Có sẵn", "Mới 95%", "Ngăn 1 - Kệ A1-02", doc1);
        DocumentCopy5 c3 = new DocumentCopy5(3, "BC-PTTKHT-003", 1, 3, 2022, "Đang mượn", "Tốt", "Ngăn 1 - Kệ A1-03", doc1);
        doc1.getCopies5().add(c1);
        doc1.getCopies5().add(c2);
        doc1.getCopies5().add(c3);

        DocumentCopy5 c4 = new DocumentCopy5(4, "BC-JAVA-001", 2, 1, 2023, "Có sẵn", "Mới 100%", "Ngăn 2 - Kệ A2-01", doc2);
        DocumentCopy5 c5 = new DocumentCopy5(5, "BC-JAVA-002", 2, 2, 2023, "Có sẵn", "Mới 99%", "Ngăn 2 - Kệ A2-02", doc2);
        DocumentCopy5 c6 = new DocumentCopy5(6, "BC-JAVA-003", 2, 3, 2023, "Bảo trì", "Sờn gáy", "Kho kỹ thuật", doc2);
        doc2.getCopies5().add(c4);
        doc2.getCopies5().add(c5);
        doc2.getCopies5().add(c6);

        DocumentCopy5 c7 = new DocumentCopy5(7, "BC-CC-001", 3, 1, 2022, "Có sẵn", "Mới 99%", "Ngăn 3 - Kệ B1-01", doc3);
        DocumentCopy5 c8 = new DocumentCopy5(8, "BC-CC-002", 3, 2, 2022, "Có sẵn", "Mới 97%", "Ngăn 3 - Kệ B1-02", doc3);
        doc3.getCopies5().add(c7);
        doc3.getCopies5().add(c8);

        DocumentCopy5 c9 = new DocumentCopy5(9, "BC-DB-001", 4, 1, 2022, "Có sẵn", "Mới 95%", "Ngăn 1 - Kệ C1-01", doc4);
        DocumentCopy5 c10 = new DocumentCopy5(10, "BC-DB-002", 4, 2, 2023, "Có sẵn", "Mới 100%", "Ngăn 1 - Kệ C1-02", doc4);
        doc4.getCopies5().add(c9);
        doc4.getCopies5().add(c10);

        DocumentCopy5 c11 = new DocumentCopy5(11, "BC-AI-001", 5, 1, 2024, "Có sẵn", "Mới 100%", "Ngăn 2 - Kệ D1-01", doc5);
        doc5.getCopies5().add(c11);

        documentCopies.add(c1);
        documentCopies.add(c2);
        documentCopies.add(c3);
        documentCopies.add(c4);
        documentCopies.add(c5);
        documentCopies.add(c6);
        documentCopies.add(c7);
        documentCopies.add(c8);
        documentCopies.add(c9);
        documentCopies.add(c10);
        documentCopies.add(c11);

        // 6. Readers & Cards
        Card5 card1 = new Card5(1, "CARD-B23DCCN015", 1, parseDate("2023-10-01"), parseDate("2027-10-31"), "Active", "CARD-015");
        Reader5 r1 = new Reader5(1, "tuananh_bd", "123456", "Đỗ Đức Tuấn Anh", parseDate("2005-05-15"), 
                "Nam", "tuananh.b23dccn015@stu.ptit.edu.vn", "0988123455", "Km10 Nguyễn Trãi, Hà Đông, Hà Nội", "Reader", 
                "B23DCCN015", "Công nghệ thông tin 1", "D23CQCN01-B", parseDate("2027-10-31"), 5, true, card1);

        Card5 card2 = new Card5(2, "CARD-B23DCCN020", 2, parseDate("2023-10-01"), parseDate("2027-10-31"), "Active", "CARD-020");
        Reader5 r2 = new Reader5(2, "lethihuong_bd", "123456", "Lê Thị Hương", parseDate("2005-08-20"), 
                "Nữ", "huonglt@stu.ptit.edu.vn", "0912345678", "Thanh Xuân, Hà Nội", "Reader", 
                "B23DCCN020", "An toàn thông tin", "D23CQAT02-B", parseDate("2027-10-31"), 5, true, card2);

        Card5 card3 = new Card5(3, "CARD-B22DCVT055", 3, parseDate("2022-10-01"), parseDate("2026-10-31"), "Active", "CARD-055");
        Reader5 r3 = new Reader5(3, "tranduc_bd", "123456", "Trần Văn Đức", parseDate("2004-03-12"), 
                "Nam", "ductv@stu.ptit.edu.vn", "0934567890", "Cầu Giấy, Hà Nội", "Reader", 
                "B22DCVT055", "Điện tử viễn thông", "D22CQVT01-B", parseDate("2026-10-31"), 5, true, card3);

        readers.add(r1);
        readers.add(r2);
        readers.add(r3);

        // 7. Staffs
        Staff5 staff1 = new Staff5(4, "thuthu_01", "123456", "Nguyễn Thị Minh Nguyệt", parseDate("1990-11-25"), 
                "Nữ", "nguyetntm@ptit.edu.vn", "0977889900", "Quầy Lưu thông Tầng 2 Thư viện PTIT", "Staff", 
                "STAFF-015", "Thủ thư quầy Lưu thông", "Phòng Công tác Độc giả", 14500000.0);
        staffs.add(staff1);

        // 8. Sample Borrow Slip
        BorrowSlip5 slip1 = new BorrowSlip5();
        slip1.setId(1);
        slip1.setSlipCode("SLIP-20261101-001");
        slip1.setReader5(r1);
        slip1.setStaff5(staff1);
        slip1.setBorrowDate(parseDate("2026-11-01"));
        slip1.setDueDate(parseDate("2026-11-15"));
        slip1.setStatus("Đang mượn");
        slip1.setNote("Bạn đọc mượn phục vụ nghiên cứu môn PTTKHT");

        BorrowDetail5 d1 = new BorrowDetail5(1, slip1, c3, "Đang mượn", null, 0.0, null);
        slip1.addBorrowDetail(d1);
        borrowSlips.add(slip1);
    }
}

