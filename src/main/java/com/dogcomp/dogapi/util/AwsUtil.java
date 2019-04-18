package com.dogcomp.dogapi.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsUtil {
    @Value("${amazonProperties.accessKey}")
    String awsKey;

    @Value("${amazonProperties.secretKey}")
    String awsSecret;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.endpointUrl}")
    private String awsUrl;

    AmazonS3 awsClient =null;

    //Lazy Load
    public AmazonS3 getClient(){
        if(awsClient != null){
            return awsClient;
        }else{
            AWSCredentials credentials = new BasicAWSCredentials(
                    awsKey,
                    awsSecret
            );
            awsClient =  AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.AP_SOUTHEAST_2)
                    .build();
            return awsClient;
        }



    }

    public String getAwsUrl() {
        return awsUrl;
    }

    public String getBucketName() {
        return bucketName;
    }
}
