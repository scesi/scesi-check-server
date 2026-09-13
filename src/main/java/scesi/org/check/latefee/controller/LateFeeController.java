package scesi.org.check.latefee.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.output.LateFeeReportOutput;
import scesi.org.check.latefee.model.response.LateFeeResponse;
import scesi.org.check.latefee.service.task.ILateFeeService;

import java.util.List;

@RestController
@RequestMapping("/late-fee")
public class LateFeeController {
    private final ILateFeeService iLateFeeService;

    public LateFeeController(ILateFeeService iLateFeeService) {
        this.iLateFeeService = iLateFeeService;
    }

    @GetMapping("/")
    public ResponseEntity<StandardResponse<List<LateFeeResponse>>> getAllLateFees() {
        List<ILateFeeProjection> lateFees = iLateFeeService.getAllLateFees();
        List<LateFeeResponse> lateFeeResponses = lateFees.stream().map(this::generateLateFeeResponse).toList();
        StandardResponse<List<LateFeeResponse>> standardResponse = StandardResponse.<List<LateFeeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Late Fee retrieved")
                .data(lateFeeResponses)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PatchMapping("/{lateFeeId}")
    public ResponseEntity<StandardResponse<Boolean>> changeLateFeePaymentStatus(
            @PathVariable("lateFeeId") final Long lateFeeId
    ) {
        Boolean lateFeeChangeStatus = iLateFeeService.changeTypeLateFeeById(lateFeeId);
        StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Late Fee changed")
                .data(lateFeeChangeStatus)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    private LateFeeResponse generateLateFeeResponse(ILateFeeProjection lateFee) {
        return LateFeeResponse.builder()
                .id(lateFee.getId())
                .amountFee(lateFee.getAmountFee())
                .createdDate(lateFee.getCreatedDate())
                .typeLateFeeEntity(lateFee.getTypeLateFee())
                .attendanceId(lateFee.getAttendanceId())
                .build();
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getLateFeeNotPayedReport() {
        LateFeeReportOutput pdfReport = iLateFeeService.getLateFeeNotPayedReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("Reporte Multas Mes " + pdfReport.period().getMonthValue() + ".pdf").build());
        headers.setContentLength(pdfReport.content().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pdfReport.content());
    }
}
