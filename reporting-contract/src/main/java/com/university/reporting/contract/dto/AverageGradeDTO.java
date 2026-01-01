package com.university.reporting.contract.dto;

public class AverageGradeDTO {

    private Integer courseId;
    private String courseCode;
    private Double averageGrade;

    public AverageGradeDTO(
            Integer courseId,
            String courseCode,
            Double averageGrade
    ) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.averageGrade = averageGrade;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }
}
