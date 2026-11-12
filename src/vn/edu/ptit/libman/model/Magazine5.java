package vn.edu.ptit.libman.model;

/**
 * Thực thể Magazine5 - Tạp chí chuyên ngành.
 * Kế thừa Document5. So khớp với Sơ đồ lớp Thực thể và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class Magazine5 extends Document5 {
    private static final long serialVersionUID = 1L;

    private String issn;
    private int issueNumber;
    private int publicationMonth;
    private String releaseFrequency;

    public Magazine5() {
        super();
    }

    public Magazine5(int id, String docCode, String title, int publishYear, String language, 
                     double price, String locationShelf, String summary, Category5 category5, 
                     Publisher5 publisher5, String issn, int issueNumber, int publicationMonth, String releaseFrequency) {
        super(id, docCode, title, publishYear, language, price, locationShelf, summary, category5, publisher5);
        this.issn = issn;
        this.issueNumber = issueNumber;
        this.publicationMonth = publicationMonth;
        this.releaseFrequency = releaseFrequency;
    }

    public String getIssn() {
        return issn;
    }

    public void setIsn(String issn) {
        this.issn = issn;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getPublicationMonth() {
        return publicationMonth;
    }

    public void setPublicationMonth(int publicationMonth) {
        this.publicationMonth = publicationMonth;
    }

    public String getReleaseFrequency() {
        return releaseFrequency;
    }

    public void setReleaseFrequency(String releaseFrequency) {
        this.releaseFrequency = releaseFrequency;
    }
}

