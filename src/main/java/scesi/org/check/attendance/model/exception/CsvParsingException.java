package scesi.org.check.attendance.model.exception;

public class CsvParsingException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Error parsing the csv file";

    public CsvParsingException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
