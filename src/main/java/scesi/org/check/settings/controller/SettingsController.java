package scesi.org.check.settings.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.settings.model.entity.SettingEntity;
import scesi.org.check.settings.model.request.UpdateSettingsRequest;
import scesi.org.check.settings.model.response.SettingResponse;
import scesi.org.check.settings.service.ISettingsService;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final ISettingsService iSettingsService;

    public SettingsController(ISettingsService iSettingsService) {
        this.iSettingsService = iSettingsService;
    }

    @GetMapping
    public ResponseEntity<StandardResponse<SettingResponse>> getSettings() {
        SettingEntity settings = iSettingsService.getSettings();
        SettingResponse response = generateSettingResponse(settings);
        StandardResponse<SettingResponse> standardResponse = StandardResponse.<SettingResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Settings retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(standardResponse);
    }

    @PatchMapping
    public ResponseEntity<StandardResponse<SettingResponse>> updateSettings(
            @Validated @RequestBody UpdateSettingsRequest request
    ) {
        SettingEntity updatedSettings = iSettingsService.updateSettings(request);
        SettingResponse response = generateSettingResponse(updatedSettings);
        StandardResponse<SettingResponse> standardResponse = StandardResponse.<SettingResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Settings updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(standardResponse);
    }

    private SettingResponse generateSettingResponse(SettingEntity settings) {
        return SettingResponse.builder()
                .id(settings.getId())
                .absenceCost(settings.getAbsenceCost())
                .lateArrivalCost(settings.getLateArrivalCost())
                .toleranceTimeMinutes(settings.getToleranceTimeMinutes())
                .absenceThresholdMinutes(settings.getAbsenceThresholdMinutes())
                .lastLateFeeGenerationDate(settings.getLastLateFeeGenerationDate())
                .build();
    }
}