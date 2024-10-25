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
    public String ItemDescriptionOrEmpty() {
        return this.ItemDescription == null ? "" : this.ItemDescription;
    }

    public String ImageUrlOrDummy() {
        return this.ImageUrl == null ? "https://dummyimage.com/600x400/777777/ffffff.png&text=No+image" : this.ImageUrl;
    }

    public Double LatitudeOr0() {
        return this.Latitude == null ? 0d : this.Latitude;
    }

    public Double LongitudeOr0() {
        return this.Longitude == null ? 0 : this.Longitude;
    }
}
