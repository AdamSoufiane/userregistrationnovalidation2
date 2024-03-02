package infrastructure.rest;

import application.ports.in.UserRegistrationPort;
import application.services.UserRegistrationService;
import domain.entities.User;
import application.exceptions.UserRegistrationException;
import infrastructure.exceptions.PersistenceException;
import infrastructure.web.dto.UserRegistrationRequest;
import infrastructure.web.dto.UserRegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRegistrationAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationAdapter.class);
    private final UserRegistrationService userRegistrationService;
    private final UserRegistrationPort userRegistrationPort;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            User newUser = userRegistrationService.registerUser(request);
            if (newUser.isEmailAlreadyInUse(userRegistrationPort)) {
                logger.info("Attempt to register with an existing email: {}", request.getEmail());
                return newUser.createResponse(HttpStatus.CONFLICT, "Email already in use.", false);
            }
            userRegistrationPort.registerUser(newUser);
            logger.info("User registered successfully: {}", request.getEmail());
            return newUser.createResponse(HttpStatus.CREATED, "User registered successfully", true);
        } catch (UserRegistrationException e) {
            logger.error("User registration failed: {}", e.getMessage());
            return User.createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), false);
        } catch (PersistenceException e) {
            logger.error("Persistence error during registration: {}", e.getMessage());
            return User.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false);
        }
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserRegistrationResponse> handleUserRegistrationException(UserRegistrationException e) {
        return User.createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), false);
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<UserRegistrationResponse> handlePersistenceException(PersistenceException e) {
        return User.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UserRegistrationResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return User.createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), false);
    }
}
