package com.webeventapp.backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String email;
    private String phone;
    private String instagram;
    private String whatsapp;
    private String website;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProviderMedia> mediaList = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProviderClick> clickList = new ArrayList<>();

    protected Provider() {} // für JPA

    private Provider(Builder builder) {
        this.name = builder.name;
        this.category = builder.category;
        this.location = builder.location;
        this.description = builder.description;
        this.email = builder.email;
        this.phone = builder.phone;
        this.instagram = builder.instagram;
        this.whatsapp = builder.whatsapp;
        this.website = builder.website;
    }

    public static class Builder {
        private String name;
        private String category;
        private String location;
        private String description;
        private String email;
        private String phone;
        private String instagram;
        private String whatsapp;
        private String website;

        public Builder name(String name) { this.name = name; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder instagram(String instagram) { this.instagram = instagram; return this; }
        public Builder whatsapp(String whatsapp) { this.whatsapp = whatsapp; return this; }
        public Builder website(String website) { this.website = website; return this; }

        public Provider build() {
            return new Provider(this);
        }
    }

    public void addMedia(ProviderMedia media) {
        mediaList.add(media);
        media.setProvider(this);
    }

    public void addClick(ProviderClick click) {
        clickList.add(click);
        click.setProvider(this);
    }

    // Getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getInstagram() { return instagram; }
    public String getWhatsapp() { return whatsapp; }
    public String getWebsite() { return website; }

    // equals/hashCode für Collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;
        Provider provider = (Provider) o;
        return Objects.equals(id, provider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<ProviderMedia> getMediaList() {
        return mediaList;
    }

    public List<ProviderClick> getClickList() {
        return clickList;
    }

}
