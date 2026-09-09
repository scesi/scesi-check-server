package scesi.org.check.attendance.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.repository.IAttendanceRepository;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.exception.EventNotFoundException;
import scesi.org.check.event.model.repository.IEventRepository;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.repository.IUserRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    private final IAttendanceRepository iAttendanceRepository;
    private final IUserRepository iUserRepository;
    private final IEventRepository iEventRepository;

    public AttendanceServiceImpl(IAttendanceRepository iAttendanceRepository,
                                 IUserRepository iUserRepository,
                                 IEventRepository iEventRepository) {
        this.iAttendanceRepository = iAttendanceRepository;
        this.iUserRepository = iUserRepository;
        this.iEventRepository = iEventRepository;
    }

    @Override
    public Boolean saveAttendancesFromCSV(MultipartFile file) {
        try (
                InputStream inputStream = file.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(0)
                        .build()) {

            List<String[]> rows = csvReader.readAll();
            List<AttendanceEntity> attendanceEntities = new ArrayList<>();
            Optional<EventEntity> eventEntityOptional = iEventRepository.findFirstByOrderByStartTimeDesc();
            if (eventEntityOptional.isEmpty()) {
                throw new EventNotFoundException();
            }
            for (String[] row : rows) {
                User user = iUserRepository.getReferenceById(Long.valueOf(row[0]));
                attendanceEntities.add(AttendanceEntity.builder()
                        .creationDate(Instant.parse(row[1]))
                        .readerAccuracy(row[2])
                        .user(user)
                        .event(eventEntityOptional.get())
                        .build());
            }
            iAttendanceRepository.saveAll(attendanceEntities);
            return true;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}