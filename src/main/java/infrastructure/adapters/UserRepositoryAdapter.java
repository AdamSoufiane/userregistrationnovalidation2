package infrastructure.adapters;

import application.ports.out.UserRepositoryPort;
import domain.entities.User;
import domain.exceptions.ValidationException;
import infrastructure.exceptions.PersistenceException;
import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Adapter implementation for the UserRepositoryPort.
 * Handles database operations for User entities.
 */
@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Saves a user entity to the database.
     * @param user The user entity to save.
     * @throws PersistenceException if there is an issue with persisting the user.
     * @throws ValidationException if the user data does not pass validation.
     */
    @Override
    @Transactional
    public void saveUser(User user) throws PersistenceException, ValidationException {
        if (user == null || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User and email must not be null or empty");
        }
        user.validate();
        try {
            entityManager.persist(user);
        } catch (EntityExistsException | TransactionRequiredException e) {
            throw new PersistenceException("Error persisting user: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a user by email.
     * @param email The email to search for.
     * @return An Optional containing the user or empty if not found.
     * @throws PersistenceException if there is an issue with the query.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) throws PersistenceException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new PersistenceException("More than one result found for email: " + email, e);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error retrieving user by email", e);
        }
    }

    // @Transactional annotations, validation checks, and Javadoc comments for other methods...

}