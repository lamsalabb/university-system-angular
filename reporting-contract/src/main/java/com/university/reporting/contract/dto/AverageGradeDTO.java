package com.university.reporting.contract.dto;

public record AverageGradeDTO(Integer courseId, String courseCode, String grade, Long count) {
}
