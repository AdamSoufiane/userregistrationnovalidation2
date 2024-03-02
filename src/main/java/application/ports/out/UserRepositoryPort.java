package application.ports.out;

import domain.entities.User;
import infrastructure.exceptions.PersistenceException;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * An outbound port that defines the contract for user persistence operations.
 */
public interface UserRepositoryPort {

    /**
     * An integer representing the version of the interface to facilitate version control and compatibility checks.
     */
    int INTERFACE_VERSION = 1;

    /**
     * Persists a new User entity to the data store.
     * @param user the User entity to save
     */
    void save(User user) throws PersistenceException;

    /**
     * Retrieves a User entity by email if it exists in the data store.
     * @param email the email to search for
     * @return an Optional containing the User if found
     */
    Optional<User> findUserByEmail(String email) throws PersistenceException;

    /**
     * Removes a User entity from the data store.
     * @param user the User entity to delete
     */
    void deleteUser(User user) throws PersistenceException;

    /**
     * Updates an existing User entity in the data store.
     * @param user the User entity to update
     */
    void updateUser(User user) throws PersistenceException;

    /**
     * Retrieves all User entities from the data store.
     * @return a list of all User entities
     */
    List<User> findAllUsers() throws PersistenceException;

    /**
     * Retrieves a User entity by its identifier if it exists in the data store.
     * @param id the identifier of the User
     * @return an Optional containing the User if found
     */
    Optional<User> findUserById(Long id) throws PersistenceException;

    /**
     * Retrieves a list of User entities filtered by last name.
     * @param lastName the last name to filter by
     * @return a list of User entities with the given last name
     */
    List<User> findUsersByLastName(String lastName) throws PersistenceException;

    /**
     * Checks if a User entity with the given email exists in the data store.
     * @param email the email to check for existence
     * @return true if a User with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Saves a batch of User entities to the data store in a single transaction.
     * @param users the list of User entities to save
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void saveAll(List<User> users) throws PersistenceException;

    /**
     * Removes a batch of User entities from the data store in a single transaction.
     * @param users the list of User entities to delete
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void deleteAll(List<User> users) throws PersistenceException;

    /**
     * Removes a User entity by its identifier from the data store.
     * @param id the identifier of the User to delete
     */
    void deleteUserById(Long id) throws PersistenceException;
}