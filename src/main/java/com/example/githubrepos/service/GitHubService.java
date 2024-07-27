package com.example.githubrepos.service;

import com.example.githubrepos.client.GitHubClient;
import com.example.githubrepos.exception.UserNotFoundException;
import com.example.githubrepos.model.Branch;
import com.example.githubrepos.model.GitHubRepositoryWithBranches;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<GitHubRepositoryWithBranches> getRepositoriesWithBranches(String username, String accept) {
        try {
            return gitHubClient.getRepositories(username, accept).stream()
                    .filter(repo -> !repo.isFork())
                    .map(repo -> GitHubRepositoryWithBranches.builder()
                            .repositoryName(repo.getName())
                            .ownerLogin(repo.getOwner().getLogin())
                            .branches(getBranches(repo.getOwner().getLogin(), repo.getName(), accept))
                            .build())
                    .collect(Collectors.toList());
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User " + username + " not found");
        }
    }

    private List<Branch> getBranches(String ownerLogin, String repositoryName, String accept) {
        return gitHubClient.getBranches(ownerLogin, repositoryName, accept).stream()
                .map(gitHubBranch -> Branch.builder()
                        .name(gitHubBranch.getName())
                        .lastCommitSha(gitHubBranch.getCommit().getSha())
                        .build())
                .collect(Collectors.toList());
    }
}
