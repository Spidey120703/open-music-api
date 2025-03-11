package com.spidey.openmusicapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spidey.openmusicapi.mapper")
public class OpenMusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenMusicApiApplication.class, args);
    }

}
