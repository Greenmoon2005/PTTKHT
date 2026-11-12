# HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (LIBMAN) - ĐỀ 01
## BÁO CÁO CÀI ĐẶT MÃ NGUỒN (SOURCE CODE) 2 MODULES TRỌNG TÂM

- **Học viện:** Học viện Công nghệ Bưu chính Viễn thông (PTIT)
- **Học phần:** Phát triển và Thiết kế Hệ thống Thông tin (PTTKHT)
- **Họ và tên sinh viên:** Đỗ Đức Tuấn Anh
- **Mã sinh viên:** `B23DCCN015`
- **Số cuối (Hậu tố bài tập):** `5` (toàn bộ lớp, bảng CSDL, giao diện, DAO đều mang hậu tố `5`)
- **Đề bài:** Hệ thống Quản lý Thư viện (LibMan) - Đề 01
- **2 Module cá nhân được phân công:**
  1. **Module 1:** Bạn đọc tìm thông tin tài liệu (*Reader searches document info*)
  2. **Module 2:** Nhân viên cho bạn đọc mượn tài liệu (*Staff loans document to reader*)

---

## 1. BẢNG SO KHỚP 100% GIỮA CODE VÀ CÁC PHA ĐẶC TẢ, PHÂN TÍCH, THIẾT KẾ

| Thành phần thiết kế | Pha Phân tích / Thiết kế trước đó | File Mã nguồn trong `CODE/` | Trạng thái so khớp |
| :--- | :--- | :--- | :--- |
| **Giao diện Màn hình chính Bạn đọc** | `gdChinhBD5` / `MainReaderFrm5` | `vn.edu.ptit.libman.presentation.reader_ui.MainReaderFrm5` | **Khớp 100%**: nút `btnSearchDoc`, `btnMyBorrows`, `btnProfile`, `btnLogout` |
| **Giao diện Tra cứu & Tìm kiếm Tài liệu** | `gdTimKiemTL5` / `SearchDocFrm5` | `vn.edu.ptit.libman.presentation.reader_ui.SearchDocFrm5` | **Khớp 100%**: `txtKeyword`, `cmbCategory`, `btnSearch`, `btnReset`, `tblResults`, `btnViewDetail`, `lblStatus` |
| **Giao diện Chi tiết Tài liệu & Bản sao** | `gdChiTietTL5` / `DocDetailFrm5` | `vn.edu.ptit.libman.presentation.reader_ui.DocDetailFrm5` | **Khớp 100%**: `lblTitle`, `lblDocCode`, `lblCategory`, `lblPublisher`, `lblLocation`, `txtSummary`, `tblCopies`, `btnBack` |
| **Giao diện Màn hình chính Thủ thư** | `gdChinhNV5` / `MainStaffFrm5` | `vn.edu.ptit.libman.presentation.staff_ui.MainStaffFrm5` | **Khớp 100%**: nút `btnBorrowDoc`, `btnReturnDoc`, `btnManageDoc`, `btnLogout` |
| **Giao diện Cho mượn tài liệu tại quầy** | `gdChoMuonTL5` / `BorrowDocFrm5` | `vn.edu.ptit.libman.presentation.staff_ui.BorrowDocFrm5` | **Khớp 100%**: `txtCardCode`, `btnCheckCard`, `lblReaderName`, `txtCopyBarcode`, `btnAddCopy`, `tblBorrowItems`, `btnRemoveItem`, `btnConfirmLoan`, `btnCancel` |
| **Giao diện Bản xem trước & In Phiếu mượn** | `gdInPhieuMuon5` / `PrintBorrowSlipFrm5` | `vn.edu.ptit.libman.presentation.staff_ui.PrintBorrowSlipFrm5` | **Khớp 100%**: `pnlSlipPreview`, `lblSlipCode`, `lblBorrowDate`, `lblDueDate`, `tblSlipDetails`, `btnPrint`, `btnFinish` |
| **DAO Nền tảng** | `DAO5` | `vn.edu.ptit.libman.dao.DAO5` | **Khớp 100%**: `conn: Connection`, `con: Connection`, `getConnection()`, `closeConnection()` |
| **DAO Thể loại tài liệu** | `CategoryDAO5` | `vn.edu.ptit.libman.dao.CategoryDAO5` | **Khớp 100%**: `getAllCategories5()`, `getCategoryById5()` |
| **DAO Đầu mục tài liệu** | `DocumentDAO5` | `vn.edu.ptit.libman.dao.DocumentDAO5` | **Khớp 100%**: `searchDocument5()`, `getDocumentById5()` |
| **DAO Bản sao vật lý** | `DocumentCopyDAO5` | `vn.edu.ptit.libman.dao.DocumentCopyDAO5` | **Khớp 100%**: `getCopiesByDocumentId5()`, `getCopyByBarcode5()`, `getAvailableCopyByBarcode5()`, `updateCopyStatus5()` |
| **DAO Bạn đọc & Thẻ** | `ReaderDAO5` | `vn.edu.ptit.libman.dao.ReaderDAO5` | **Khớp 100%**: `getReaderByCardBarcode5()`, `getReaderByCardCode5()`, `checkReaderEligibility5()`, `getCurrentBorrowedCount5()` |
| **DAO Phiếu mượn (Transaction)** | `BorrowSlipDAO5` | `vn.edu.ptit.libman.dao.BorrowSlipDAO5` | **Khớp 100%**: `saveBorrowSlip5()`, `getBorrowSlipByCode5()`, `getSlipById5()` (quản lý transaction commit/rollback) |
| **Lớp Thực thể người dùng** | `User5`, `Reader5`, `Staff5`, `Manager5`, `Card5` | `vn.edu.ptit.libman.model.*` | **Khớp 100%**: đúng đầy đủ các thuộc tính, phương thức `isValid()`, quan hệ kế thừa và kết tập 1-1 |
| **Lớp Thực thể học liệu** | `Category5`, `Publisher5`, `Author5`, `Document5`, `Book5`, `Textbook5`, `Magazine5`, `DocumentCopy5` | `vn.edu.ptit.libman.model.*` | **Khớp 100%**: đúng toàn bộ quan hệ 1-N, N-N, kế thừa và trạng thái bản sao |
| **Lớp Thực thể mượn trả** | `BorrowSlip5`, `BorrowDetail5` | `vn.edu.ptit.libman.model.*` | **Khớp 100%**: quan hệ 1-N, `addBorrowDetail()`, thuộc tính hạn trả, trạng thái |
| **Cơ sở dữ liệu Quan hệ 3NF** | `database_schema_diagram_5.puml` | `sql/schema_libman_db_5.sql` | **Khớp 100%**: 19 bảng chuẩn 3NF, đầy đủ khóa PK, FK, Unique, Index và dữ liệu mẫu |

---

## 2. CẤU TRÚC THƯ MỤC SOURCE CODE (`f:\PTTKHT\CODE\`)

```text
CODE/
├── src/
│   └── vn/edu/ptit/libman/
│       ├── Main5.java                              # Cổng khởi chạy hệ thống (chọn Module 1 hoặc Module 2)
│       ├── model/                                  # Tầng Thực thể Miền (Entity Domain Layer)
│       │   ├── User5.java                          # Lớp trừu tượng người dùng
│       │   ├── Reader5.java                        # Bạn đọc thư viện (kế thừa User5)
│       │   ├── Staff5.java                         # Thủ thư tác nghiệp (kế thừa User5)
│       │   ├── Manager5.java                       # Người quản lý thư viện (kế thừa User5)
│       │   ├── Card5.java                          # Thẻ bạn đọc (1:1 với Reader5)
│       │   ├── Category5.java                      # Thể loại tài liệu
│       │   ├── Publisher5.java                     # Nhà xuất bản
│       │   ├── Author5.java                        # Tác giả tài liệu
│       │   ├── Document5.java                      # Đầu mục tài liệu / Tựa sách
│       │   ├── Book5.java                          # Sách thông thường (kế thừa Document5)
│       │   ├── Textbook5.java                      # Giáo trình đại học (kế thừa Document5)
│       │   ├── Magazine5.java                      # Tạp chí chuyên ngành (kế thừa Document5)
│       │   ├── DocumentCopy5.java                  # Bản sao vật lý cụ thể (1:N với Document5)
│       │   ├── BorrowSlip5.java                    # Phiếu mượn tài liệu
│       │   └── BorrowDetail5.java                  # Chi tiết từng cuốn sách mượn
│       ├── dao/                                    # Tầng Truy xuất CSDL (DAO / Control Layer)
│       │   ├── DAO5.java                           # Lớp trừu tượng DAO quản lý kết nối JDBC
│       │   ├── CategoryDAO5.java                   # DAO Thể loại
│       │   ├── DocumentDAO5.java                   # DAO Tài liệu & Tìm kiếm
│       │   ├── DocumentCopyDAO5.java               # DAO Bản sao tài liệu
│       │   ├── ReaderDAO5.java                     # DAO Bạn đọc & Xác thực thẻ
│       │   └── BorrowSlipDAO5.java                 # DAO Phiếu mượn & Transaction an toàn
│       ├── util/                                   # Tầng Tiện ích & Cấu hình (Utility Layer)
│       │   ├── DBContext5.java                     # Cấu hình kết nối MySQL & Chế độ Fallback
│       │   ├── MockDatabase5.java                  # Kho dữ liệu mô phỏng trong bộ nhớ (chuẩn schema 3NF)
│       │   ├── SessionManager5.java                # Quản lý phiên đăng nhập hiện tại
│       │   └── DateUtil5.java                      # Tiện ích định dạng & tính toán ngày tháng
│       ├── presentation/                           # Tầng Giao diện Người dùng (Boundary / UI Layer)
│       │   ├── reader_ui/                          # GIAO DIỆN MODULE 1 (BẠN ĐỌC TÌM TÀI LIỆU)
│       │   │   ├── MainReaderFrm5.java             # Màn hình chính Bạn đọc
│       │   │   ├── SearchDocFrm5.java              # Màn hình Tra cứu & Tìm kiếm tài liệu
│       │   │   └── DocDetailFrm5.java              # Màn hình Chi tiết tài liệu & Bản sao
│       │   └── staff_ui/                           # GIAO DIỆN MODULE 2 (THỦ THƯ CHO MƯỢN TÀI LIỆU)
│       │       ├── MainStaffFrm5.java              # Màn hình chính Bàn làm việc Thủ thư
│       │       ├── BorrowDocFrm5.java              # Màn hình Lập phiếu cho mượn tài liệu
│       │       └── PrintBorrowSlipFrm5.java        # Màn hình Xem trước & In phiếu mượn
│       └── test/                                   # TẦNG KIỂM THỬ TỰ ĐỘNG (UNIT & INTEGRATION TESTS)
│           └── TestLibMan5.java                    # Kiểm thử 11 test cases cho cả 2 module
├── lib/
│   └── mysql-connector-j-8.0.33.jar                # Thư viện Driver JDBC MySQL 8.0
├── sql/
│   └── schema_libman_db_5.sql                      # Kịch bản DDL tạo bảng 3NF & DML dữ liệu mẫu
├── build/                                          # Thư mục chứa các file .class sau khi biên dịch
├── compile.bat                                     # File script biên dịch mã nguồn
├── run_all.bat                                     # File script chạy Portal trung tâm chọn module
├── run_reader.bat                                  # File script chạy trực tiếp Module 1 (Bạn đọc)
├── run_staff.bat                                   # File script chạy trực tiếp Module 2 (Thủ thư)
├── run_tests.bat                                   # File script chạy bộ kiểm thử tự động TestLibMan5
└── README.md                                       # Tài liệu hướng dẫn & Báo cáo kỹ thuật
```

---

## 3. HƯỚNG DẪN KHỞI CHẠY VÀ KIỂM THỬ DỰ ÁN

Hệ thống được thiết kế **thông minh với cơ chế kép (Dual-Engine Architecture)**:
1. **Chế độ MySQL Server:** Khi MySQL Server đang chạy tại `localhost:3306`, hệ thống tự động kết nối qua JDBC Driver `mysql-connector-j-8.0.33.jar` để thao tác trực tiếp với database `libman_db_5`.
2. **Chế độ In-Memory Fallback:** Nếu môi trường chưa cài đặt hoặc chưa khởi động MySQL Server, `DBContext5` tự động kích hoạt bộ nhớ đệm `MockDatabase5` được khởi tạo sẵn 100% dữ liệu mẫu chuẩn theo `schema_libman_db_5.sql`. Nhờ đó, giảng viên và sinh viên có thể khởi chạy và chấm bài ngay lập tức mà không bao giờ gặp lỗi ngắt kết nối.

### Cách 1: Khởi chạy bằng các file `.bat` (Khuyên dùng trên Windows)
- Bấm đúp chuột vào `compile.bat` để biên dịch mã nguồn.
- Bấm đúp chuột vào `run_all.bat` để mở màn hình chọn Module.
- Bấm đúp chuột vào `run_reader.bat` để mở trực tiếp Module 1 (Bạn đọc).
- Bấm đúp chuột vào `run_staff.bat` để mở trực tiếp Module 2 (Thủ thư).
- Bấm đúp chuột vào `run_tests.bat` để chạy 11 kịch bản kiểm thử tự động.

### Cách 2: Khởi chạy bằng dòng lệnh Terminal (PowerShell / Command Prompt)

```powershell
# Di chuyển vào thư mục CODE
cd f:\PTTKHT\CODE

# Biên dịch mã nguồn:
javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.0.33.jar" -d "build" (Get-ChildItem -Path "src" -Recurse -Filter "*.java").FullName

# Chạy cổng trung tâm:
java -cp "build;lib/mysql-connector-j-8.0.33.jar" vn.edu.ptit.libman.Main5

# Chạy kiểm thử tự động:
java -cp "build;lib/mysql-connector-j-8.0.33.jar" vn.edu.ptit.libman.test.TestLibMan5
```

---

## 4. KẾT QUẢ KIỂM THỬ TỰ ĐỘNG (`TestLibMan5`)

```text
================================================================================
   BỘ KIỂM THỬ TỰ ĐỘNG HỆ THỐNG LIBMAN - ĐỀ 01 - SINH VIÊN B23DCCN015   
   SO KHỚP TOÀN DIỆN VỚI KỊCH BẢN USE CASE VÀ SƠ ĐỒ THIẾT KẾ PHA TRƯỚC   
================================================================================

--- [TEST GROUP 1: MODULE 1 - BẠN ĐỌC TÌM THÔNG TIN TÀI LIỆU] ---
  [PASS] TC1.1 - Lấy toàn bộ danh sách thể loại nạp ComboBox
  [PASS] TC1.2 - Tìm kiếm tài liệu theo từ khóa 'PTTKHT'
  [PASS] TC1.3 - Tìm kiếm tài liệu theo thể loại 'Kỹ thuật Phần mềm' (CAT ID = 1)
  [PASS] TC1.4 - Tìm kiếm từ khóa không tồn tại -> Danh sách rỗng (Ngoại lệ A1)
  [PASS] TC1.5 - Lấy chi tiết tài liệu và các bản sao vật lý (DocDetailFrm5)

--- [TEST GROUP 2: MODULE 2 - NHÂN VIÊN CHO MƯỢN TÀI LIỆU] ---
  [PASS] TC2.1 - Quét mã thẻ bạn đọc hợp lệ 'CARD-015' (Sinh viên B23DCCN015)
  [PASS] TC2.2 - Quét mã thẻ không tồn tại 'CARD-INVALID-999' -> Trả về null
  [PASS] TC2.3 - Kiểm tra bản sao khả dụng để mượn (BC-PTTKHT-001)
  [PASS] TC2.4 - Bản sao 'Đang mượn' (BC-PTTKHT-003) -> Không khả dụng
  [PASS] TC2.5 - Kiểm tra số sách đang giữ của bạn đọc
  [PASS] TC2.6 - Thực thi giao dịch Lập phiếu mượn (Transaction SaveBorrowSlip5)

================================================================================
                    KẾT QUẢ TỔNG HỢP KIỂM THỬ (TEST SUMMARY)                    
================================================================================
• Tổng số kịch bản kiểm thử: 11
• Số ca kiểm thử THÀNH CÔNG (PASSED): 11 (100%)
• Số ca kiểm thử THẤT BẠI (FAILED)  : 0
================================================================================
```

