package ru.tokarev.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MinioConfiguration {

    @Bean
    @Scope(scopeName = "singleton")
    public MinioClient minioClient(@Value("${minio.root.endpoint}") String endpoint,
                                   @Value("${minio.root.username}") String username,
                                   @Value("${minio.root.password}") String password) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
    }
}
