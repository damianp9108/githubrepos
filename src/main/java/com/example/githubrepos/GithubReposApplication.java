package com.example.githubrepos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GithubReposApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubReposApplication.class, args);
	}
}
