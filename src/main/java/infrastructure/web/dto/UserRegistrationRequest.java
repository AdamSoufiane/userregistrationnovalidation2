package infrastructure.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"name", "email", "password"})
public class UserRegistrationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Email(message = "Email must be a valid email format")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
