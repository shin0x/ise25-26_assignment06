package de.seuhd.campuscoffee.domain.ports;

import de.seuhd.campuscoffee.domain.exceptions.DuplicationException;
import de.seuhd.campuscoffee.domain.exceptions.MissingFieldException;
import de.seuhd.campuscoffee.domain.exceptions.NotFoundException;
import de.seuhd.campuscoffee.domain.model.User;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface UserService {
    /**
     * Clears all user data.
     * This operation removes all user accounts from the system.
     * Warning: This is a destructive operation typically used only for testing
     * or administrative purposes. Use with caution in production environments.
     */
    void clear();

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users; never null, but may be empty if no users exist
     */
    @NonNull List<User> getAll();

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param id the unique identifier of the user to retrieve; must not be null
     * @return the user with the specified ID; never null
     * @throws NotFoundException if no user exists with the given ID
     */
    @NonNull User getById(@NonNull Long id);

    /**
     * Retrieves a specific user by their unique username.
     *
     * @param username the unique username of the user to retrieve; must not be null
     * @return the user with the specified username; never null
     * @throws NotFoundException if no user exists with the given username
     */
    @NonNull User getByLoginName(@NonNull String username);

    /**
     * Creates a new user or updates an existing one.
     * This method performs an "upsert" operation:
     * <ul>
     *   <li>If the user has no ID (null), a new user is created</li>
     *   <li>If the user has an ID and it exists, the existing user is updated</li>
     * </ul>
     * <p>
     * Business rules enforced:
     * <ul>
     *   <li>Usernames and emails must be unique (enforced by database constraint)</li>
     *   <li>All required fields (e.g. username, password or credential reference) must be present and valid</li>
     *   <li>Timestamps (createdAt, updatedAt) are managed by the {@code UserDataService} implementation.</li>
     * </ul>
     *
     * @param user the user entity to create or update; must not be null
     * @return the persisted user entity with populated ID and timestamps; never null
     * @throws NotFoundException if attempting to update a user that does not exist
     * @throws DuplicationException if a user with the same username or email already exists
     * @throws MissingFieldException if required fields for a valid user are missing
     */
    @NonNull User upsert(@NonNull User user);

    /**
     * Imports or links a user from an external identity provider.
     * Typical usage: import a user created externally (OIDC, SAML, LDAP) and persist or update a local account.
     * The method should map external identity information to the domain {@code User} model and persist it.
     *
     * @param provider the external provider identifier (e.g. "google", "ldap"); must not be null
     * @param externalId the identifier of the user in the external provider; must not be null
     * @return the created or updated user entity; never null
     * @throws NotFoundException if the external identity cannot be resolved
     * @throws MissingFieldException if the external identity lacks required fields for creating a valid user
     * @throws DuplicationException if a local user with the same username or email already exists and cannot be reconciled
     */
    @NonNull User importFromExternalProvider(@NonNull String provider, @NonNull String externalId);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete; must not be null
     * @throws NotFoundException if no user exists with the given ID
     */
    void delete(@NonNull Long id);
}