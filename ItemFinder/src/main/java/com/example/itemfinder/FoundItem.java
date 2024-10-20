package com.example.itemfinder;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "found_item")
public class FoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "finding_date")
    private LocalDateTime date;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "finder_id")
    private User finder;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "loser_id")
    private User loser;

    @Column(name = "finding_location")
    private String findingLocation;

    // No-arg constructor required by JPA
    public FoundItem() {
    }

    // Constructor with all fields
    public FoundItem(String name, LocalDateTime date, String description, String imagePath, User finder, String status, User loser, String findingLocation) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.imagePath = imagePath;
        this.finder = finder;
        this.status = status;
        this.loser = loser;
        this.findingLocation = findingLocation;
    }

    // Getters and Setters for all fields

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public User getFinder() {
        return finder;
    }

    public void setFinder(User finder) {
        this.finder = finder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    public String getFindingLocation() {
        return findingLocation;
    }

    public void setFindingLocation(String findingLocation) {
        this.findingLocation = findingLocation;
    }
}



