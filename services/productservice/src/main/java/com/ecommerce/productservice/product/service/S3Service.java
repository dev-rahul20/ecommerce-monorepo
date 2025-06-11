package com.ecommerce.productservice.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.exception.ProductImageNotUploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    public String uploadBase64Image(String base64Data, String fileName) {
       
    	byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        String key = "products/images/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                											.bucket(bucketName)
                											.key(key)
                											.acl("public-read")
                											.build();
        
        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
        
        return Optional.of(response)
        			   .filter(res -> res.sdkHttpResponse().isSuccessful())  // Check if the HTTP response was successful
        			   .map(res -> s3BaseUrl + "/" + bucketName + "/" + key)
        			   .orElseThrow(() -> new ProductImageNotUploadException(
        				"Failed to upload image to S3. HTTP Status Code: " + response.sdkHttpResponse().statusCode()));
    }
    
}