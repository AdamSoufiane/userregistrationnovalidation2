package application.ports.in;

import domain.entities.User;
import application.exceptions.UserRegistrationException;

/**
 * An inbound port that defines the contract for the user registration service.
 * It declares a method for registering new users based on provided user data.
 */
public interface UserRegistrationPort {

    /**
     * Register a new user with the provided user data after performing input validation.
     *
     * @param userData The user data to be used for registration.
     * @return The registered user.
     * @throws UserRegistrationException If there is an error during the registration process.
     */
    User registerUser(User userData) throws UserRegistrationException;

}