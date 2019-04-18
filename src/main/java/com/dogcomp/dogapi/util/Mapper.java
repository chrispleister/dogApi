package com.dogcomp.dogapi.util;

import com.dogcomp.dogapi.entity.DogEntity;
import com.dogcomp.dogapi.model.DogModel;

public class Mapper {
    public static DogModel mapToModel(DogEntity dogEntity){
        DogModel dogModel = new DogModel();
        dogModel.setBreed(dogEntity.getBreed());
        dogModel.setCloudUrl(dogEntity.getUrl());
        dogModel.setUploadedAt(dogEntity.getUploadedAt());
        return dogModel;
    }
}
