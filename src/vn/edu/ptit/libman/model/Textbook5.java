package vn.edu.ptit.libman.model;

/**
 * Thực thể Textbook5 - Giáo trình đại học.
 * Kế thừa Document5. So khớp với Sơ đồ lớp Thực thể và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class Textbook5 extends Document5 {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String subjectName;
    private String academicLevel;
    private int semester;

    public Textbook5() {
        super();
    }

    public Textbook5(int id, String docCode, String title, int publishYear, String language, 
                     double price, String locationShelf, String summary, Category5 category5, 
                     Publisher5 publisher5, String courseCode, String subjectName, String academicLevel, int semester) {
        super(id, docCode, title, publishYear, language, price, locationShelf, summary, category5, publisher5);
        this.courseCode = courseCode;
        this.subjectName = subjectName;
        this.academicLevel = academicLevel;
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}

