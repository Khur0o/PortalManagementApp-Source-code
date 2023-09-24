package com.example.portalmanagementapp;

public class UserhelperClass {
    public String Email, Name,Age, Phone, Status, Password, UserID;

    public UserhelperClass(){

    }

    public UserhelperClass(String Email, String Name, String Age, String Phone, String Status, String Password, String UserID) {
        this.Email = Email;
        this.Name = Name;
        this.Age = Age;
        this.Phone = Phone;
        this.Status = Status;
        this.Password = Password;
        this.UserID = UserID;
    }
}