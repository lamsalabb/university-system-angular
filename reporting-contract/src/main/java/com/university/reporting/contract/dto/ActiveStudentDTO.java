package com.university.reporting.contract.dto;

public class ActiveStudentDTO {

    private Integer studentId;
    private String email;
    private String firstName;
    private String lastName;

    public ActiveStudentDTO(
            Integer studentId,
            String email,
            String firstName,
            String lastName
    ) {
        this.studentId = studentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
