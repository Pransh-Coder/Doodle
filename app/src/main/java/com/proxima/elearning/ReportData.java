package com.proxima.elearning;

public class ReportData {
    String subject;
    String description;
    String report_by;
    String solved;
    String id;

    public ReportData(String subject, String description, String report_by, String solved, String id) {
        this.subject = subject;
        this.description = description;
        this.report_by = report_by;
        this.solved = solved;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getReport_by() {
        return report_by;
    }

    public String getSolved() {
        return solved;
    }
}
