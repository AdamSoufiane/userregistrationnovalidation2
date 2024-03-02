package application.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * An application-specific exception that is thrown when a user registration attempt fails due to application-level issues.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * A unique error code representing the specific registration exception.
     */
    private String errorCode;

}