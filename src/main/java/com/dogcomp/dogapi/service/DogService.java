package com.dogcomp.dogapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.dogcomp.dogapi.entity.DogEntity;
import com.dogcomp.dogapi.model.ApiResponse;
import com.dogcomp.dogapi.model.DogModel;
import com.dogcomp.dogapi.repository.DogRepository;
import com.dogcomp.dogapi.util.AwsUtil;
import com.dogcomp.dogapi.util.Mapper;
import com.dogcomp.dogapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class DogService {
    @Value("${apiData.dogApiUrl}")
    private String dogPictureUrl;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private AwsUtil awsUtil;


    public ResponseEntity<DogModel> createDogFromApi() throws IOException {
        /** Fetch API record*/
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse apiResponse = restTemplate.getForObject(dogPictureUrl, ApiResponse.class);

        /** Download Image */
        File tempFile = Util.downloadFileToTemp(apiResponse.getUnescapedMessage(), "dogImage" + UUID.randomUUID());

        /** Upload Image */
        AmazonS3 awsClient = awsUtil.getClient();
        awsClient.putObject(
                awsUtil.getBucketName(),
                tempFile.getName(),
                tempFile
        );

        /**Build entity*/
        String breed = Util.extractBreedFromUrl(apiResponse.getMessage());

        DogEntity dogEntity = new DogEntity();
        dogEntity.setBreed(breed);
        dogEntity.setUploadedAt(Instant.now());
        dogEntity.setBaseUrl(awsUtil.getAwsUrl());
        dogEntity.setAwsKey(tempFile.getName());

        dogRepository.save(dogEntity);

        return new ResponseEntity<>(Mapper.mapToModel(dogEntity), HttpStatus.OK);
    }

    public ResponseEntity<DogModel> getDogById(Long id) {
        DogModel dogModel = Mapper.mapToModel(dogRepository.getOne(id));
        return new ResponseEntity<>(dogModel, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> removeDogById(Long id) {
        DogEntity dogEntity = dogRepository.findById(id).get();
        awsUtil.getClient().deleteObject(new DeleteObjectRequest(awsUtil.getBucketName(), dogEntity.getAwsKey()));
        dogRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<DogModel>> getByBreed(String breed) {
        List<DogEntity> dogEntityList = dogRepository.findByBreed(breed);

        return new ResponseEntity<>(dogEntityList.
                stream().
                map(Mapper::mapToModel).
                collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getAllBreeds() {
        List<String> breedList = dogRepository.findDistinctBreeds();
        return new ResponseEntity<>(breedList, HttpStatus.OK);
    }
}

