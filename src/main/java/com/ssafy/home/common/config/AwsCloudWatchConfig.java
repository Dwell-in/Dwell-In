package com.ssafy.home.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient; 

@Configuration
public class AwsCloudWatchConfig {

    @Bean
    CloudWatchClient cloudWatchClient() {
        return CloudWatchClient.builder()
            .region(Region.AP_SOUTHEAST_2)
            .build();
    }
}
