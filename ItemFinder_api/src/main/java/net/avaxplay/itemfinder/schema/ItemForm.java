package net.avaxplay.itemfinder.schema;

import java.time.LocalDateTime;

public class ItemForm {
    private Integer creatorId;
    private String itemName;
    private String itemDescription;
    private LocalDateTime eventDate;
    private String imageUrl;
    private Boolean completed;
    private Integer helperId;
    private Double latitude;
    private Double longitude;
  //  private String locationText = "";

    public ItemForm() {
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getHelperId() {
        return helperId;
    }

    public void setHelperId(Integer helperId) {
        this.helperId = helperId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocationText() {
        return "";
    }

//    public String getLocationText() {
//        return locationText;
//    }
//
//    public void setLocationText(String locationText) {
//        this.locationText = locationText;
//    }
// Getters and setters for each field
    // These allow Spring to populate form data into this object
}
