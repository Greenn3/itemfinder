package net.avaxplay.itemfinder.schema;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public record User(
        @Id Integer UserId,
        @NotEmpty String Username,
        @NotEmpty String PasswordHash
) {
}
