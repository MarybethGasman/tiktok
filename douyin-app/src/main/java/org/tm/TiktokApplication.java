package org.tm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.tm.service.FileSystemStorageService;

@EnableTransactionManagement
@SpringBootApplication
public class TiktokApplication {
    public static void main(String[] args) {
        SpringApplication.run(TiktokApplication.class);
    }


    @Bean
    CommandLineRunner init(FileSystemStorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }
}
