package scesi.org.check.attendance.service.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import scesi.org.check.attendance.model.exception.CsvParsingException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AttendanceCsvParserImpl implements IAttendanceCsvParser {

    @Override
    public List<AttendanceCsvRow> processCsvFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            return new CsvToBeanBuilder<AttendanceCsvRow>(reader)
                    .withType(AttendanceCsvRow.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

        } catch (Throwable e) {
            throw new CsvParsingException(e);
        }
    }
}