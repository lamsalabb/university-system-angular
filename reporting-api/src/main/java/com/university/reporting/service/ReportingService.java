package com.university.reporting.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import com.university.reporting.repository.ReportingRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportingService {

    private final ReportingRepository repository;

    public ReportingService(ReportingRepository repository) {
        this.repository = repository;
    }

    public List<ActiveStudentDTO> activeStudents() {
        return repository.findActiveStudents();
    }

    public List<CourseEnrollmentDTO> courseEnrollment() {
        return repository.enrollmentByCourse();
    }

    public List<AverageGradeDTO> averageGrades() {
        return repository.averageGrades();
    }

    public byte[] generateAverageGradesPdf(List<AverageGradeDTO> grades) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        document.add(new Paragraph("Grade Distribution Report", titleFont));
        document.add(Chunk.NEWLINE);

        PdfPTable pdfTable = new PdfPTable(4);

        pdfTable.setWidthPercentage(100);
        pdfTable.setWidths(new float[]{2, 3, 2, 2});

        pdfTable.addCell(new PdfPCell(new Phrase("Course ID", headerFont)));
        pdfTable.addCell(new PdfPCell(new Phrase("Course Code", headerFont)));
        pdfTable.addCell(new PdfPCell(new Phrase("Grade", headerFont)));
        pdfTable.addCell(new PdfPCell(new Phrase("Student Count", headerFont)));

        for (AverageGradeDTO dto : grades) {
            pdfTable.addCell(new PdfPCell(new Phrase(dto.courseId().toString(), bodyFont)));
            pdfTable.addCell(new PdfPCell(new Phrase(dto.courseCode(), bodyFont)));
            pdfTable.addCell(new PdfPCell(new Phrase(dto.grade(), bodyFont)));
            pdfTable.addCell(new PdfPCell(new Phrase(dto.count().toString(), bodyFont)));
        }


        document.add(pdfTable);
        document.close();

        return out.toByteArray();


    }

}
