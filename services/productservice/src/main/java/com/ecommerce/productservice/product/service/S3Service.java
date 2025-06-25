package com.ecommerce.productservice.product.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.exception.ProductImageNotUploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    public String uploadBase64Image(String base64Data, String s3Key) {
       
    	byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                											.bucket(bucketName)
                											.key(s3Key)
                											.acl(ObjectCannedACL.PUBLIC_READ)
                											.build();
        
        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
        
        return Optional.of(response)
        			   .filter(res -> res.sdkHttpResponse().isSuccessful())  // Check if the HTTP response was successful
        			   .map(res -> s3BaseUrl + "/" + bucketName + "/" + s3Key)
        			   .orElseThrow(() -> new ProductImageNotUploadException(
        				"Failed to upload image to S3. HTTP Status Code: " + response.sdkHttpResponse().statusCode()));
    }

	public DeleteObjectResponse deleteImageFromAWS(String s3Key) {
		
		DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
			    											   .bucket(bucketName)
			    											   .key(s3Key)
			    											   .build();

		return s3Client.deleteObject(deleteRequest);
	}
    
}