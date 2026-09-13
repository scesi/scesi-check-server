package scesi.org.check.latefee.model.entity;

public class ReportGenerationErrorException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Report generation error";

    public ReportGenerationErrorException() {
        super(DEFAULT_MESSAGE);
    }
}
