package com.dogcomp.dogapi.rest;

import com.dogcomp.dogapi.model.DogModel;
import com.dogcomp.dogapi.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/v1/dog")
public class DogController {
    @Autowired
    DogService dogService;

    @RequestMapping(method = PUT)
    public ResponseEntity<DogModel> createDog() throws IOException {
    return dogService.createDogFromApi();
    }

    @RequestMapping(path="/{id}", method = GET)
    public ResponseEntity<DogModel> getDogById(@PathVariable Long id) {
        return dogService.getDogById(id);
    }

    @RequestMapping(path="/{id}", method = DELETE)
    public ResponseEntity<HttpStatus> deleteDogById(@PathVariable Long id) {
        return dogService.removeDogById(id);
    }

    @RequestMapping(path="/breed/{breed}", method = GET)
    public ResponseEntity<List<DogModel>> filterDogsByBreed(@PathVariable String breed) {
        return dogService.getByBreed(breed);
    }

    @RequestMapping(path="/breed/", method = GET)
    public ResponseEntity<List<String>> getAllBreeds() {
        return dogService.getAllBreeds();
    }




}
