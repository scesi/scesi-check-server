package scesi.org.check.attendance.service.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCsvRow {

    @CsvBindByName(column = "id", required = true)
    private Long idUser;

    @CsvBindByName(column = "creationDate", required = true)
    private Instant creationDate;

    @CsvBindByName(column = "readerAccuracy", required = true)
    private String readerAccuracy;
}