-- =============================================================================
-- HỌC VIỆN CÔNG NGHỆ BƯU CHÍNH VIỄN THÔNG (PTIT)
-- BÀI TẬP LỚN / BÀI TẬP CÁ NHÂN MÔN PHÂN TÍCH VÀ THIẾT KẾ HỆ THỐNG THÔNG TIN
-- ĐỀ TÀI: HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (LIBMAN) - ĐỀ 01
-- SINH VIÊN: ĐỖ ĐỨC TUẤN ANH - MÃ SINH VIÊN: B23DCCN015 (HẬU TỐ 5)
-- =============================================================================
-- CSDL CHUẨN 3NF TOÀN HỆ THỐNG (SO KHỚP HOÀN TOÀN VỚI SƠ ĐỒ THIẾT KẾ PHA TRƯỚC)
-- =============================================================================

DROP DATABASE IF EXISTS `libman_db_5`;
CREATE DATABASE `libman_db_5` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `libman_db_5`;

-- 1. Bảng tblUser5 (Lớp cha của Bạn đọc, Thủ thư, Quản lý)
CREATE TABLE `tblUser5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `dob` DATE NULL,
    `gender` VARCHAR(10) NULL,
    `email` VARCHAR(100) NULL,
    `phone` VARCHAR(20) NULL,
    `address` VARCHAR(255) NULL,
    `role` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Bảng tblReader5 (Bạn đọc / Độc giả)
CREATE TABLE `tblReader5` (
    `user_id` INT NOT NULL,
    `reader_code` VARCHAR(20) NOT NULL UNIQUE,
    `faculty` VARCHAR(100) NULL,
    `student_class` VARCHAR(50) NULL,
    `expiry_date` DATE NULL,
    `max_borrow_limit` INT NOT NULL DEFAULT 5,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `fk_reader_user5` FOREIGN KEY (`user_id`) REFERENCES `tblUser5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Bảng tblStaff5 (Thủ thư tác nghiệp)
CREATE TABLE `tblStaff5` (
    `user_id` INT NOT NULL,
    `staff_code` VARCHAR(20) NOT NULL UNIQUE,
    `position` VARCHAR(50) NULL,
    `department` VARCHAR(100) NULL,
    `salary` DECIMAL(12,2) NULL DEFAULT 0.00,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `fk_staff_user5` FOREIGN KEY (`user_id`) REFERENCES `tblUser5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Bảng tblManager5 (Người quản lý thư viện)
CREATE TABLE `tblManager5` (
    `user_id` INT NOT NULL,
    `management_level` VARCHAR(50) NULL,
    `office_room` VARCHAR(30) NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `fk_manager_user5` FOREIGN KEY (`user_id`) REFERENCES `tblUser5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Bảng tblCard5 (Thẻ thư viện bạn đọc)
CREATE TABLE `tblCard5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `card_code` VARCHAR(30) NOT NULL UNIQUE,
    `reader_id` INT NOT NULL UNIQUE,
    `issue_date` DATE NULL,
    `expire_date` DATE NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'Active',
    `barcode` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_card_reader5` FOREIGN KEY (`reader_id`) REFERENCES `tblReader5` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Bảng tblCategory5 (Thể loại / Chuyên mục tài liệu)
CREATE TABLE `tblCategory5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `category_code` VARCHAR(20) NOT NULL UNIQUE,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Bảng tblPublisher5 (Nhà xuất bản)
CREATE TABLE `tblPublisher5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `publisher_code` VARCHAR(20) NOT NULL UNIQUE,
    `name` VARCHAR(150) NOT NULL,
    `address` VARCHAR(255) NULL,
    `phone` VARCHAR(20) NULL,
    `email` VARCHAR(100) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Bảng tblAuthor5 (Tác giả)
CREATE TABLE `tblAuthor5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `author_code` VARCHAR(20) NOT NULL UNIQUE,
    `full_name` VARCHAR(100) NOT NULL,
    `biography` TEXT NULL,
    `nationality` VARCHAR(50) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Bảng tblDocument5 (Đầu mục tài liệu / Tựa sách)
CREATE TABLE `tblDocument5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `doc_code` VARCHAR(30) NOT NULL UNIQUE,
    `title` VARCHAR(255) NOT NULL,
    `category_id` INT NULL,
    `publisher_id` INT NULL,
    `publish_year` INT NULL,
    `language` VARCHAR(30) NULL DEFAULT 'Tiếng Việt',
    `price` DECIMAL(10,2) NULL DEFAULT 0.00,
    `location_shelf` VARCHAR(50) NULL,
    `summary` TEXT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_doc_category5` FOREIGN KEY (`category_id`) REFERENCES `tblCategory5` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_doc_publisher5` FOREIGN KEY (`publisher_id`) REFERENCES `tblPublisher5` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Bảng tblDocumentAuthor5 (Bảng trung gian N-N giữa Document5 và Author5)
CREATE TABLE `tblDocumentAuthor5` (
    `document_id` INT NOT NULL,
    `author_id` INT NOT NULL,
    PRIMARY KEY (`document_id`, `author_id`),
    CONSTRAINT `fk_da_doc5` FOREIGN KEY (`document_id`) REFERENCES `tblDocument5` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_da_author5` FOREIGN KEY (`author_id`) REFERENCES `tblAuthor5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Bảng tblBook5 (Kế thừa Document5)
CREATE TABLE `tblBook5` (
    `doc_id` INT NOT NULL,
    `isbn` VARCHAR(30) NULL,
    `number_of_pages` INT NULL,
    `book_edition` VARCHAR(30) NULL,
    `translator` VARCHAR(100) NULL,
    PRIMARY KEY (`doc_id`),
    CONSTRAINT `fk_book_doc5` FOREIGN KEY (`doc_id`) REFERENCES `tblDocument5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. Bảng tblTextbook5 (Kế thừa Document5 - Giáo trình đại học)
CREATE TABLE `tblTextbook5` (
    `doc_id` INT NOT NULL,
    `course_code` VARCHAR(30) NULL,
    `subject_name` VARCHAR(100) NULL,
    `academic_level` VARCHAR(50) NULL,
    `semester` INT NULL,
    PRIMARY KEY (`doc_id`),
    CONSTRAINT `fk_textbook_doc5` FOREIGN KEY (`doc_id`) REFERENCES `tblDocument5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 13. Bảng tblMagazine5 (Kế thừa Document5 - Tạp chí chuyên ngành)
CREATE TABLE `tblMagazine5` (
    `doc_id` INT NOT NULL,
    `issn` VARCHAR(30) NULL,
    `issue_number` INT NULL,
    `publication_month` INT NULL,
    `release_frequency` VARCHAR(50) NULL,
    PRIMARY KEY (`doc_id`),
    CONSTRAINT `fk_magazine_doc5` FOREIGN KEY (`doc_id`) REFERENCES `tblDocument5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 14. Bảng tblDocumentCopy5 (Bản sao tài liệu vật lý / Cuốn sách cụ thể)
CREATE TABLE `tblDocumentCopy5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `barcode` VARCHAR(50) NOT NULL UNIQUE,
    `document_id` INT NOT NULL,
    `copy_number` INT NOT NULL DEFAULT 1,
    `print_year` INT NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'Có sẵn',
    `condition_status` VARCHAR(50) NULL DEFAULT 'Mới 99%',
    `location_rack` VARCHAR(50) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_copy_doc5` FOREIGN KEY (`document_id`) REFERENCES `tblDocument5` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 15. Bảng tblBorrowSlip5 (Phiếu mượn tài liệu)
CREATE TABLE `tblBorrowSlip5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `slip_code` VARCHAR(30) NOT NULL UNIQUE,
    `reader_id` INT NOT NULL,
    `staff_id` INT NOT NULL,
    `borrow_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `due_date` DATETIME NOT NULL,
    `return_date` DATETIME NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'Đang mượn',
    `deposit_amount` DECIMAL(10,2) NULL DEFAULT 0.00,
    `note` TEXT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_slip_reader5` FOREIGN KEY (`reader_id`) REFERENCES `tblReader5` (`user_id`),
    CONSTRAINT `fk_slip_staff5` FOREIGN KEY (`staff_id`) REFERENCES `tblStaff5` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 16. Bảng tblBorrowDetail5 (Chi tiết từng cuốn sách trong phiếu mượn)
CREATE TABLE `tblBorrowDetail5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `borrow_slip_id` INT NOT NULL,
    `copy_id` INT NOT NULL,
    `borrow_status` VARCHAR(30) NOT NULL DEFAULT 'Đang mượn',
    `return_date` DATETIME NULL,
    `fine_amount` DECIMAL(10,2) NULL DEFAULT 0.00,
    `condition_on_return` VARCHAR(100) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_detail_slip5` FOREIGN KEY (`borrow_slip_id`) REFERENCES `tblBorrowSlip5` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_detail_copy5` FOREIGN KEY (`copy_id`) REFERENCES `tblDocumentCopy5` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 17. Bảng tblSupplier5 (Nhà cung cấp)
CREATE TABLE `tblSupplier5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `supplier_code` VARCHAR(30) NOT NULL UNIQUE,
    `company_name` VARCHAR(150) NOT NULL,
    `tax_code` VARCHAR(30) NULL,
    `address` VARCHAR(255) NULL,
    `phone` VARCHAR(20) NULL,
    `email` VARCHAR(100) NULL,
    `representative` VARCHAR(100) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 18. Bảng tblImportInvoice5 (Hóa đơn nhập sách từ NCC)
CREATE TABLE `tblImportInvoice5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `invoice_code` VARCHAR(30) NOT NULL UNIQUE,
    `supplier_id` INT NOT NULL,
    `staff_id` INT NOT NULL,
    `import_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `total_amount` DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    `payment_status` VARCHAR(30) NOT NULL DEFAULT 'Đã thanh toán',
    `note` TEXT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_inv_supplier5` FOREIGN KEY (`supplier_id`) REFERENCES `tblSupplier5` (`id`),
    CONSTRAINT `fk_inv_staff5` FOREIGN KEY (`staff_id`) REFERENCES `tblStaff5` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 19. Bảng tblImportDetail5 (Chi tiết nhập tài liệu)
CREATE TABLE `tblImportDetail5` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `invoice_id` INT NOT NULL,
    `document_id` INT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    `unit_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    `line_total` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    `vat_rate` DECIMAL(4,2) NULL DEFAULT 0.10,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_imp_invoice5` FOREIGN KEY (`invoice_id`) REFERENCES `tblImportInvoice5` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_imp_doc5` FOREIGN KEY (`document_id`) REFERENCES `tblDocument5` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- =============================================================================
-- DỮ LIỆU MẪU BAN ĐẦU (SEED DATA PHỤC VỤ THỰC THI 2 MODULES & TEST)
-- =============================================================================

-- Người dùng hệ thống
INSERT INTO `tblUser5` (`id`, `username`, `password`, `full_name`, `dob`, `gender`, `email`, `phone`, `address`, `role`) VALUES
(1, 'tuananh_bd', '123456', 'Đỗ Đức Tuấn Anh', '2005-05-15', 'Nam', 'tuananh.b23dccn015@stu.ptit.edu.vn', '0988123455', 'Km10 Nguyễn Trãi, Hà Đông, Hà Nội', 'Reader'),
(2, 'lethihuong_bd', '123456', 'Lê Thị Hương', '2005-08-20', 'Nữ', 'huonglt@stu.ptit.edu.vn', '0912345678', 'Thanh Xuân, Hà Nội', 'Reader'),
(3, 'tranduc_bd', '123456', 'Trần Văn Đức', '2004-03-12', 'Nam', 'ductv@stu.ptit.edu.vn', '0934567890', 'Cầu Giấy, Hà Nội', 'Reader'),
(4, 'thuthu_01', '123456', 'Nguyễn Thị Minh Nguyệt', '1990-11-25', 'Nữ', 'nguyetntm@ptit.edu.vn', '0977889900', 'Quầy Giao dịch Tầng 2 Thư viện PTIT', 'Staff'),
(5, 'quanly_01', '123456', 'TS. Vũ Hoài Nam', '1982-06-18', 'Nam', 'namvh@ptit.edu.vn', '0903456789', 'Phòng Giám đốc Thư viện PTIT', 'Manager');

-- Thông tin Bạn đọc
INSERT INTO `tblReader5` (`user_id`, `reader_code`, `faculty`, `student_class`, `expiry_date`, `max_borrow_limit`, `active`) VALUES
(1, 'B23DCCN015', 'Công nghệ thông tin 1', 'D23CQCN01-B', '2027-10-31', 5, TRUE),
(2, 'B23DCCN020', 'An toàn thông tin', 'D23CQAT02-B', '2027-10-31', 5, TRUE),
(3, 'B22DCVT055', 'Điện tử viễn thông', 'D22CQVT01-B', '2026-10-31', 5, TRUE);

-- Thông tin Thủ thư & Quản lý
INSERT INTO `tblStaff5` (`user_id`, `staff_code`, `position`, `department`, `salary`) VALUES
(4, 'STAFF-015', 'Thủ thư quầy Lưu thông', 'Phòng Công tác Độc giả', 14500000.00);

INSERT INTO `tblManager5` (`user_id`, `management_level`, `office_room`) VALUES
(5, 'Giám đốc Trung tâm Thư viện', 'Phòng 204 Nhà A2');

-- Thẻ bạn đọc (Barcode dùng để quét tại quầy mượn)
INSERT INTO `tblCard5` (`id`, `card_code`, `reader_id`, `issue_date`, `expire_date`, `status`, `barcode`) VALUES
(1, 'CARD-B23DCCN015', 1, '2023-10-01', '2027-10-31', 'Active', 'CARD-015'),
(2, 'CARD-B23DCCN020', 2, '2023-10-01', '2027-10-31', 'Active', 'CARD-020'),
(3, 'CARD-B22DCVT055', 3, '2022-10-01', '2026-10-31', 'Active', 'CARD-055');

-- Thể loại tài liệu
INSERT INTO `tblCategory5` (`id`, `category_code`, `name`, `description`) VALUES
(1, 'CAT-CNPM', 'Kỹ thuật Phần mềm & PTTKHT', 'Các tài liệu, giáo trình về thiết kế kiến trúc phần mềm, phân tích nghiệp vụ, mô hình hóa UML.'),
(2, 'CAT-JAVA', 'Lập trình Java & Web Enterprise', 'Sách chuyên sâu về Java Core, Swing, Spring Boot, Servlet/JSP và hệ thống doanh nghiệp.'),
(3, 'CAT-CSDL', 'Cơ sở Dữ liệu & Hệ phân tán', 'Tài liệu kiến trúc CSDL quan hệ 3NF, NoSQL, tối ưu chỉ mục và an toàn dữ liệu.'),
(4, 'CAT-AI-ML', 'Trí tuệ Nhân tạo & Khoa học Dữ liệu', 'Học máy, xử lý ngôn ngữ tự nhiên, học sâu và thị giác máy tính.'),
(5, 'CAT-MANG', 'Mạng máy tính & An ninh Thông tin', 'Giao thức TCP/IP, định tuyến Cisco, kiểm thử thâm nhập và mật mã học.');

-- Nhà xuất bản
INSERT INTO `tblPublisher5` (`id`, `publisher_code`, `name`, `address`, `phone`, `email`) VALUES
(1, 'NXB-GD', 'NXB Giáo Dục Việt Nam', '81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024-38220801', 'gd@nxbgd.vn'),
(2, 'NXB-TTTT', 'NXB Thông Tin và Truyền Thông', '115 Trần Duy Hưng, Cầu Giấy, Hà Nội', '024-35772139', 'nxb.tttt@mic.gov.vn'),
(3, 'NXB-KHKT', 'NXB Khoa Học và Kỹ Thuật', '70 Trần Hưng Đạo, Hà Nội', '024-39423171', 'khkt@nxbkhkt.vn'),
(4, 'NXB-OREILLY', 'O\'Reilly Media Publishing', 'Sebastopol, California, USA', '+1-707-827-7000', 'orders@oreilly.com');

-- Tác giả
INSERT INTO `tblAuthor5` (`id`, `author_code`, `full_name`, `biography`, `nationality`) VALUES
(1, 'TG-PTIT-01', 'Học viện Công nghệ Bưu chính Viễn thông', 'Bộ môn Công nghệ phần mềm biên soạn chuẩn giảng dạy kỹ sư.', 'Việt Nam'),
(2, 'TG-MARTIN', 'Robert C. Martin (Uncle Bob)', 'Chuyên gia công nghệ phần mềm thế giới, đồng tác giả tuyên ngôn Agile.', 'Hoa Kỳ'),
(3, 'TG-HORST', 'Cay S. Horstmann', 'Giáo sư khoa học máy tính Đại học San Jose, tác giả bộ sách kinh điển Core Java.', 'Hoa Kỳ'),
(4, 'TG-DATE', 'C. J. Date', 'Học giả hàng đầu thế giới về đại số quan hệ và chuẩn hóa CSDL 3NF/BCNF.', 'Vương quốc Anh');

-- Đầu mục tài liệu
INSERT INTO `tblDocument5` (`id`, `doc_code`, `title`, `category_id`, `publisher_id`, `publish_year`, `language`, `price`, `location_shelf`, `summary`) VALUES
(1, 'DOC-PTTKHT-01', 'Bài giảng Phát triển & Thiết kế Hệ thống Thông tin 2 (CNPM)', 1, 2, 2020, 'Tiếng Việt', 85000.00, 'Kệ A1-Khoa CNTT', 'Giáo trình cốt lõi giảng dạy các pha Đặc tả, Phân tích hướng đối tượng với UML, Thiết kế kiến trúc 3 lớp MVC, Cài đặt module và Kiểm thử đơn vị.'),
(2, 'DOC-JAVA-02', 'Lập trình Hướng đối tượng với Java & Swing UI', 2, 1, 2023, 'Tiếng Việt', 120000.00, 'Kệ A2-Khoa CNTT', 'Cẩm nang toàn diện về xây dựng ứng dụng máy tính để bàn (Desktop Application) với Java Swing, kiến trúc phân tầng DAO và kết nối JDBC.'),
(3, 'DOC-CC-03', 'Clean Code: A Handbook of Agile Software Craftsmanship', 1, 4, 2021, 'Tiếng Anh', 450000.00, 'Kệ B1-Khu Quốc tế', 'Tác phẩm kinh điển về kỹ thuật viết mã sạch, cấu trúc đặt tên chuẩn mực, nguyên lý SOLID và kiến trúc module trong dự án phần mềm.'),
(4, 'DOC-DB-04', 'Thiết kế Cơ sở Dữ liệu Quan hệ & Chuẩn hóa 3NF nâng cao', 3, 3, 2022, 'Tiếng Việt', 95000.00, 'Kệ C1-Khoa CNTT', 'Lý thuyết đại số quan hệ, thiết kế mô hình thực thể ERD, phân rã quan hệ đa trị, chuyển đổi từ mô hình quan niệm sang lược đồ vật lý MySQL.'),
(5, 'DOC-AI-05', 'Trí tuệ nhân tạo & Mô hình hóa Hệ thống Thông minh', 4, 3, 2024, 'Tiếng Việt', 150000.00, 'Kệ D1-Khoa KHMT', 'Khái niệm nền tảng AI, mạng nơ-ron học sâu, biểu diễn tri thức và ứng dụng tối ưu hóa luồng nghiệp vụ trong các hệ thống thông tin quy mô lớn.');

-- Liên kết Document - Author
INSERT INTO `tblDocumentAuthor5` (`document_id`, `author_id`) VALUES
(1, 1),
(2, 3),
(3, 2),
(4, 4),
(5, 1);

-- Phân loại chi tiết Book / Textbook
INSERT INTO `tblTextbook5` (`doc_id`, `course_code`, `subject_name`, `academic_level`, `semester`) VALUES
(1, 'INT1408', 'Phát triển Hệ thống Thông tin 2', 'Đại học chính quy', 5),
(2, 'INT1340', 'Lập trình Hướng đối tượng', 'Đại học chính quy', 4),
(4, 'INT1332', 'Cơ sở Dữ liệu', 'Đại học chính quy', 3);

INSERT INTO `tblBook5` (`doc_id`, `isbn`, `number_of_pages`, `book_edition`, `translator`) VALUES
(3, '978-0132350884', 464, '1st Edition', 'Nguyên bản tiếng Anh'),
(5, '978-604-913-882-1', 380, 'Tái bản lần 2', 'Không');

-- Bản sao tài liệu vật lý (Bản sao dùng cho Module 1 hiển thị & Module 2 quét cho mượn)
INSERT INTO `tblDocumentCopy5` (`id`, `barcode`, `document_id`, `copy_number`, `print_year`, `status`, `condition_status`, `location_rack`) VALUES
-- Cuốn PTTKHT
(1, 'BC-PTTKHT-001', 1, 1, 2021, 'Có sẵn', 'Mới 98%', 'Ngăn 1 - Kệ A1-01'),
(2, 'BC-PTTKHT-002', 1, 2, 2021, 'Có sẵn', 'Mới 95%', 'Ngăn 1 - Kệ A1-02'),
(3, 'BC-PTTKHT-003', 1, 3, 2022, 'Đang mượn', 'Tốt', 'Ngăn 1 - Kệ A1-03'),
-- Cuốn Java Swing
(4, 'BC-JAVA-001', 2, 1, 2023, 'Có sẵn', 'Mới 100%', 'Ngăn 2 - Kệ A2-01'),
(5, 'BC-JAVA-002', 2, 2, 2023, 'Có sẵn', 'Mới 99%', 'Ngăn 2 - Kệ A2-02'),
(6, 'BC-JAVA-003', 2, 3, 2023, 'Bảo trì', 'Sờn gáy', 'Kho kỹ thuật'),
-- Cuốn Clean Code
(7, 'BC-CC-001', 3, 1, 2022, 'Có sẵn', 'Mới 99%', 'Ngăn 3 - Kệ B1-01'),
(8, 'BC-CC-002', 3, 2, 2022, 'Có sẵn', 'Mới 97%', 'Ngăn 3 - Kệ B1-02'),
-- Cuốn Cơ sở Dữ liệu
(9, 'BC-DB-001', 4, 1, 2022, 'Có sẵn', 'Mới 95%', 'Ngăn 1 - Kệ C1-01'),
(10, 'BC-DB-002', 4, 2, 2023, 'Có sẵn', 'Mới 100%', 'Ngăn 1 - Kệ C1-02'),
-- Cuốn AI
(11, 'BC-AI-001', 5, 1, 2024, 'Có sẵn', 'Mới 100%', 'Ngăn 2 - Kệ D1-01');

-- Phiếu mượn lịch sử mẫu
INSERT INTO `tblBorrowSlip5` (`id`, `slip_code`, `reader_id`, `staff_id`, `borrow_date`, `due_date`, `return_date`, `status`, `deposit_amount`, `note`) VALUES
(1, 'SLIP-20261101-001', 1, 4, '2026-11-01 09:15:00', '2026-11-15 17:00:00', NULL, 'Đang mượn', 0.00, 'Bạn đọc mượn phục vụ nghiên cứu môn PTTKHT');

INSERT INTO `tblBorrowDetail5` (`id`, `borrow_slip_id`, `copy_id`, `borrow_status`, `return_date`, `fine_amount`, `condition_on_return`) VALUES
(1, 1, 3, 'Đang mượn', NULL, 0.00, NULL);

