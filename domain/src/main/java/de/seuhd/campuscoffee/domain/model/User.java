package de.seuhd.campuscoffee.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder(toBuilder = true)
public record User (
        @Nullable Long id,
        @Nullable LocalDateTime createdAt,
        @Nullable  LocalDateTime updatedAt,
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Login name contains invalid characters")
        @NonNull String loginName,
        @Email(message = "Email address is invalid")
        @NonNull String emailAddress,
        @NonNull String firstName,
        @NonNull String lastName


        ) implements Serializable { // serializable to allow cloning (see TestFixtures class).
    @Serial
    private static final long serialVersionUID = 1L;
}
