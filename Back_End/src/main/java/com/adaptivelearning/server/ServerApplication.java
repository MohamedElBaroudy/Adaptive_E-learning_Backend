package com.adaptivelearning.server;

import com.adaptivelearning.server.Model.StudentCourse;
import com.adaptivelearning.server.Repository.StudentCourseRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.adaptivelearning.server.Property.FileStorageProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import static org.springframework.boot.SpringApplication.run;

@EntityScan(basePackageClasses = {ServerApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EnableJpaRepositories("com.adaptivelearning.server.Repository")
public class ServerApplication {

    public static void main(String[] args) {
        run(ServerApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
