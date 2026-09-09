package scesi.org.check.attendance.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import scesi.org.check.attendance.service.IAttendanceService;
import scesi.org.check.core.model.response.StandardResponse;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final IAttendanceService iAttendanceService;

    public AttendanceController(IAttendanceService iAttendanceService) {
        this.iAttendanceService = iAttendanceService;
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<StandardResponse<Boolean>> uploadCsv(
            @RequestParam("file") final MultipartFile file
    ) {
        final Boolean dataSavedStatus = iAttendanceService.saveAttendancesFromCSV(file);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Attendances saved successfully")
                .data(dataSavedStatus)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }
}
