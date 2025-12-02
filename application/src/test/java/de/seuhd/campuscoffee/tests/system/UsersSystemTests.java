package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.seuhd.campuscoffee.domain.tests.TestFixtures.*;
import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.userRequests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsersSystemTests extends AbstractSysTest {
    @Test
    void createUser() {
        User userToCreate = getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).getFirst());
        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    @Test
    void getUserByLoginName() {
        User userToCreate = getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).get(0));

        User fetchedUser = userDtoMapper.toDomain(userRequests.retrieveByFilter("name", createdUser.loginName()));
        assertEqualsIgnoringIdAndTimestamps(fetchedUser, createdUser);
    }

    @Test
    void deleteUser() {
        User userToCreate = getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).get(0));

        List<Integer> statuses = userRequests.deleteAndReturnStatusCodes(List.of(createdUser.id()));
        assertEquals(204, statuses.get(0));

        List<User> remaining = userRequests.retrieveAll().stream()
                .map(userDtoMapper::toDomain)
                .toList();

        assertTrue(remaining.stream().noneMatch(u -> u.id().equals(createdUser.id())));
    }


}