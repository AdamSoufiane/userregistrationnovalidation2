package application.services;

import application.ports.out.UserRepositoryPort;
import domain.entities.User;
import application.exceptions.UserRegistrationException;
import infrastructure.exceptions.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserRegistrationService {

    private final UserRepositoryPort userRepositoryPort;

    public User registerUser(User userData) throws UserRegistrationException {
        if (userData == null || !StringUtils.hasText(userData.getName()) || !StringUtils.hasText(userData.getEmail()) || !StringUtils.hasText(userData.getPassword())) {
            throw new UserRegistrationException("User data fields cannot be null or empty.");
        }

        if (userRepositoryPort.existsByEmail(userData.getEmail())) {
            throw new UserRegistrationException("Email already in use.");
        }

        try {
            userRepositoryPort.saveUser(userData);
            // Since saveUser is void, we assume the userData is updated with new state, e.g., generated ID
            // If the method should return a User, the UserRepositoryPort interface needs to be updated
            return userData;
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationException("Data integrity violation: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new UserRegistrationException("Persistence error occurred while registering user.", e);
        }
    }
}