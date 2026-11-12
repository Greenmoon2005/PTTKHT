package vn.edu.ptit.libman.model;

/**
 * Thực thể Book5 - Sách thông thường / Sách tham khảo.
 * Kế thừa Document5. So khớp với Sơ đồ lớp Thực thể và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class Book5 extends Document5 {
    private static final long serialVersionUID = 1L;

    private String isbn;
    private int numberOfPages;
    private String bookEdition;
    private String translator;

    public Book5() {
        super();
    }

    public Book5(int id, String docCode, String title, int publishYear, String language, 
                 double price, String locationShelf, String summary, Category5 category5, 
                 Publisher5 publisher5, String isbn, int numberOfPages, String bookEdition, String translator) {
        super(id, docCode, title, publishYear, language, price, locationShelf, summary, category5, publisher5);
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.bookEdition = bookEdition;
        this.translator = translator;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }
}

