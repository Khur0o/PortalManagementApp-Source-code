package com.example.portalmanagementapp;

public class RegisterClass {
    String Fullname, Email, Nationality, Age,
            Gender, PhoneNumber, Address, MothersName, MothersContactNo, FathersName, FathersContactNo, StudentID, YearLevel, LastYearAttended;

    public RegisterClass() {

    }

    public RegisterClass(String fullname, String email, String nationality, String age,
                         String gender, String phoneNumber, String address, String mothersName,
                         String mothersContactNo, String fathersName, String fathersContactNo,
                         String studentID, String yearLevel, String lastYearAttended) {
        this.Fullname = fullname;
        this.Email = email;
        this.Nationality = nationality;
        this.Age = age;
        this.Gender = gender;
        this.PhoneNumber = phoneNumber;
        this.Address = address;
        this.MothersName = mothersName;
        this.MothersContactNo = mothersContactNo;
        this.FathersName = fathersName;
        this.FathersContactNo = fathersContactNo;
        this.StudentID = studentID;
        this.YearLevel = yearLevel;
        this.LastYearAttended = lastYearAttended;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMothersName() {
        return MothersName;
    }

    public void setMothersName(String mothersName) {
        MothersName = mothersName;
    }

    public String getMothersContactNo() {
        return MothersContactNo;
    }

    public void setMothersContactNo(String mothersContactNo) {
        MothersContactNo = mothersContactNo;
    }

    public String getFathersName() {
        return FathersName;
    }

    public void setFathersName(String fathersName) {
        FathersName = fathersName;
    }

    public String getFathersContactNo() {
        return FathersContactNo;
    }

    public void setFathersContactNo(String fathersContactNo) {
        FathersContactNo = fathersContactNo;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getYearLevel() {
        return YearLevel;
    }

    public void setYearLevel(String yearLevel) {
        YearLevel = yearLevel;
    }

    public String getLastYearAttended() {
        return LastYearAttended;
    }

    public void setLastYearAttended(String lastYearAttended) {
        LastYearAttended = lastYearAttended;
    }
}
