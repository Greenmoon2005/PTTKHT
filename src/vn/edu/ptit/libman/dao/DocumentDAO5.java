package vn.edu.ptit.libman.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.ptit.libman.model.*;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Lớp DocumentDAO5 - Thực thi toàn bộ truy vấn liên quan đến đầu mục tài liệu (tblDocument5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 & Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class DocumentDAO5 extends DAO5 {

    private final DocumentCopyDAO5 copyDAO;
    private final CategoryDAO5 categoryDAO;

    public DocumentDAO5() {
        super();
        this.copyDAO = new DocumentCopyDAO5();
        this.categoryDAO = new CategoryDAO5();
    }

    /**
     * Tìm kiếm tài liệu theo từ khóa và thể loại.
     * So khớp hoàn toàn với kịch bản Use Case 1 và sơ đồ tuần tự Module 1.
     * @param keyword Từ khóa tìm kiếm (so khớp tiêu đề, mã tài liệu, hoặc tác giả)
     * @param categoryId ID thể loại (nếu <= 0 thì tìm trên tất cả thể loại)
     */
    public List<Document5> searchDocument5(String keyword, int categoryId) {
        List<Document5> list = new ArrayList<>();
        ensureConnection();

        if (this.conn != null) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT d.id, d.doc_code, d.title, d.category_id, d.publisher_id, ")
               .append("d.publish_year, d.language, d.price, d.location_shelf, d.summary, ")
               .append("c.name AS category_name, p.name AS publisher_name ")
               .append("FROM tblDocument5 d ")
               .append("LEFT JOIN tblCategory5 c ON d.category_id = c.id ")
               .append("LEFT JOIN tblPublisher5 p ON d.publisher_id = p.id ")
               .append("LEFT JOIN tblDocumentAuthor5 da ON d.id = da.document_id ")
               .append("LEFT JOIN tblAuthor5 a ON da.author_id = a.id ")
               .append("WHERE 1=1 ");

            List<Object> params = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kwPattern = "%" + keyword.trim().toLowerCase() + "%";
                sql.append("AND (LOWER(d.title) LIKE ? OR LOWER(d.doc_code) LIKE ? OR LOWER(a.full_name) LIKE ?) ");
                params.add(kwPattern);
                params.add(kwPattern);
                params.add(kwPattern);
            }

            if (categoryId > 0) {
                sql.append("AND d.category_id = ? ");
                params.add(categoryId);
            }

            sql.append("ORDER BY d.id ASC");

            try (PreparedStatement ps = this.conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Document5 doc = extractDocumentFromResultSet(rs);
                        // Nạp danh sách bản sao vật lý
                        doc.setCopies5(copyDAO.getCopiesByDocumentId5(doc.getId()));
                        list.add(doc);
                    }
                    return list;
                }
            } catch (SQLException e) {
                System.err.println("[DocumentDAO5] Lỗi truy vấn searchDocument5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        String kw = keyword != null ? keyword.trim().toLowerCase() : "";
        for (Document5 doc : MockDatabase5.getInstance().documents) {
            boolean matchCat = (categoryId <= 0) || (doc.getCategory5() != null && doc.getCategory5().getId() == categoryId);
            boolean matchKw = kw.isEmpty() 
                    || doc.getTitle().toLowerCase().contains(kw) 
                    || doc.getDocCode().toLowerCase().contains(kw) 
                    || doc.getAuthorNames().toLowerCase().contains(kw);

            if (matchCat && matchKw) {
                list.add(doc);
            }
        }
        return list;
    }

    /**
     * Lấy chi tiết một đầu tài liệu theo ID.
     */
    public Document5 getDocumentById5(int id) {
        ensureConnection();
        if (this.conn != null) {
            String sql = "SELECT d.id, d.doc_code, d.title, d.category_id, d.publisher_id, "
                       + "d.publish_year, d.language, d.price, d.location_shelf, d.summary, "
                       + "c.name AS category_name, p.name AS publisher_name "
                       + "FROM tblDocument5 d "
                       + "LEFT JOIN tblCategory5 c ON d.category_id = c.id "
                       + "LEFT JOIN tblPublisher5 p ON d.publisher_id = p.id "
                       + "WHERE d.id = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Document5 doc = extractDocumentFromResultSet(rs);
                        doc.setAuthors5(getAuthorsByDocumentId5(id));
                        doc.setCopies5(copyDAO.getCopiesByDocumentId5(id));
                        return doc;
                    }
                }
            } catch (SQLException e) {
                System.err.println("[DocumentDAO5] Lỗi truy vấn getDocumentById5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (Document5 doc : MockDatabase5.getInstance().documents) {
            if (doc.getId() == id) {
                return doc;
            }
        }
        return null;
    }

    private Document5 extractDocumentFromResultSet(ResultSet rs) throws SQLException {
        Document5 doc = new Document5();
        doc.setId(rs.getInt("id"));
        doc.setDocCode(rs.getString("doc_code"));
        doc.setTitle(rs.getString("title"));
        doc.setPublishYear(rs.getInt("publish_year"));
        doc.setLanguage(rs.getString("language"));
        doc.setPrice(rs.getDouble("price"));
        doc.setLocationShelf(rs.getString("location_shelf"));
        doc.setSummary(rs.getString("summary"));

        int catId = rs.getInt("category_id");
        if (catId > 0) {
            Category5 cat = new Category5();
            cat.setId(catId);
            cat.setName(rs.getString("category_name"));
            doc.setCategory5(cat);
        }

        int pubId = rs.getInt("publisher_id");
        if (pubId > 0) {
            Publisher5 pub = new Publisher5();
            pub.setId(pubId);
            pub.setName(rs.getString("publisher_name"));
            doc.setPublisher5(pub);
        }

        return doc;
    }

    private List<Author5> getAuthorsByDocumentId5(int docId) {
        List<Author5> authors = new ArrayList<>();
        if (this.conn != null) {
            String sql = "SELECT a.id, a.author_code, a.full_name, a.biography, a.nationality "
                       + "FROM tblAuthor5 a "
                       + "JOIN tblDocumentAuthor5 da ON a.id = da.author_id "
                       + "WHERE da.document_id = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, docId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Author5 auth = new Author5();
                        auth.setId(rs.getInt("id"));
                        auth.setAuthorCode(rs.getString("author_code"));
                        auth.setFullName(rs.getString("full_name"));
                        auth.setBiography(rs.getString("biography"));
                        auth.setNationality(rs.getString("nationality"));
                        authors.add(auth);
                    }
                }
            } catch (SQLException e) {
                System.err.println("[DocumentDAO5] Lỗi truy vấn tác giả: " + e.getMessage());
            }
        }
        return authors;
    }

    private void ensureConnection() {
        if (this.conn == null) {
            this.conn = getConnection();
        }
    }
}

