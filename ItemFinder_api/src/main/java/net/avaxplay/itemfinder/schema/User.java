package net.avaxplay.itemfinder.schema;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public record User(
        @Id Integer UserId,
        @NotEmpty String Username,
        @NotEmpty String PasswordHash
) {
    public User redacted() {
        return new User(this.UserId, this.Username, "REDACTED");
    }

    public static User redacted(User user) {
        return new User(user.UserId, user.Username, "REDACTED");
    }
}
