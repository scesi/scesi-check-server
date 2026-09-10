package scesi.org.check.latefee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.response.LateFeeResponse;
import scesi.org.check.rol.controller.ILateFeeService;

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

    private LateFeeResponse generateLateFeeResponse(ILateFeeProjection lateFee) {
        return LateFeeResponse.builder()
                .id(lateFee.getId())
                .amountFee(lateFee.getAmountFee())
                .createdDate(lateFee.getCreatedDate())
                .typeLateFeeEntity(lateFee.getTypeLateFee())
                .attendanceId(lateFee.getAttendanceId())
                .build();
    }
}
