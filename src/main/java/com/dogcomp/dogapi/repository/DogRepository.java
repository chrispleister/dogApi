package com.dogcomp.dogapi.repository;

import com.dogcomp.dogapi.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<DogEntity, Long> {

    List<DogEntity> findByBreed(String dogBreed);

    @Query("SELECT DISTINCT d.breed FROM DogEntity d")
    List<String> findDistinctBreeds();
}

