package dto;

import java.util.Date;

public class SanctionDTO {
    private int id;
    private int studentId;
    private String sanctionDescription;
    private Date sanctionDate;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSanctionDescription() {
        return sanctionDescription;
    }

    public void setSanctionDescription(String sanctionDescription) {
        this.sanctionDescription = sanctionDescription;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }
}
