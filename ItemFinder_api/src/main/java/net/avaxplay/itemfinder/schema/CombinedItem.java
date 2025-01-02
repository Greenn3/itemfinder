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
public record CombinedItem(
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
        Double Longitude,
        String LocationText,
        String ItemType
) {
    public String ItemDescriptionOrEmpty() {
        return this.ItemDescription == null ? "" : this.ItemDescription;
    }

    public String ImageUrlOrDummy() {
        return (this.ImageUrl == null || this.ImageUrl.isEmpty()) ? "/img/no_image-400x400.png" : this.ImageUrl;
    }

    public Double LatitudeOr0() {
        return this.Latitude == null ? 0d : this.Latitude;
    }

    public Double LongitudeOr0() {
        return this.Longitude == null ? 0 : this.Longitude;
    }
}
