package project.bzu.groupassignment1.models;

public class Student {
    private int ID;
    private String Name;
    private String Gender;
    private String DOB;
    private String Grade;

    public Student() {
    }

    public Student(int ID, String name, String gender, String DOB, String grade) {
        this.ID = ID;
        Name = name;
        Gender = gender;
        this.DOB = DOB;
        Grade = grade;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Gender='" + Gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Grade='" + Grade + '\'' +
                '}';
    }
}
