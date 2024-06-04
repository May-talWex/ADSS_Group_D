package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ShiftLimitationController {
    @JsonCreator
    public ShiftLimitation JSONtoClass(
            @JsonProperty Employee worker,
            @JsonProperty LocalDate date,
            @JsonProperty boolean isMorningShift
    ) {
        return new ShiftLimitation(worker, date, isMorningShift);
    }
}
