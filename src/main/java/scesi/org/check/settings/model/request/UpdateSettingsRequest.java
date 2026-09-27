package scesi.org.check.settings.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateSettingsRequest(
        @NotNull(message = "absenceCost is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "absenceCost must be positive")
        @Digits(integer = 6, fraction = 2, message = "absenceCost must have max 6 integer digits and 2 decimal places")
        BigDecimal absenceCost,

        @NotNull(message = "lateArrivalCost is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "lateArrivalCost must be positive")
        @Digits(integer = 6, fraction = 2, message = "lateArrivalCost must have max 6 integer digits and 2 decimal places")
        BigDecimal lateArrivalCost,

        @NotNull(message = "toleranceTimeMinutes is required")
        @Min(value = 0, message = "toleranceTimeMinutes must be positive")
        Integer toleranceTimeMinutes,

        @NotNull(message = "absenceThresholdMinutes is required")
        @Min(value = 1, message = "absenceThresholdMinutes must be at least 1")
        Integer absenceThresholdMinutes
) {}