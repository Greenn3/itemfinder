package com.example.itemfinder;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity // Annotate the class as an entity
@Table(name = "lost_item") // Specify the table name in the database
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @Nullable
    @Column(name = "item_name")
    private String name;

    @Nullable
    @Column(name = "losing_date")
    private LocalDateTime lostDate;

    @Nullable
    @Column(name = "description")
    private String description;

    @Nullable
    @Column(name = "image_path")
    private String imagePath;

    @Nullable
    @ManyToOne // Define a many-to-one relationship
    @JoinColumn(name = "loser_id") // Foreign key column name
    private User loser;

    @Nullable
    @Column(name = "status")
    private String status;

    @Nullable
    @ManyToOne // Define a many-to-one relationship
    @JoinColumn(name = "finder_id") // Foreign key column name
    private User finder;

    @Nullable
    @Column(name = "losing_location")
    private String losingLocation;

    // No-argument constructor required by JPA
    public LostItem() {
    }

    // All-arguments constructor
    public LostItem(String name, LocalDateTime lostDate, String description, String imagePath, User loser, String status, User finder, String losingLocation) {
        this.name = name;
        this.lostDate = lostDate;
        this.description = description;
        this.imagePath = imagePath;
        this.loser = loser;
        this.status = status;
        this.finder = finder;
        this.losingLocation = losingLocation;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLostDate() {
        return lostDate;
    }

    public void setLostDate(LocalDateTime lostDate) {
        this.lostDate = lostDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getFinder() {
        return finder;
    }

    public void setFinder(User finder) {
        this.finder = finder;
    }

    public String getLosingLocation() {
        return losingLocation;
    }

    public void setLosingLocation(String losingLocation) {
        this.losingLocation = losingLocation;
    }
}
