package net.avaxplay.itemfinder.schema;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * @param ItemId
 * @param CreatorId
 * @param ItemName
 * @param ItemDescription
 * @param CreationDate is set automatically by database therefor it's value be ignored
 * @param EventDate
 * @param ImageUrl
 * @param Completed is optional, default: false
 * @param HelperId
 * @param Latitude
 * @param Longitude
 */
public record Item(
        @Id Integer ItemId,
        @NotEmpty Integer CreatorId,
        @NotEmpty String ItemName,
        String ItemDescription,
        LocalDateTime CreationDate,
        @NotEmpty LocalDateTime EventDate,
        String ImageUrl,
        Boolean Completed,
        Integer HelperId,
        Double Latitude,
        Double Longitude
) {
}
