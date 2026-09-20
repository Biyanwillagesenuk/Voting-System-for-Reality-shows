package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contestants")
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String bio;
    private String photoUrl;

    @Column(name = "show_id")
    private Long showId;

    // This creates the actual Foreign Key constraint in the database
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", insertable = false, updatable = false)
    private Show show;

    public Contestant() {}

    public Contestant(String name, int age, String bio, String photoUrl, Long showId) {
        this.name = name;
        this.age = age;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.showId = showId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
}