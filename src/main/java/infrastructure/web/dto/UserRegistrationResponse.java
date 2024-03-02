package infrastructure.web.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {

    @JsonProperty("status")
    @NotNull
    @Min(100)
    @Max(599)
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("success")
    private boolean success;

}
