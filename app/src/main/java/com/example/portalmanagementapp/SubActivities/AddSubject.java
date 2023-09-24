package com.example.portalmanagementapp.SubActivities;

public class AddSubject {
    private String Subject; // Change the property name to "Subject"

    public AddSubject() {
        // Default constructor required for calls to DataSnapshot.getValue(AddSubject.class)
    }

    public AddSubject(String subject) {
        this.Subject = subject; // Update the property assignment
    }

    public String getSubject() {
        return Subject; // Update the getter method
    }

    public void setSubject(String subject) {
        this.Subject = subject; // Update the setter method
    }
}

