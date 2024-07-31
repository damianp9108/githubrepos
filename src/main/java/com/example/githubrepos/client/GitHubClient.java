package com.example.githubrepos.client;

import com.example.githubrepos.entity.GitHubBranch;
import com.example.githubrepos.entity.GitHubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient webClient;

    public List<GitHubRepository> getRepositories(String username, String accept) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Accept", accept)
                .retrieve()
                .bodyToFlux(GitHubRepository.class)
                .collectList()
                .block();
    }

    public List<GitHubBranch> getBranches(String owner, String repo, String accept) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .header("Accept", accept)
                .retrieve()
                .bodyToFlux(GitHubBranch.class)
                .collectList()
                .block();
    }
}
