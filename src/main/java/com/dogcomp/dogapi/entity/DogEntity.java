package com.dogcomp.dogapi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "DOGS")
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "breed")
    @NotBlank
    String breed;

    @Column(name = "uploaded_at")
    Instant uploadedAt;

    @NotBlank
    @Column(name = "base_url")
    String baseUrl;

    @NotBlank
    @Column(name = "aws_key")
    String awsKey;

    public String getUrl() {
        return baseUrl + awsKey;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAwsKey() {
        return awsKey;
    }

    public void setAwsKey(String awsKey) {
        this.awsKey = awsKey;
    }

    public DogEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogEntity dogEntity = (DogEntity) o;
        return id.equals(dogEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
