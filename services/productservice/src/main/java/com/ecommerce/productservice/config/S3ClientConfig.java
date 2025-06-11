package com.ecommerce.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class S3ClientConfig {

    @Value("${aws.s3.region}")
    private String awsRegion;

    @Value("${aws.accessKeyId:}")
    private String accessKey;

    @Value("${aws.secretKey:}")
    private String secretKey;

    @Bean
    S3Client s3Client() {
    	
        S3ClientBuilder builder = S3Client.builder().region(Region.of(awsRegion));
        
        if (!accessKey.isBlank() && !secretKey.isBlank()) {
            builder.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)));
        }
        
        return builder.build();
    }
}