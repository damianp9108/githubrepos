package com.example.githubrepos.service;

import com.example.githubrepos.client.GitHubClient;
import com.example.githubrepos.dto.Branch;
import com.example.githubrepos.dto.GitHubRepositoryWithBranches;
import com.example.githubrepos.entity.Commit;
import com.example.githubrepos.entity.GitHubRepository;
import com.example.githubrepos.entity.Owner;
import com.example.githubrepos.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<GitHubRepositoryWithBranches> getRepositoriesWithBranches(String username, String accept) {
        System.out.println("Fetching repositories for username: " + username);

        List<GitHubRepository> repositories;
        try {
            repositories = gitHubClient.getRepositories(username, accept);
        } catch (WebClientResponseException.NotFound e) {
            throw new UserNotFoundException("User " + username + " not found");
        }
        return repositories.stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> GitHubRepositoryWithBranches.builder()
                        .repositoryName(repo.getName())
                        .ownerLogin(ofNullable(repo.getOwner()).map(Owner::getLogin).orElse(null))
                        .branches(getBranches(ofNullable(repo.getOwner()).map(Owner::getLogin).orElse(null),
                                repo.getName(), accept))
                        .build())
                .collect(Collectors.toList());

    }

    private List<Branch> getBranches(String ownerLogin, String repositoryName, String accept) {
        return gitHubClient.getBranches(ownerLogin, repositoryName, accept).stream()
                .map(gitHubBranch -> Branch.builder()
                        .name(gitHubBranch.getName())
                        .lastCommitSha(ofNullable(gitHubBranch.getCommit()).map(Commit::getSha).orElse(null))
                        .build())
                .collect(Collectors.toList());
    }
}
