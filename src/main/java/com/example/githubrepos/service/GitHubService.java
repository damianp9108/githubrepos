package com.example.githubrepos.service;

import com.example.githubrepos.client.GitHubClient;
import com.example.githubrepos.model.GitHubBranch;
import com.example.githubrepos.model.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Autowired
    private GitHubClient gitHubClient;

    public List<GitHubRepository> getRepositories(String username, String accept) {
        List<GitHubRepository> repositories = gitHubClient.getRepositories(username, accept);
        return repositories.stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());
    }

    public List<GitHubBranch> getBranches(String owner, String repo, String accept) {
        return gitHubClient.getBranches(owner, repo, accept);
    }
}
