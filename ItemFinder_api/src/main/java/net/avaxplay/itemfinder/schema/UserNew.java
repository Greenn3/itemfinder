package net.avaxplay.itemfinder.schema;

import jakarta.validation.constraints.NotEmpty;

public record UserNew(
        @NotEmpty String Username,
        @NotEmpty String Password
) {
}
