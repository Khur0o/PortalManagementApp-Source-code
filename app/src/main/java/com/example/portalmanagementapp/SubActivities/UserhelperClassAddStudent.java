package com.example.portalmanagementapp.SubActivities;

public class UserhelperClassAddStudent {
    String Fullname, StudentNumber, Age, Gender, UserID;


    public UserhelperClassAddStudent (){

    }
    public UserhelperClassAddStudent(String Fullname, String StudentNumber, String Age, String Gender, String UserID) {

        this.Fullname = Fullname;
        this.StudentNumber = StudentNumber;
        this.Age = Age;
        this.Gender = Gender;
        this.UserID = UserID;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
